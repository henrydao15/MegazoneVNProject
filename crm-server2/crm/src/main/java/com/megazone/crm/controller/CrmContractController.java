package com.megazone.crm.controller;


import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.AdminConfig;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.AuthUtil;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.common.log.CrmContractLog;
import com.megazone.crm.constant.CrmAuthEnum;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.CrmContract;
import com.megazone.crm.entity.PO.CrmReceivablesPlan;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmMembersSelectVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.ICrmBackLogDealService;
import com.megazone.crm.service.ICrmContractService;
import com.megazone.crm.service.ICrmInvoiceService;
import com.megazone.crm.service.ICrmTeamMembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * Contract Form Front Controller
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
@RestController
@RequestMapping("/crmContract")
@Api(tags = "Contract Module Interface")
@SysLog(subModel = SubModelType.CRM_CONTRACT, logClass = CrmContractLog.class)
public class CrmContractController {

	@Autowired
	private ICrmContractService crmContractService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ICrmTeamMembersService teamMembersService;

	@Autowired
	private ICrmInvoiceService crmInvoiceService;

	@PostMapping("/queryPageList")
	@ApiOperation("Query list page data")
	public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody CrmSearchBO search) {
		search.setPageType(1);
		BasePage<Map<String, Object>> mapBasePage = crmContractService.queryPageList(search);
		return R.ok(mapBasePage);
	}


	@PostMapping("/queryById/{contractId}")
	@ApiOperation("Query by ID")
	public Result<CrmModel> queryById(@PathVariable("contractId") @ApiParam(name = "id", value = "id") Integer contractId) {
		Integer number = crmContractService.lambdaQuery().eq(CrmContract::getContractId, contractId).ne(CrmContract::getCheckStatus, 7).count();
		if (number == 0) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, "Contract");
		}
		CrmModel model = crmContractService.queryById(contractId);
		return R.ok(model);
	}


	@PostMapping("/deleteByIds")
	@ApiOperation("Delete data by ID")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteByIds(@ApiParam(name = "ids", value = "id list") @RequestBody List<Integer> ids) {
		crmContractService.deleteByIds(ids);
		return R.ok();
	}

	@PostMapping("/field")
	@ApiOperation("Query to add required fields")
	public Result<List> queryContractField(@RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmContractService.queryField(null));
		}
		return R.ok(crmContractService.queryFormPositionField(null));
	}

	@PostMapping("/field/{id}")
	@ApiOperation("Query the information required to modify the data")
	public Result<List> queryField(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id, @RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmContractService.queryField(id));
		}
		return R.ok(crmContractService.queryFormPositionField(id));
	}

	@PostMapping("/changeOwnerUser")
	@ApiOperation("Modify the person in charge of the contract")
	@SysLogHandler(behavior = BehaviorEnum.CHANGE_OWNER)
	public Result changeOwnerUser(@RequestBody CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
		crmContractService.changeOwnerUser(crmChangeOwnerUserBO);
		return R.ok();
	}

	@PostMapping("/add")
	@ApiOperation("Save data")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#crmModel.entity[name]", detail = "'Added contract:' + #crmModel.entity[name]")
	public Result add(@RequestBody CrmContractSaveBO crmModel) {
		crmContractService.addOrUpdate(crmModel);
		return R.ok();
	}

	@PostMapping("/update")
	@ApiOperation("Modify data")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result update(@RequestBody CrmContractSaveBO crmModel) {
		crmContractService.addOrUpdate(crmModel);
		return R.ok();
	}


	@PostMapping("/getMembers/{contractId}")
	@ApiOperation("Get team members")
	public Result<List<CrmMembersSelectVO>> getMembers(@PathVariable("contractId") @ApiParam("Contract ID") Integer contractId) {
		CrmEnum crmEnum = CrmEnum.CONTRACT;
		CrmContract contract = crmContractService.getById(contractId);
		if (contract == null) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, crmEnum.getRemarks());
		}
		List<CrmMembersSelectVO> members = teamMembersService.getMembers(crmEnum, contractId, contract.getOwnerUserId());
		return R.ok(members);
	}

	@PostMapping("/addMembers")
	@ApiOperation("Add team members")
	@SysLogHandler(behavior = BehaviorEnum.ADD_MEMBER)
	public Result addMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.addMember(CrmEnum.CONTRACT, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/updateMembers")
	@ApiOperation("Add team members")
	@SysLogHandler(behavior = BehaviorEnum.ADD_MEMBER)
	public Result updateMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.addMember(CrmEnum.CONTRACT, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/deleteMembers")
	@ApiOperation("Delete team member")
	@SysLogHandler
	public Result deleteMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.deleteMember(CrmEnum.CONTRACT, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/exitTeam/{contractId}")
	@ApiOperation("Leave Team")
	@SysLogHandler
	public Result exitTeam(@PathVariable("contractId") @ApiParam("Contract ID") Integer contractId) {
		teamMembersService.exitTeam(CrmEnum.CONTRACT, contractId);
		return R.ok();
	}

	@PostMapping("/qureyReceivablesListByContractId")
	@ApiOperation("Query collection list")
	public Result<BasePage<JSONObject>> queryReceivablesListByContractId(@RequestBody CrmRelationPageBO crmRelationPageBO) {
		boolean auth = AuthUtil.isCrmAuth(CrmEnum.CONTRACT, crmRelationPageBO.getContractId(), CrmAuthEnum.LIST);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<JSONObject> jsonObjects = crmContractService.queryListByContractId(crmRelationPageBO);
		return R.ok(jsonObjects);
	}

	@PostMapping("/queryProductListByContractId")
	@ApiOperation("Query products under contract")
	public Result<JsonNode> queryProductListByContractId(@RequestBody CrmRelationPageBO crmRelationPageBO) {
		boolean auth = AuthUtil.isCrmAuth(CrmEnum.CONTRACT, crmRelationPageBO.getContractId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		JSONObject page = crmContractService.queryProductListByContractId(crmRelationPageBO);
		return R.ok(page.node);
	}


	@PostMapping("/queryReturnVisit")
	@ApiOperation("Query products under contract")
	public Result<BasePage<JSONObject>> queryReturnVisit(@RequestBody CrmRelationPageBO crmRelationPageBO) {
		boolean auth = AuthUtil.isCrmAuth(CrmEnum.CONTRACT, crmRelationPageBO.getContractId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<JSONObject> page = crmContractService.queryReturnVisit(crmRelationPageBO);
		return R.ok(page);
	}

	@PostMapping("/queryReceivablesPlanListByContractId")
	@ApiOperation("Query the payment plan under the contract")
	public Result<BasePage<CrmReceivablesPlan>> queryReceivablesPlanListByContractId(@RequestBody CrmRelationPageBO crmRelationPageBO) {
		boolean auth = AuthUtil.isCrmAuth(CrmEnum.CONTRACT, crmRelationPageBO.getContractId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<CrmReceivablesPlan> receivablesPlanList = crmContractService.queryReceivablesPlanListByContractId(crmRelationPageBO);
		return R.ok(receivablesPlanList);
	}

	@PostMapping("/queryReceivablesPlansByContractId")
	@ApiOperation("Query the payment plan under the contract")
	public Result<List<CrmReceivablesPlan>> queryReceivablesPlansByContractId(@RequestParam("contractId") Integer contractId, @RequestParam(value = "receivablesId", required = false) Integer receivablesId) {
		List<CrmReceivablesPlan> receivablesPlanList = crmContractService.queryReceivablesPlansByContractId(contractId, receivablesId);
		return R.ok(receivablesPlanList);
	}

	@PostMapping("/queryInvoiceByContractId")
	@ApiOperation("Query the invoice under the contract")
	public Result<BasePage<Map<String, Object>>> queryInvoiceByContractId(@RequestBody CrmRelationPageBO crmRelationPageBO) {
		CrmSearchBO search = new CrmSearchBO();
		search.setPageType(0);
		search.setLabel(CrmEnum.INVOICE.getType());
		CrmSearchBO.Search entity = new CrmSearchBO.Search();
		entity.setFormType(FieldEnum.TEXT.getFormType());
		entity.setSearchEnum(CrmSearchBO.FieldSearchEnum.IS);
		entity.setName("contractNum");
		List<String> nums = new ArrayList<>();
		CrmContract contract = crmContractService.getById(crmRelationPageBO.getContractId());
		nums.add(contract.getNum());
		entity.setValues(nums.stream().map(Object::toString).collect(Collectors.toList()));
		search.getSearchList().add(entity);
		BasePage<Map<String, Object>> mapBasePage = crmInvoiceService.queryPageList(search);
		return R.ok(mapBasePage);
	}

	@ApiOperation(value = "Query contract expiration reminder settings")
	@PostMapping("/queryContractConfig")
	public Result<AdminConfig> queryContractConfig() {
		AdminConfig config = ApplicationContextHolder.getBean(AdminService.class).queryFirstConfigByName("expiringContractDays").getData();
		if (config == null) {
			config = new AdminConfig();
			config.setStatus(0);
			config.setName("expiringContractDays");
			config.setValue("3");
			config.setDescription("Contract expiration reminder");
		}
		return R.ok(config);
	}

	@ApiOperation(value = "Set contract expiration reminder settings")
	@PostMapping("/setContractConfig")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "Set contract expiration reminder settings", detail = "Set contract expiration reminder settings")
	public Result setContractConfig(@RequestParam("status") Integer status, @RequestParam(value = "contractDay", required = false, defaultValue = "0") Integer contractDay) {
		if (status == 1 && contractDay == null) {
			return R.error(CrmCodeEnum.CRM_CONTRACT_CONFIG_ERROR);
		}
		AdminConfig adminConfig = adminService.queryFirstConfigByName("expiringContractDays").getData();
		if (adminConfig == null) {
			adminConfig = new AdminConfig();
		}
		adminConfig.setStatus(status);
		adminConfig.setName("expiringContractDays");
		adminConfig.setValue(contractDay.toString());
		adminConfig.setDescription("Contract expiration reminder");
		adminService.updateAdminConfig(adminConfig);
		ApplicationContextHolder.getBean(ICrmBackLogDealService.class).removeByMap(new JSONObject().fluentPut("model", 8).getInnerMapObject());
		return R.ok();
	}

	@PostMapping("/contractDiscard")
	@ApiOperation("Contract void")
	@SysLogHandler
	public Result contractDiscard(@RequestParam("contractId") Integer contractId) {
		boolean auth = AuthUtil.isRwAuth(contractId, CrmEnum.CONTRACT, CrmAuthEnum.EDIT);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		crmContractService.contractDiscard(contractId);
		return R.ok();
	}

	@PostMapping("/queryFileList")
	@ApiOperation("Query attachment list")
	public Result<List<FileEntity>> queryFileList(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<FileEntity> fileEntities = crmContractService.queryFileList(id);
		return R.ok(fileEntities);
	}

	@PostMapping("/num")
	@ApiOperation("Number of detail pages displayed")
	public Result<CrmInfoNumVO> num(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		CrmInfoNumVO infoNumVO = crmContractService.num(id);
		return R.ok(infoNumVO);
	}


	@PostMapping("/batchExportExcel")
	@ApiOperation("Select Export")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Contract Export", detail = "Selected Export")
	public void batchExportExcel(@RequestBody @ApiParam(name = "ids", value = "id list") List<Integer> ids, HttpServletResponse response) {
		CrmSearchBO search = new CrmSearchBO();
		search.setPageType(0);
		search.setLabel(CrmEnum.CONTRACT.getType());
		CrmSearchBO.Search entity = new CrmSearchBO.Search();
		entity.setFormType(FieldEnum.TEXT.getFormType());
		entity.setSearchEnum(CrmSearchBO.FieldSearchEnum.ID);
		entity.setValues(ids.stream().map(Object::toString).collect(Collectors.toList()));
		search.getSearchList().add(entity);
		search.setPageType(0);
		crmContractService.exportExcel(response, search);
	}

	@PostMapping("/allExportExcel")
	@ApiOperation("Export all")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Contract Export", detail = "Export All")
	public void allExportExcel(@RequestBody CrmSearchBO search, HttpServletResponse response) {
		search.setPageType(0);
		crmContractService.exportExcel(response, search);
	}

	@PostMapping("/information/{id}")
	@ApiOperation("Query details page information")
	public Result<List<CrmModelFiledVO>> information(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<CrmModelFiledVO> information = crmContractService.information(id);
		return R.ok(information);
	}

	@PostMapping("/querySimpleEntity")
	@ApiExplain("Query a simple contract object")
	public Result<List<SimpleCrmEntity>> querySimpleEntity(@RequestBody List<Integer> ids) {
		List<SimpleCrmEntity> crmEntities = crmContractService.querySimpleEntity(ids);
		return R.ok(crmEntities);
	}

	@PostMapping("/updateInformation")
	@ApiOperation("Basic information is saved and modified")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateInformation(@RequestBody CrmUpdateInformationBO updateInformationBO) {
		crmContractService.updateInformation(updateInformationBO);
		return R.ok();
	}

	@PostMapping("/queryListByProductId")
	@ApiOperation("Query list page data")
	public Result<BasePage<Map<String, Object>>> queryListByProductId(@RequestBody BiParams biParams) {
		BasePage<Map<String, Object>> mapBasePage = crmContractService.queryListByProductId(biParams);
		return R.ok(mapBasePage);
	}
}
