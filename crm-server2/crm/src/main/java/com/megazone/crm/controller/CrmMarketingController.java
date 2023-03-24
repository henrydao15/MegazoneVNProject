package com.megazone.crm.controller;


import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.utils.ExcelParseUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.common.log.CrmMarketingLog;
import com.megazone.crm.entity.BO.CrmCensusBO;
import com.megazone.crm.entity.BO.CrmMarketingPageBO;
import com.megazone.crm.entity.BO.CrmModelSaveBO;
import com.megazone.crm.entity.BO.CrmSyncDataBO;
import com.megazone.crm.entity.PO.CrmMarketing;
import com.megazone.crm.entity.PO.CrmMarketingForm;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmMarketingMapper;
import com.megazone.crm.service.ICrmMarketingFormService;
import com.megazone.crm.service.ICrmMarketingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-08-12
 */
@RestController
@RequestMapping("/crmMarketing")
@SysLog(subModel = SubModelType.CRM_MARKETING, logClass = CrmMarketingLog.class)
public class CrmMarketingController {

	@Autowired
	private ICrmMarketingService crmMarketingService;

	@Autowired
	private ICrmMarketingFormService crmMarketingFormService;

	@PostMapping("/add")
	@ApiOperation(value = "")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#crmMarketing.marketingName", detail = "'Create marketing form:'+#crmMarketing.marketingName")
	public Result add(@Validated @RequestBody CrmMarketing crmMarketing) {
		crmMarketingService.addOrUpdate(crmMarketing);
		return Result.ok();
	}

	@PostMapping("/update")
	@ApiOperation(value = "")
	public Result update(@Validated @RequestBody CrmMarketing crmMarketing) {
		crmMarketingService.addOrUpdate(crmMarketing);
		return Result.ok();
	}

	@PostMapping("/queryPageList")
	@ApiOperation(value = "")
	public Result<BasePage<CrmMarketing>> queryPageList(@RequestBody CrmMarketingPageBO crmMarketingPageBO) {
		BasePage<CrmMarketing> page = crmMarketingService.queryPageList(crmMarketingPageBO, null);
		return Result.ok(page);
	}

	@PostMapping("/queryMiNiPageList")
	@ApiOperation(value = "")
	@ParamAspect
	public Result<BasePage<CrmMarketing>> queryMiNiPageList(@RequestBody CrmMarketingPageBO crmMarketingPageBO) {
		UserUtil.setUser(ApplicationContextHolder.getBean(AdminService.class).queryLoginUserInfo(crmMarketingPageBO.getUserId()).getData());
		try {
			BasePage<CrmMarketing> page = crmMarketingService.queryPageList(crmMarketingPageBO, 1);
			return Result.ok(page);
		} finally {
			UserUtil.removeUser();
		}
	}

	/**
	 * @param marketingId
	 */
	@PostMapping("/queryById")
	@ApiOperation(value = "")
	public Result<JsonNode> queryById(@RequestParam("marketingId") Integer marketingId) {
		JSONObject data = crmMarketingService.queryById(marketingId, null);
		return Result.ok(data.node);
	}

	@ParamAspect
	@PostMapping("/queryMiNiById")
	@ApiOperation(value = "")
	public Result<JsonNode> queryMiNiById(@RequestParam("marketingId") Integer marketingId, @RequestParam("userId") Long userId) {
		UserUtil.setUser(ApplicationContextHolder.getBean(AdminService.class).queryLoginUserInfo(userId).getData());
		try {
			JSONObject data = crmMarketingService.queryById(marketingId, null);
			return Result.ok(data.node);
		} finally {
			UserUtil.removeUser();
		}
	}

	/**
	 *
	 */
	@PostMapping("/deleteByIds")
	@ApiOperation(value = "")
	public Result deleteByIds(@RequestBody List<Integer> marketingIds) {
		crmMarketingService.deleteByIds(marketingIds);
		return R.ok();
	}

	/**
	 * @param marketingId
	 */
	@PostMapping("/queryField")
	@ApiOperation(value = "")
	public Result<List<CrmModelFiledVO>> queryField(@RequestParam("marketingId") Integer marketingId) {
		List<CrmModelFiledVO> crmModelFiledVOS = crmMarketingService.queryField(marketingId);
		return Result.ok(crmModelFiledVOS);
	}

	/**
	 *
	 */
	@PostMapping("/updateStatus")
	@ApiOperation(value = "")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateStatus(@RequestParam("marketingIds") String marketingIds, @RequestParam("status") Integer status) {
		crmMarketingService.updateStatus(marketingIds, status);
		return Result.ok();
	}

	@ParamAspect
	@PostMapping("/updateShareNum")
	@ApiOperation(value = "")
	public Result updateShareNum(@RequestParam("marketingId") Integer marketingId, @RequestParam("num") Integer num) {
		crmMarketingService.updateShareNum(marketingId, num);
		return Result.ok();
	}


	@PostMapping("/census")
	@ApiOperation(value = "")
	public Result<BasePage<JSONObject>> census(@RequestBody CrmCensusBO crmCensusBO) {
		BasePage<JSONObject> page = crmMarketingService.census(crmCensusBO);
		return Result.ok(page);
	}

	/**
	 *
	 */
	@ParamAspect
	@PostMapping("/queryAddField")
	@ApiOperation("")
	public Result<JsonNode> queryAddField(@RequestBody JSONObject data) {
		JSONObject jsonObject = crmMarketingService.queryAddField(data.getString("marketingId"));
		return Result.ok(jsonObject.node);
	}

	/**
	 *
	 */
	@ParamAspect
	@PostMapping("/saveMarketingInfo")
	@ApiOperation("")
	public Result saveMarketingInfo(@RequestBody JSONObject data) {
		try {
			crmMarketingService.saveMarketingInfo(data);
		} finally {
			UserUtil.removeUser();
		}
		return Result.ok();
	}

	@ParamAspect
	@PostMapping("/queryMarketingId")
	@ApiOperation("")
	public Result<JsonNode> queryMarketingId(@RequestBody JSONObject data) {
		AES aes = SecureUtil.aes(ICrmMarketingService.BYTES);
		Integer marketingId = Integer.valueOf(aes.decryptStr(data.getString("marketingId")));
		Long currentUserId = Long.valueOf(aes.decryptStr(data.getString("currentUserId")));
		UserUtil.setUser(ApplicationContextHolder.getBean(AdminService.class).queryLoginUserInfo(currentUserId).getData());
		try {
			String device = data.getString("device");
			JSONObject jsonObject = crmMarketingService.queryById(marketingId, device);
			return Result.ok(jsonObject.node);
		} finally {
			UserUtil.removeUser();
		}
	}

	/**
	 *
	 */
	@PostMapping("/syncData")
	@ApiOperation("")
	public Result syncData(@RequestBody CrmSyncDataBO syncDataBO) {
		crmMarketingService.syncData(syncDataBO);
		return Result.ok();
	}

	@PostMapping("/customerExportExcel")
	public void customerExportExcel(@RequestParam("marketingId") Integer marketingId, @RequestParam("status") Integer status) {
		Long userId = UserUtil.getUserId();
		List<Long> userIds = ApplicationContextHolder.getBean(AdminService.class).queryChildUserId(userId).getData();
		userIds.add(userId);
		PageEntity pageEntity = new PageEntity();
		pageEntity.setPageType(0);
		List<JSONObject> fieldList = ApplicationContextHolder.getBean(CrmMarketingMapper.class).census(pageEntity.parse(), marketingId, userIds, status).getList();
		List<CrmModelFiledVO> nameList = crmMarketingService.queryField(marketingId);
		nameList.add(new CrmModelFiledVO("ownerUserName", FieldEnum.TEXT, "responsible person", 1));
		CrmMarketing marketing = crmMarketingService.getById(marketingId);
		Integer crmType = marketing.getCrmType();
		String title;
		if (Arrays.asList(ICrmMarketingService.FIXED_CRM_TYPE).contains(crmType)) {
			title = "Customer Information";
		} else {
			CrmMarketingForm marketingForm = crmMarketingFormService.getById(crmType);
			title = marketingForm.getTitle();
		}
		List<Map<String, Object>> list = fieldList.stream().map(record -> {
			CrmModelSaveBO jsonObject = JSON.parseObject(record.getString("fieldInfo"), CrmModelSaveBO.class);
			Map<String, Object> entity = jsonObject.getEntity();
			jsonObject.getField().forEach(field -> {
				entity.put(field.getFieldName(), field.getValue());
			});
			entity.put("ownerUserName", UserCacheUtil.getUserName(record.getLong("ownerUserId")));
			return entity;
		}).collect(Collectors.toList());
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {
				record.put("dealStatus", Objects.equals(1, record.get("dealStatus")) ? "Dealed" : "Undealed");
			}

			@Override
			public String getExcelName() {
				return title;
			}
		}, nameList);
	}
}

