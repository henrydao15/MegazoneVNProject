package com.megazone.crm.controller;


import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.AuthUtil;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.common.log.CrmBusinessLog;
import com.megazone.crm.constant.CrmAuthEnum;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.CrmBusiness;
import com.megazone.crm.entity.PO.CrmBusinessType;
import com.megazone.crm.entity.PO.CrmContacts;
import com.megazone.crm.entity.VO.CrmBusinessStatusVO;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmMembersSelectVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.ICrmBusinessService;
import com.megazone.crm.service.ICrmBusinessTypeService;
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
 * Business Opportunity Table Front Controller
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
@RestController
@RequestMapping("/crmBusiness")
@Api(tags = "Opportunity Module Interface")
@SysLog(subModel = SubModelType.CRM_BUSINESS, logClass = CrmBusinessLog.class)
public class CrmBusinessController {

	@Autowired
	private ICrmBusinessService crmBusinessService;

	@Autowired
	private ICrmBusinessTypeService crmBusinessTypeService;

	@Autowired
	private ICrmTeamMembersService teamMembersService;

	@PostMapping("/queryPageList")
	@ApiOperation("Query list page data")
	public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody CrmSearchBO search) {
		search.setPageType(1);
		BasePage<Map<String, Object>> mapBasePage = crmBusinessService.queryPageList(search);
		return R.ok(mapBasePage);
	}

	@PostMapping("/add")
	@ApiOperation("Save data")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#crmModel.entity[businessName]", detail = "'Added clue:' + #crmModel.entity[businessName]")
	public Result add(@RequestBody CrmBusinessSaveBO crmModel) {
		crmBusinessService.addOrUpdate(crmModel);
		return R.ok();
	}

	@PostMapping("/information/{id}")
	@ApiOperation("Query details page information")
	public Result<List<CrmModelFiledVO>> information(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<CrmModelFiledVO> information = crmBusinessService.information(id);
		return R.ok(information);
	}

	@PostMapping("/update")
	@ApiOperation("Modify data")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result update(@RequestBody CrmBusinessSaveBO crmModel) {
		if (AuthUtil.isRwAuth((Integer) crmModel.getEntity().get("businessId"), CrmEnum.BUSINESS, CrmAuthEnum.EDIT)) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		crmBusinessService.addOrUpdate(crmModel);
		return R.ok();
	}

	@PostMapping("/queryById/{businessId}")
	@ApiOperation("Query by ID")
	public Result<CrmModel> queryById(@PathVariable("businessId") @ApiParam(name = "id", value = "id") Integer businessId) {
		Integer number = crmBusinessService.lambdaQuery().eq(CrmBusiness::getBusinessId, businessId).count();
		if (number == 0) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, "Opportunity");
		}
		CrmModel model = crmBusinessService.queryById(businessId);
		return R.ok(model);
	}

	@PostMapping("/queryProduct")
	@ApiOperation("Query Product")
	public Result<JsonNode> queryProduct(@RequestBody CrmBusinessQueryRelationBO businessQueryProductBO) {
		boolean auth = AuthUtil.isCrmAuth(CrmEnum.BUSINESS, businessQueryProductBO.getBusinessId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		JSONObject jsonObject = crmBusinessService.queryProduct(businessQueryProductBO);
		return R.ok(jsonObject.node);
	}

	@PostMapping("/queryContract")
	public Result<BasePage<JSONObject>> queryContract(@RequestBody CrmBusinessQueryRelationBO businessQueryRelationBO) {
		boolean auth = AuthUtil.isCrmAuth(CrmEnum.BUSINESS, businessQueryRelationBO.getBusinessId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<JSONObject> page = crmBusinessService.queryContract(businessQueryRelationBO);
		return R.ok(page);
	}

	@PostMapping("/queryContacts")
	@ApiOperation("Query contacts under business opportunities")
	public Result<BasePage<CrmContacts>> queryContacts(@RequestBody CrmContactsPageBO pageEntity) {
		BasePage<CrmContacts> contactsBasePage = crmBusinessService.queryContacts(pageEntity);
		return R.ok(contactsBasePage);
	}

	@PostMapping("/relateContacts")
	@ApiOperation("Associated Contact")
	public Result relateContacts(@RequestBody CrmRelevanceBusinessBO relevanceBusinessBO) {
		crmBusinessService.relateContacts(relevanceBusinessBO);
		return R.ok();
	}

	@PostMapping("/unrelateContacts")
	@ApiOperation("Disassociate Contact")
	public Result unrelateContacts(@RequestBody CrmRelevanceBusinessBO relevanceBusinessBO) {
		crmBusinessService.unrelateContacts(relevanceBusinessBO);
		return R.ok();
	}

	@PostMapping("/deleteByIds")
	@ApiOperation("Delete data by ID")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteByIds(@ApiParam(name = "ids", value = "id list") @RequestBody List<Integer> ids) {
		crmBusinessService.deleteByIds(ids);
		return R.ok();
	}

	@PostMapping("/changeOwnerUser")
	@ApiOperation("Modify the person in charge of the opportunity")
	@SysLogHandler(behavior = BehaviorEnum.CHANGE_OWNER)
	public Result changeOwnerUser(@RequestBody CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
		crmBusinessService.changeOwnerUser(crmChangeOwnerUserBO);
		return R.ok();
	}

	@PostMapping("/field")
	@ApiOperation("Query to add required fields")
	public Result<List> queryBusinessField(@RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmBusinessService.queryField(null));
		}
		return R.ok(crmBusinessService.queryFormPositionField(null));
	}

	@PostMapping("/field/{id}")
	@ApiOperation("Query the information required to modify the data")
	public Result<List> queryField(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id,
								   @RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmBusinessService.queryField(id));
		}
		return R.ok(crmBusinessService.queryFormPositionField(id));
	}

	@PostMapping("/getMembers/{businessId}")
	@ApiOperation("Get team members")
	public Result<List<CrmMembersSelectVO>> getMembers(@PathVariable("businessId") @ApiParam("business opportunity ID") Integer businessId) {
		CrmEnum crmEnum = CrmEnum.BUSINESS;
		CrmBusiness business = crmBusinessService.getById(businessId);
		if (business == null) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, crmEnum.getRemarks());
		}
		List<CrmMembersSelectVO> members = teamMembersService.getMembers(crmEnum, businessId, business.getOwnerUserId());
		return R.ok(members);
	}

	@PostMapping("/addMembers")
	@ApiOperation("Add team members")
	@SysLogHandler(behavior = BehaviorEnum.ADD_MEMBER)
	public Result addMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.addMember(CrmEnum.BUSINESS, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/updateMembers")
	@ApiOperation("Add team members")
	@SysLogHandler(behavior = BehaviorEnum.ADD_MEMBER)
	public Result updateMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.addMember(CrmEnum.BUSINESS, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/deleteMembers")
	@ApiOperation("Delete team member")
	@SysLogHandler
	public Result deleteMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.deleteMember(CrmEnum.BUSINESS, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/exitTeam/{businessId}")
	@ApiOperation("Delete team member")
	@SysLogHandler
	public Result exitTeam(@PathVariable("businessId") @ApiParam("Opportunity ID") Integer businessId) {
		teamMembersService.exitTeam(CrmEnum.BUSINESS, businessId);
		return R.ok();
	}

	@PostMapping("/queryBusinessStatus/{businessId}")
	@ApiOperation("Query Opportunity Status")
	public Result<CrmBusinessStatusVO> queryBusinessStatus(@PathVariable("businessId") @ApiParam("Opportunity ID") Integer businessId) {
		boolean auth = AuthUtil.isCrmAuth(CrmEnum.BUSINESS, businessId, CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		CrmBusinessStatusVO statusVO = crmBusinessTypeService.queryBusinessStatus(businessId);
		return R.ok(statusVO);
	}

	@PostMapping("/boostBusinessStatus")
	@ApiOperation("Opportunity Status Group Advancement")
	public Result boostBusinessStatus(@RequestBody CrmBusinessStatusBO businessStatus) {
		boolean auth = AuthUtil.isRwAuth(businessStatus.getBusinessId(), CrmEnum.BUSINESS, CrmAuthEnum.EDIT);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		crmBusinessTypeService.boostBusinessStatus(businessStatus);
		return R.ok();
	}

	@PostMapping("/queryBusinessStatusOptions")
	@ApiOperation("Query Opportunity Status Group and Opportunity Status")
	public Result<List<CrmBusinessType>> queryBusinessStatusOptions() {
		List<CrmBusinessType> businessTypeList = crmBusinessTypeService.queryBusinessStatusOptions();
		return R.ok(businessTypeList);
	}

	@PostMapping("/setContacts")
	@ApiOperation("Set the primary contact")
	public Result setContacts(@RequestBody CrmFirstContactsBO contacts) {
		crmBusinessService.setContacts(contacts);
		return R.ok();
	}

	@PostMapping("/batchExportExcel")
	@ApiOperation("Select Export")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Select Export", detail = "Export Opportunity")
	public void batchExportExcel(@RequestBody @ApiParam(name = "ids", value = "id list") List<Integer> ids, HttpServletResponse response) {
		CrmSearchBO search = new CrmSearchBO();
		search.setPageType(0);
		search.setLabel(CrmEnum.BUSINESS.getType());
		CrmSearchBO.Search entity = new CrmSearchBO.Search();
		entity.setFormType(FieldEnum.TEXT.getFormType());
		entity.setSearchEnum(CrmSearchBO.FieldSearchEnum.ID);
		entity.setValues(ids.stream().map(Object::toString).collect(Collectors.toList()));
		search.getSearchList().add(entity);
		search.setPageType(0);
		crmBusinessService.exportExcel(response, search);
	}

	@PostMapping("/allExportExcel")
	@ApiOperation("Export all")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Export All", detail = "Export Opportunities")
	public void allExportExcel(@RequestBody CrmSearchBO search, HttpServletResponse response) {
		search.setPageType(0);
		crmBusinessService.exportExcel(response, search);
	}

	@PostMapping("/queryFileList")
	@ApiOperation("Query attachment list")
	public Result<List<FileEntity>> queryFileList(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<FileEntity> fileEntities = crmBusinessService.queryFileList(id);
		return R.ok(fileEntities);
	}

	@PostMapping("/num")
	@ApiOperation("Number of detail pages displayed")
	public Result<CrmInfoNumVO> num(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		CrmInfoNumVO infoNumVO = crmBusinessService.num(id);
		return R.ok(infoNumVO);
	}

	@PostMapping("/star/{id}")
	@ApiOperation("Opportunity Star")
	public Result star(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
		crmBusinessService.star(id);
		return R.ok();
	}

	@PostMapping("/querySimpleEntity")
	@ApiExplain("Query a simple opportunity object")
	public Result<List<SimpleCrmEntity>> querySimpleEntity(@RequestBody List<Integer> ids) {
		List<SimpleCrmEntity> crmEntities = crmBusinessService.querySimpleEntity(ids);
		return R.ok(crmEntities);
	}

	@PostMapping("/updateInformation")
	@ApiOperation("Basic information is saved and modified")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateInformation(@RequestBody CrmUpdateInformationBO updateInformationBO) {
		boolean auth = AuthUtil.isRwAuth(updateInformationBO.getId(), CrmEnum.BUSINESS, CrmAuthEnum.EDIT);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		crmBusinessService.updateInformation(updateInformationBO);
		return R.ok();
	}

}
