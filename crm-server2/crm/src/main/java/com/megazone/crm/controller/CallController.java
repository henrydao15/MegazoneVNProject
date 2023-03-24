package com.megazone.crm.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.common.cache.CrmCacheKey;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.admin.entity.AdminConfig;
import com.megazone.core.feign.admin.entity.CallUser;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.admin.service.CallUserService;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.redis.Redis;
import com.megazone.core.utils.BaseUtil;
import com.megazone.crm.entity.BO.CallRecordBO;
import com.megazone.crm.entity.PO.CallRecord;
import com.megazone.crm.service.ICallRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Ian
 * @date 2020/8/27
 */
@RestController
@RequestMapping("/crmCall")
@Api(tags = "")
public class CallController {

	@Autowired
	private ICallRecordService callRecordService;
	@Autowired
	private CallUserService callUserService;

	@Autowired
	private Redis redis;

	@Autowired
	private AdminService adminService;


	@PostMapping("/save")
	@ApiOperation("")
	public Result save(@RequestBody CallRecord callRecord) {
		String id = CrmCacheKey.CRM_CALL_CACHE_KEY + callRecord.getNumber();
		if (redis.exists(id)) {
			return R.ok();
		}
		redis.setex(id, 5, 1);
		return R.ok(callRecordService.saveRecord(callRecord));
	}


	@PostMapping("/index")
	@ApiOperation("")
	public Result<BasePage<JSONObject>> index(@RequestBody CallRecordBO callRecordBO) {
		return R.ok(callRecordService.pageCallRecordList(callRecordBO));
	}

	/**
	 *
	 */
	@PostMapping("/upload")
	@ApiOperation("")
	public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) {
		String prefix = BaseUtil.getDate();
		callRecordService.upload(file, id, prefix);
		return R.ok();
	}

	@PostMapping("/download")
	@ApiOperation("")
	public void download(@RequestParam("id") String id, HttpServletResponse response) {
		callRecordService.download(id, response);
	}

	@PostMapping("/searchPhone")
	@ApiOperation("")
	public Result<JsonNode> searchPhone(@RequestParam("search") String search) {
		return R.ok(callRecordService.searchPhone(search).node);
	}

	@PostMapping("/queryPhoneNumber")
	@ApiOperation("")
	public Result<List<JSONObject>> queryPhoneNumber(@RequestParam("model") String model, @RequestParam("modelId") String modelId) {
		return R.ok(callRecordService.queryPhoneNumber(model, modelId));
	}

	@PostMapping("/analysis")
	@ApiOperation("")
	public Result<BasePage<JSONObject>> analysis(@RequestBody BiParams biParams) {
		return R.ok(callRecordService.analysis(biParams));
	}

	@PostMapping("/authorize")
	@ApiOperation("")
	public Result authorize(@RequestBody CallUser callUser) {
		return R.ok(callUserService.authorize(callUser).getData());
	}

	@PostMapping("/checkAuth")
	@ApiOperation("")
	public Result<JsonNode> checkAuth() {
		JSONObject jsonObject = new JSONObject();

		AdminConfig adminConfig = adminService.queryFirstConfigByName("call").getData();
		boolean isStart = adminConfig != null && adminConfig.getStatus() == 1;
		Integer data = callUserService.checkAuth().getData();
		jsonObject.put("auth", data != null && isStart);
		jsonObject.put("status", isStart ? 1 : 0);
		if (data != null) {
			jsonObject.put("hisUse", 2 == data ? 2 : 0);
		}
		return R.ok(jsonObject.node);
	}
}
