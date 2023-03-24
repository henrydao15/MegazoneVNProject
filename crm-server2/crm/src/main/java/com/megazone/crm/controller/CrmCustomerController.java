package com.megazone.crm.controller;


import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.AdminConfig;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.common.AuthUtil;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.common.log.CrmCustomerLog;
import com.megazone.crm.constant.CrmAuthEnum;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.CrmContacts;
import com.megazone.crm.entity.PO.CrmCustomer;
import com.megazone.crm.entity.PO.CrmCustomerSetting;
import com.megazone.crm.entity.VO.CrmDataCheckVO;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmMembersSelectVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.CrmUploadExcelService;
import com.megazone.crm.service.ICrmCustomerService;
import com.megazone.crm.service.ICrmTeamMembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * Customer table Front controller
 * </p>
 *
 * @author
 * @since 2020-05-29
 */
@RestController
@RequestMapping("/crmCustomer")
@Api(tags = "Customer Module Interface")
@SysLog(subModel = SubModelType.CRM_CUSTOMER, logClass = CrmCustomerLog.class)
public class CrmCustomerController {

	@Autowired
	private ICrmCustomerService crmCustomerService;

	@Autowired
	private CrmUploadExcelService uploadExcelService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ICrmTeamMembersService teamMembersService;

	@PostMapping("/queryPageList")
	@ApiOperation("Query list page data")
	public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody CrmSearchBO search) {
		search.setPageType(1);
		BasePage<Map<String, Object>> mapBasePage = crmCustomerService.queryPageList(search);
		return R.ok(mapBasePage);
	}

	@PostMapping("/add")
	@ApiOperation("Save data")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#crmModel.entity[customerName]", detail = "'Added customer:' + #crmModel.entity[customerName]")
	public Result<Map<String, Object>> add(@RequestBody CrmBusinessSaveBO crmModel) {
		Map<String, Object> map = crmCustomerService.addOrUpdate(crmModel, false, null);
		return R.ok(map);
	}

	@PostMapping("/update")
	@ApiOperation("Modify data")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result<Map<String, Object>> update(@RequestBody CrmBusinessSaveBO crmModel) {
		Map<String, Object> map = crmCustomerService.addOrUpdate(crmModel, false, null);
		return R.ok(map);
	}

	@PostMapping("/queryById/{customerId}")
	@ApiOperation("Query by ID")
	public Result<CrmModel> queryById(@PathVariable("customerId") @ApiParam(name = "id", value = "id") Integer customerId, Integer poolId) {
		Integer number = crmCustomerService.lambdaQuery().eq(CrmCustomer::getCustomerId, customerId).ne(CrmCustomer::getStatus, 3).count();
		if (number == 0) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, "Customer");
		}
		boolean auth = AuthUtil.isPoolAuth(customerId, CrmAuthEnum.READ);
		if (auth) {
			CrmModel crmModel = new CrmModel();
			crmModel.put("dataAuth", 0);
			return R.ok(crmModel);
		}
		CrmModel model = crmCustomerService.queryById(customerId, poolId);
		return R.ok(model);
	}

	@PostMapping("/field")
	@ApiOperation("Query to add required fields")
	public Result<List> queryCustomerField(@RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmCustomerService.queryField(null));
		}
		return R.ok(crmCustomerService.queryFormPositionField(null));
	}

	@PostMapping("/field/{id}")
	@ApiOperation("Query the information required to modify the data")
	public Result<List> queryField(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id,
								   @RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmCustomerService.queryField(id));
		}
		return R.ok(crmCustomerService.queryFormPositionField(id));
	}

	@PostMapping("/queryContacts")
	@ApiOperation("Query the contact under the customer")
	public Result<BasePage<CrmContacts>> queryContacts(@RequestBody CrmContactsPageBO pageEntity) {
		boolean auth = AuthUtil.isPoolAuth(pageEntity.getCustomerId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<CrmContacts> contactsBasePage = crmCustomerService.queryContacts(pageEntity);
		return R.ok(contactsBasePage);
	}

	@PostMapping("/deleteByIds")
	@ApiOperation("Delete data by ID")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteByIds(@ApiParam(name = "ids", value = "id list") @RequestBody List<Integer> ids) {
		crmCustomerService.deleteByIds(ids);
		return R.ok();
	}


	@PostMapping("/detectionDataCanBeDelete")
	@ApiOperation("Delete data by ID")
	public Result detectionDataCanBeDelete(@ApiParam(name = "ids", value = "id list") @RequestBody List<Integer> ids) {
		return R.ok(crmCustomerService.detectionDataCanBeDelete(ids));
	}

	@PostMapping("/queryBusiness")
	@ApiOperation("Query customer business opportunities")
	public Result<BasePage<Map<String, Object>>> queryBusiness(@RequestBody CrmContactsPageBO pageEntity) {
		boolean auth = AuthUtil.isPoolAuth(pageEntity.getCustomerId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<Map<String, Object>> basePage = crmCustomerService.queryBusiness(pageEntity);
		return R.ok(basePage);
	}

	@PostMapping("/queryContract")
	@ApiOperation("Query the customer's contract")
	public Result<BasePage<Map<String, Object>>> queryContract(@RequestBody CrmContactsPageBO pageEntity) {
		boolean auth = AuthUtil.isPoolAuth(pageEntity.getCustomerId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<Map<String, Object>> basePage = crmCustomerService.queryContract(pageEntity);
		return R.ok(basePage);
	}

	@PostMapping("/queryReceivablesPlan")
	@ApiOperation("Query payment plan according to customer id")
	public Result<BasePage<JSONObject>> queryReceivablesPlan(@RequestBody CrmRelationPageBO crmRelationPageBO) {
		boolean auth = AuthUtil.isPoolAuth(crmRelationPageBO.getCustomerId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<JSONObject> data = crmCustomerService.queryReceivablesPlan(crmRelationPageBO);
		return R.ok(data);
	}

	@PostMapping("/queryReceivables")
	@ApiOperation("Query payment based on customer id")
	public Result<BasePage<JSONObject>> queryReceivables(@RequestBody CrmRelationPageBO crmRelationPageBO) {
		boolean auth = AuthUtil.isPoolAuth(crmRelationPageBO.getCustomerId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<JSONObject> data = crmCustomerService.queryReceivables(crmRelationPageBO);
		return R.ok(data);
	}

	@PostMapping("/queryReturnVisit")
	@ApiOperation("Query payment based on customer id")
	public Result<BasePage<JSONObject>> queryReturnVisit(@RequestBody CrmRelationPageBO crmRelationPageBO) {
		boolean auth = AuthUtil.isPoolAuth(crmRelationPageBO.getCustomerId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<JSONObject> data = crmCustomerService.queryReturnVisit(crmRelationPageBO);
		return R.ok(data);
	}

	@PostMapping("/queryInvoice")
	@ApiOperation("Query invoice according to customer id")
	public Result<BasePage<JSONObject>> queryInvoice(@RequestBody CrmRelationPageBO crmRelationPageBO) {
		boolean auth = AuthUtil.isPoolAuth(crmRelationPageBO.getCustomerId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<JSONObject> data = crmCustomerService.queryInvoice(crmRelationPageBO);
		return R.ok(data);
	}

	@PostMapping("/queryInvoiceInfo")
	@ApiOperation("Query invoice header information based on customer id")
	public Result<BasePage<JSONObject>> queryInvoiceInfo(@RequestBody CrmRelationPageBO crmRelationPageBO) {
		boolean auth = AuthUtil.isPoolAuth(crmRelationPageBO.getCustomerId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<JSONObject> data = crmCustomerService.queryInvoiceInfo(crmRelationPageBO);
		return R.ok(data);
	}

	@PostMapping("/queryCallRecord")
	@ApiOperation("Query call records based on customer id")
	public Result<BasePage<JSONObject>> queryCallRecord(@RequestBody CrmRelationPageBO crmRelationPageBO) {
		boolean auth = AuthUtil.isPoolAuth(crmRelationPageBO.getCustomerId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<JSONObject> data = crmCustomerService.queryCallRecord(crmRelationPageBO);
		return R.ok(data);
	}

	@PostMapping("/lock")
	@ApiOperation("Lock or unlock client")
	@SysLogHandler
	public Result lock(@RequestParam("status") Integer status, @RequestParam("ids") String id) {
		crmCustomerService.lock(status, StrUtil.splitTrim(id, Const.SEPARATOR));
		return R.ok();
	}

	@PostMapping("/setDealStatus")
	@ApiOperation("Modify customer transaction status")
	@SysLogHandler(behavior = BehaviorEnum.CHANGE_DEAL_STATUS)
	public Result setDealStatus(@RequestParam("dealStatus") Integer dealStatus, @RequestParam("ids") String id) {
		crmCustomerService.setDealStatus(dealStatus, StrUtil.splitTrim(id, Const.SEPARATOR).stream().map(Integer::valueOf).collect(Collectors.toList()));
		return R.ok();
	}

	@PostMapping("/changeOwnerUser")
	@ApiOperation("Modify account owner")
	@SysLogHandler(behavior = BehaviorEnum.CHANGE_OWNER)
	public Result changeOwnerUser(@RequestBody CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
		crmCustomerService.changeOwnerUser(crmChangeOwnerUserBO);
		return R.ok();
	}

	@PostMapping("/getMembers/{customerId}")
	@ApiOperation("Get team members")
	public Result<List<CrmMembersSelectVO>> getMembers(@PathVariable("customerId") @ApiParam("customer ID") Integer customerId) {
		CrmEnum crmEnum = CrmEnum.CUSTOMER;
		CrmCustomer customer = crmCustomerService.getById(customerId);
		if (customer == null) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, crmEnum.getRemarks());
		}
		List<CrmMembersSelectVO> members = teamMembersService.getMembers(crmEnum, customerId, customer.getOwnerUserId());
		return R.ok(members);
	}

	@PostMapping("/addMembers")
	@ApiOperation("Add team members")
	@SysLogHandler(behavior = BehaviorEnum.ADD_MEMBER)
	public Result addMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.addMember(CrmEnum.CUSTOMER, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/updateMembers")
	@ApiOperation("Add team members")
	@SysLogHandler(behavior = BehaviorEnum.ADD_MEMBER)
	public Result updateMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.addMember(CrmEnum.CUSTOMER, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/deleteMembers")
	@ApiOperation("Delete team member")
	@SysLogHandler
	public Result deleteMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.deleteMember(CrmEnum.CUSTOMER, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/exitTeam/{customerId}")
	@ApiOperation("Delete team member")
	@SysLogHandler
	public Result exitTeam(@PathVariable("customerId") @ApiParam("customer ID") Integer customerId) {
		teamMembersService.exitTeam(CrmEnum.CUSTOMER, customerId);
		return R.ok();
	}

	@PostMapping("/getRulesSetting")
	@ApiOperation("Query Rule Settings")
	public Result<JsonNode> getRulesSetting() {
		AdminConfig dealDay = adminService.queryFirstConfigByName("customerPoolSettingDealDays").getData();
		AdminConfig followupDay = adminService.queryFirstConfigByName("customerPoolSettingFollowupDays").getData();
		AdminConfig type = adminService.queryFirstConfigByName("customerPoolSetting").getData();
		AdminConfig remindConfig = adminService.queryFirstConfigByName("putInPoolRemindDays").getData();

		if (dealDay == null) {
			dealDay = new AdminConfig();
			dealDay.setName("customerPoolSettingDealDays");
			dealDay.setValue("3");
			adminService.updateAdminConfig(dealDay);
		}
		if (followupDay == null) {
			followupDay = new AdminConfig();
			followupDay.setName("customerPoolSettingFollowupDays");
			followupDay.setValue("7");
			adminService.updateAdminConfig(followupDay);
		}
		if (type == null) {
			type = new AdminConfig();
			type.setName("customerPoolSetting");
			type.setStatus(0);
			adminService.updateAdminConfig(type);
		}
		if (remindConfig == null) {
			remindConfig = new AdminConfig();
			remindConfig.setStatus(0);
			remindConfig.setValue("3");
			remindConfig.setName("putInPoolRemindDays");
			adminService.updateAdminConfig(remindConfig);
		}
		AdminConfig config = adminService.queryFirstConfigByName("expiringContractDays").getData();
		if (config == null) {
			config = new AdminConfig();
			config.setStatus(0);
			config.setName("expiringContractDays");
			config.setValue("3");
			config.setDescription("Contract expiration reminder");
			adminService.updateAdminConfig(config);
		}
		JSONObject object = new JSONObject();
		object.put("dealDay", dealDay.getValue());
		object.put("followupDay", followupDay.getValue());
		object.put("customerConfig", type.getStatus());
		object.put("contractConfig", config.getStatus());
		object.put("contractDay", config.getValue());
		object.put("putInPoolRemindConfig", remindConfig.getStatus());
		object.put("putInPoolRemindDays", remindConfig.getValue());
		return R.ok(object.node);
	}

	@PostMapping("/batchExportExcel")
	@ApiOperation("Select Export")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Select Export", detail = "Export Customer")
	public void batchExportExcel(@RequestBody @ApiParam(name = "ids", value = "id list") List<Integer> ids, HttpServletResponse response) {
		CrmSearchBO search = new CrmSearchBO();
		search.setPageType(0);
		search.setLabel(CrmEnum.CUSTOMER.getType());
		CrmSearchBO.Search entity = new CrmSearchBO.Search();
		entity.setFormType(FieldEnum.TEXT.getFormType());
		entity.setSearchEnum(CrmSearchBO.FieldSearchEnum.ID);
		entity.setValues(ids.stream().map(Object::toString).collect(Collectors.toList()));
		search.getSearchList().add(entity);
		search.setPageType(0);
		crmCustomerService.exportExcel(response, search);
	}

	@PostMapping("/allExportExcel")
	@ApiOperation("Export all")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Export all", detail = "Export customers")
	public void allExportExcel(@RequestBody CrmSearchBO search, HttpServletResponse response) {
		search.setPageType(0);
		crmCustomerService.exportExcel(response, search);
	}

	@PostMapping("/updateCustomerByIds")
	@ApiOperation("Customers are put into the open sea")
	@SysLogHandler(behavior = BehaviorEnum.PUT_IN_POOL)
	public Result updateCustomerByIds(@RequestBody CrmCustomerPoolBO poolBO) {
		crmCustomerService.updateCustomerByIds(poolBO);
		return R.ok();
	}

	@PostMapping("/distributeByIds")
	@ApiOperation("Assign customers on high seas")
	@SysLogHandler(behavior = BehaviorEnum.DISTRIBUTE)
	public Result distributeByIds(@RequestBody CrmCustomerPoolBO poolBO) {
		crmCustomerService.getCustomersByIds(poolBO, 1);
		return R.ok();
	}

	@PostMapping("/receiveByIds")
	@ApiOperation("High seas pick up customers")
	@SysLogHandler(behavior = BehaviorEnum.RECEIVE)
	public Result receiveByIds(@RequestBody CrmCustomerPoolBO poolBO) {
		crmCustomerService.getCustomersByIds(poolBO, 2);
		return R.ok();
	}

	@PostMapping("/downloadExcel")
	@ApiOperation("Download import template")
	public void downloadExcel(HttpServletResponse response) throws IOException {
		crmCustomerService.downloadExcel(false, response);
	}

	@PostMapping("/uploadExcel")
	@ApiOperation("Import Customer")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_IMPORT, object = "Import Customer", detail = "Import Customer")
	public Result<Long> uploadExcel(@RequestParam("file") MultipartFile file, @RequestParam("repeatHandling") Integer repeatHandling) {
		UploadExcelBO uploadExcelBO = new UploadExcelBO();
		uploadExcelBO.setUserInfo(UserUtil.getUser());
		uploadExcelBO.setCrmEnum(CrmEnum.CUSTOMER);
		uploadExcelBO.setPoolId(null);
		uploadExcelBO.setRepeatHandling(repeatHandling);
		Long messageId = uploadExcelService.uploadExcel(file, uploadExcelBO);
		return R.ok(messageId);
	}

	@PostMapping("/customerSetting")
	@ApiOperation("Customer Rule Settings")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "Modify own/lock customer rule settings", detail = "Modify own/lock customer rule settings")
	public Result customerSetting(@RequestBody CrmCustomerSetting customerSetting) {
		crmCustomerService.customerSetting(customerSetting);
		return R.ok();
	}

	@PostMapping("/queryCustomerSetting")
	@ApiOperation("Customer limit query")
	public Result<BasePage<CrmCustomerSetting>> queryCustomerSetting(PageEntity pageEntity, @RequestParam("type") Integer type) {
		BasePage<CrmCustomerSetting> basePage = crmCustomerService.queryCustomerSetting(pageEntity, type);
		return R.ok(basePage);
	}

	@PostMapping("/deleteCustomerSetting")
	@ApiOperation("Delete customer rule settings")
	public Result deleteCustomerSetting(@RequestParam("settingId") Integer settingId) {
		crmCustomerService.deleteCustomerSetting(settingId);
		return R.ok();
	}

	@PostMapping("/setContacts")
	public Result setContacts(@RequestBody CrmFirstContactsBO contactsBO) {
		crmCustomerService.setContacts(contactsBO);
		return R.ok();
	}

	@PostMapping("/dataCheck")
	@ApiOperation("Data check")
	public Result<List<CrmDataCheckVO>> dataCheck(@RequestBody CrmDataCheckBO dataCheckBO) {
		List<CrmDataCheckVO> crmDataCheckVOS = crmCustomerService.dataCheck(dataCheckBO);
		return Result.ok(crmDataCheckVOS);
	}

	@PostMapping("/queryFileList")
	@ApiOperation("Query attachment list")
	public Result<List<FileEntity>> queryFileList(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<FileEntity> fileEntities = crmCustomerService.queryFileList(id);
		return R.ok(fileEntities);
	}

	@PostMapping("/num")
	@ApiOperation("Number of detail pages displayed")
	public Result<CrmInfoNumVO> num(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		CrmInfoNumVO num = crmCustomerService.num(id);
		return R.ok(num);
	}

	@PostMapping("/nearbyCustomer")
	@ApiOperation("Nearby Customers")
	public Result<List<JSONObject>> nearbyCustomer(@RequestParam("lng") String lng, @RequestParam("lat") String lat,
												   @RequestParam("type") Integer type, @RequestParam("radius") Integer radius,
												   @RequestParam(value = "ownerUserId", required = false) Long ownerUserId) {
		List<JSONObject> jsonObjects = crmCustomerService.nearbyCustomer(lng, lat, type, radius, ownerUserId);
		return R.ok(jsonObjects);
	}

	@PostMapping("/star/{id}")
	@ApiOperation("Customer star")
	public Result star(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
		crmCustomerService.star(id);
		return R.ok();
	}

	/**
	 * Query the basic information of the details page
	 *
	 * @param customerId id
	 * @param poolId     high sea ID
	 * @return data
	 */
	@PostMapping("/information/{id}")
	@ApiOperation("Query details page information")
	public Result<List<CrmModelFiledVO>> information(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer customerId, @RequestParam(name = "poolId", required = false) @ApiParam(name = "poolId", value = "poolId") Integer poolId) {
		List<CrmModelFiledVO> information = crmCustomerService.information(customerId, poolId);
		return R.ok(information);
	}

	@PostMapping("/querySimpleEntity")
	@ApiExplain("Query a simple customer object")
	public Result<List<SimpleCrmEntity>> querySimpleEntity(@RequestBody List<Integer> ids) {
		List<SimpleCrmEntity> crmEntities = crmCustomerService.querySimpleEntity(ids);
		return R.ok(crmEntities);
	}

	@PostMapping("/queryByNameCustomerInfo")
	@ApiExplain("Query like simple customer object by name")
	public Result<List<SimpleCrmEntity>> queryByNameCustomerInfo(@RequestParam("name") String name) {
		List<SimpleCrmEntity> crmEntities = crmCustomerService.queryByNameCustomerInfo(name);
		return R.ok(crmEntities);
	}

	@PostMapping("/queryNameCustomerInfo")
	@ApiExplain("Query eq simple customer object by name")
	public Result<List<SimpleCrmEntity>> queryNameCustomerInfo(@RequestParam("name") String name) {
		List<SimpleCrmEntity> crmEntities = crmCustomerService.queryNameCustomerInfo(name);
		return R.ok(crmEntities);
	}

	@PostMapping("/updateInformation")
	@ApiOperation("Basic information is saved and modified")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateInformation(@RequestBody CrmUpdateInformationBO updateInformationBO) {
		crmCustomerService.updateInformation(updateInformationBO);
		return R.ok();
	}

	@ParamAspect
	@PostMapping("/queryCustomerName")
	@ApiExplain("Query customer name")
	public Result<String> queryCustomerName(@RequestParam("customerId") Integer customerId) {
		String customerName = crmCustomerService.getCustomerName(customerId);
		return R.ok(customerName);
	}
}
