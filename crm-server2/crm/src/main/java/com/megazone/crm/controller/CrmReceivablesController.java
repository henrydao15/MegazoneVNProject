package com.megazone.crm.controller;


import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.common.log.CrmReceivablesLog;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.CrmReceivables;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmMembersSelectVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.ICrmReceivablesService;
import com.megazone.crm.service.ICrmTeamMembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/crmReceivables")
@Api(tags = "")
@SysLog(subModel = SubModelType.CRM_RECEIVABLES, logClass = CrmReceivablesLog.class)
public class CrmReceivablesController {

	@Autowired
	private ICrmReceivablesService crmReceivablesService;

	@Autowired
	private ICrmTeamMembersService teamMembersService;

	@PostMapping("/queryPageList")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody CrmSearchBO search) {
		search.setPageType(1);
		BasePage<Map<String, Object>> mapBasePage = crmReceivablesService.queryPageList(search);
		return R.ok(mapBasePage);
	}

	@PostMapping("/querySimpleEntity")
	@ApiExplain("")
	public Result<List<SimpleCrmEntity>> querySimpleEntity(@RequestBody List<Integer> ids) {
		List<SimpleCrmEntity> crmEntities = crmReceivablesService.querySimpleEntity(ids);
		return R.ok(crmEntities);
	}

	@PostMapping("/add")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#crmModel.entity[number]", detail = "'Added receivable: ' + #crmModel.entity[number]")
	public Result add(@RequestBody CrmContractSaveBO crmModel) {
		crmReceivablesService.addOrUpdate(crmModel);
		return R.ok();
	}

	@PostMapping("/update")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result update(@RequestBody CrmContractSaveBO crmModel) {
		crmReceivablesService.addOrUpdate(crmModel);
		return R.ok();
	}

	@PostMapping("/queryById/{receivablesId}")
	@ApiOperation("ID")
	public Result<CrmModel> queryById(@PathVariable("receivablesId") @ApiParam(name = "id", value = "id") Integer receivablesId) {
		Integer number = crmReceivablesService.lambdaQuery().eq(CrmReceivables::getReceivablesId, receivablesId).ne(CrmReceivables::getCheckStatus, 7).count();
		if (number == 0) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, "Collection");
		}
		CrmModel model = crmReceivablesService.queryById(receivablesId);
		return R.ok(model);
	}

	@PostMapping("/field")
	@ApiOperation("")
	public Result<List> queryReceivablesField(@RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmReceivablesService.queryField(null));
		}
		return R.ok(crmReceivablesService.queryFormPositionField(null));
	}

	@PostMapping("/field/{id}")
	@ApiOperation("")
	public Result<List> queryField(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id,
								   @RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmReceivablesService.queryField(id));
		}
		return R.ok(crmReceivablesService.queryFormPositionField(id));
	}

	@PostMapping("/deleteByIds")
	@ApiOperation("ID")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteByIds(@ApiParam(name = "ids", value = "id") @RequestBody List<Integer> ids) {
		crmReceivablesService.deleteByIds(ids);
		return R.ok();
	}

	@PostMapping("/changeOwnerUser")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.CHANGE_OWNER)
	public Result changeOwnerUser(@RequestBody CrmChangeOwnerUserBO changeOwnerUserBO) {
		crmReceivablesService.changeOwnerUser(changeOwnerUserBO);
		return R.ok();
	}

	@PostMapping("/information/{id}")
	@ApiOperation("")
	public Result<List<CrmModelFiledVO>> information(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<CrmModelFiledVO> information = crmReceivablesService.information(id);
		return R.ok(information);
	}

	@PostMapping("/queryFileList")
	@ApiOperation("")
	public Result<List<FileEntity>> queryFileList(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<FileEntity> fileEntities = crmReceivablesService.queryFileList(id);
		return R.ok(fileEntities);
	}

	@PostMapping("/num")
	@ApiOperation("")
	public Result<CrmInfoNumVO> num(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		CrmInfoNumVO infoNumVO = crmReceivablesService.num(id);
		return R.ok(infoNumVO);
	}

	@PostMapping("/batchExportExcel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Export receivables", detail = "Export selected")
	public void batchExportExcel(@RequestBody @ApiParam(name = "ids", value = "id") List<Integer> ids, HttpServletResponse response) {
		CrmSearchBO search = new CrmSearchBO();
		search.setPageType(0);
		search.setLabel(CrmEnum.RECEIVABLES.getType());
		CrmSearchBO.Search entity = new CrmSearchBO.Search();
		entity.setFormType(FieldEnum.TEXT.getFormType());
		entity.setSearchEnum(CrmSearchBO.FieldSearchEnum.ID);
		entity.setValues(ids.stream().map(Object::toString).collect(Collectors.toList()));
		search.getSearchList().add(entity);
		search.setPageType(0);
		crmReceivablesService.exportExcel(response, search);
	}

	@PostMapping("/allExportExcel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Export receivable", detail = "Export all")
	public void allExportExcel(@RequestBody CrmSearchBO search, HttpServletResponse response) {
		search.setPageType(0);
		crmReceivablesService.exportExcel(response, search);
	}

	@PostMapping("/updateInformation")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateInformation(@RequestBody CrmUpdateInformationBO updateInformationBO) {
		crmReceivablesService.updateInformation(updateInformationBO);
		return R.ok();
	}

	@PostMapping("/getMembers/{receivablesId}")
	@ApiOperation("")
	public Result<List<CrmMembersSelectVO>> getMembers(@PathVariable("receivablesId") @ApiParam("ID") Integer receivablesId) {
		CrmEnum crmEnum = CrmEnum.RECEIVABLES;
		CrmReceivables receivables = crmReceivablesService.getById(receivablesId);
		if (receivables == null) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, crmEnum.getRemarks());
		}
		List<CrmMembersSelectVO> members = teamMembersService.getMembers(crmEnum, receivablesId, receivables.getOwnerUserId());
		return R.ok(members);
	}

	@PostMapping("/addMembers")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.ADD_MEMBER)
	public Result addMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.addMember(CrmEnum.RECEIVABLES, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/updateMembers")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.ADD_MEMBER)
	public Result updateMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.addMember(CrmEnum.RECEIVABLES, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/deleteMembers")
	@ApiOperation("")
	@SysLogHandler
	public Result deleteMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.deleteMember(CrmEnum.RECEIVABLES, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/exitTeam/{receivablesId}")
	@ApiOperation("")
	@SysLogHandler
	public Result exitTeam(@PathVariable("receivablesId") @ApiParam("ID") Integer receivablesId) {
		teamMembersService.exitTeam(CrmEnum.RECEIVABLES, receivablesId);
		return R.ok();
	}

}

