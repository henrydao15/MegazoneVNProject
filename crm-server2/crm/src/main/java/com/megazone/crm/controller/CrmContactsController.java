package com.megazone.crm.controller;


import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.common.AuthUtil;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.common.log.CrmContactsLog;
import com.megazone.crm.constant.CrmAuthEnum;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.CrmContacts;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmMembersSelectVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.CrmUploadExcelService;
import com.megazone.crm.service.ICrmContactsService;
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

@RestController
@RequestMapping("/crmContacts")
@Api(tags = "Contact Module Interface")
@SysLog(subModel = SubModelType.CRM_CONTACTS, logClass = CrmContactsLog.class)
public class CrmContactsController {

	@Autowired
	private ICrmContactsService crmContactsService;

	@Autowired
	private ICrmTeamMembersService teamMembersService;

	@PostMapping("/queryById/{contactsId}")
	@ApiOperation("Query by ID")
	public Result<CrmModel> queryById(@PathVariable("contactsId") @ApiParam(name = "id", value = "id") Integer contactsId) {
		Integer number = crmContactsService.lambdaQuery().eq(CrmContacts::getContactsId, contactsId).count();
		if (number == 0) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, "Contact");
		}
		CrmModel model = crmContactsService.queryById(contactsId);
		return R.ok(model);
	}

	@PostMapping("/deleteByIds")
	@ApiOperation("Delete data by ID")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteByIds(@ApiParam(name = "ids", value = "id list") @RequestBody List<Integer> ids) {
		crmContactsService.deleteByIds(ids);
		return R.ok();
	}

	@PostMapping("/queryPageList")
	@ApiOperation("Query list page data")
	public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody CrmSearchBO search) {
		search.setPageType(1);
		search.setSearch(search.getSearch().trim());
		BasePage<Map<String, Object>> mapBasePage = crmContactsService.queryPageList(search);
		return R.ok(mapBasePage);
	}

	@PostMapping("/field")
	@ApiOperation("Query to add required fields")
	public Result<List> queryContactsField(@RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmContactsService.queryField(null));
		}
		return R.ok(crmContactsService.queryFormPositionField(null));
	}

	@PostMapping("/field/{id}")
	@ApiOperation("Query the information required to modify the data")
	public Result<List> queryField(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id, @RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmContactsService.queryField(id));
		}
		return R.ok(crmContactsService.queryFormPositionField(id));
	}

	@PostMapping("/queryBusiness")
	@ApiOperation("Search business opportunities under contacts")
	public Result<BasePage<Map<String, Object>>> queryBusiness(@RequestBody CrmBusinessPageBO businessPageBO) {
		boolean auth = AuthUtil.isCrmAuth(CrmEnum.CONTACTS, businessPageBO.getContactsId(), CrmAuthEnum.READ);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		BasePage<Map<String, Object>> page = crmContactsService.queryBusiness(businessPageBO);
		return R.ok(page);
	}

	@PostMapping("/relateBusiness")
	@ApiOperation("Contact Associate Opportunity")
	public Result relateBusiness(@RequestBody CrmRelateBusinessBO relateBusinessBO) {
		crmContactsService.relateBusiness(relateBusinessBO);
		return R.ok();
	}

	@PostMapping("/unrelateBusiness")
	@ApiOperation("Contact Disassociation Opportunity")
	public Result unrelateBusiness(@RequestBody CrmRelateBusinessBO relateBusinessBO) {
		crmContactsService.unrelateBusiness(relateBusinessBO);
		return R.ok();
	}

	@PostMapping("/changeOwnerUser")
	@ApiOperation("Modify the person in charge")
	@SysLogHandler(behavior = BehaviorEnum.CHANGE_OWNER)
	public Result changeOwnerUser(@RequestBody CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
		crmContactsService.changeOwnerUser(crmChangeOwnerUserBO);
		return R.ok();
	}

	@PostMapping("/batchExportExcel")
	@ApiOperation("Select Export")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Select Export", detail = "Export Contact")
	public void batchExportExcel(@RequestBody @ApiParam(name = "ids", value = "id list") List<Integer> ids, HttpServletResponse response) {
		CrmSearchBO search = new CrmSearchBO();
		search.setPageType(0);
		search.setLabel(CrmEnum.CONTACTS.getType());
		CrmSearchBO.Search entity = new CrmSearchBO.Search();
		entity.setFormType(FieldEnum.TEXT.getFormType());
		entity.setSearchEnum(CrmSearchBO.FieldSearchEnum.ID);
		entity.setValues(ids.stream().map(Object::toString).collect(Collectors.toList()));
		search.getSearchList().add(entity);
		search.setPageType(0);
		crmContactsService.exportExcel(response, search);
	}

	@PostMapping("/allExportExcel")
	@ApiOperation("Export all")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Export All", detail = "Export Contacts")
	public void allExportExcel(@RequestBody CrmSearchBO search, HttpServletResponse response) {
		search.setPageType(0);
		crmContactsService.exportExcel(response, search);
	}

	@PostMapping("/add")
	@ApiOperation("Save data")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#crmModel.entity[name]", detail = "'Added a contact:' + #crmModel.entity[name]")
	public Result add(@RequestBody CrmContactsSaveBO crmModel) {
		crmContactsService.addOrUpdate(crmModel, false);
		return R.ok();
	}

	@PostMapping("/information/{id}")
	@ApiOperation("Query details page information")
	public Result<List<CrmModelFiledVO>> information(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<CrmModelFiledVO> information = crmContactsService.information(id);
		return R.ok(information);
	}

	@PostMapping("/update")
	@ApiOperation("Modify data")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result update(@RequestBody CrmContactsSaveBO crmModel) {
		crmContactsService.addOrUpdate(crmModel, false);
		return R.ok();
	}

	@PostMapping("/downloadExcel")
	@ApiOperation("Download import template")
	public void downloadExcel(HttpServletResponse response) throws IOException {
		crmContactsService.downloadExcel(response);
	}

	@PostMapping("/queryFileList")
	@ApiOperation("Query attachment list")
	public Result<List<FileEntity>> queryFileList(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<FileEntity> fileEntities = crmContactsService.queryFileList(id);
		return R.ok(fileEntities);
	}

	@PostMapping("/num")
	@ApiOperation("Number of detail pages displayed")
	public Result<CrmInfoNumVO> num(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		CrmInfoNumVO infoNumVO = crmContactsService.num(id);
		return R.ok(infoNumVO);
	}

	@PostMapping("/star/{id}")
	@ApiOperation("Contact star")
	public Result star(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
		crmContactsService.star(id);
		return R.ok();
	}

	@PostMapping("/querySimpleEntity")
	@ApiExplain("Query a simple contact object")
	public Result<List<SimpleCrmEntity>> querySimpleEntity(@RequestBody List<Integer> ids) {
		List<SimpleCrmEntity> crmEntities = crmContactsService.querySimpleEntity(ids);
		return R.ok(crmEntities);
	}

	@PostMapping("/uploadExcel")
	@ApiOperation("Import Contacts")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_IMPORT, object = "Import Contact", detail = "Import Contact")
	public Result<Long> uploadExcel(@RequestParam("file") MultipartFile file, @RequestParam("repeatHandling") Integer repeatHandling) {
		UploadExcelBO uploadExcelBO = new UploadExcelBO();
		uploadExcelBO.setUserInfo(UserUtil.getUser());
		uploadExcelBO.setCrmEnum(CrmEnum.CONTACTS);
		uploadExcelBO.setPoolId(null);
		uploadExcelBO.setRepeatHandling(repeatHandling);
		Long messageId = ApplicationContextHolder.getBean(CrmUploadExcelService.class).uploadExcel(file, uploadExcelBO);
		return R.ok(messageId);
	}

	@PostMapping("/updateInformation")
	@ApiOperation("Basic information is saved and modified")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateInformation(@RequestBody CrmUpdateInformationBO updateInformationBO) {
		crmContactsService.updateInformation(updateInformationBO);
		return R.ok();
	}

	@PostMapping("/getMembers/{contactsId}")
	@ApiOperation("Get team members")
	public Result<List<CrmMembersSelectVO>> getMembers(@PathVariable("contactsId") @ApiParam("contact ID") Integer contractId) {
		CrmEnum crmEnum = CrmEnum.CONTACTS;
		CrmContacts contacts = crmContactsService.getById(contractId);
		if (contacts == null) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, crmEnum.getRemarks());
		}
		List<CrmMembersSelectVO> members = teamMembersService.getMembers(crmEnum, contractId, contacts.getOwnerUserId());
		return R.ok(members);
	}

	@PostMapping("/addMembers")
	@ApiOperation("Add team members")
	@SysLogHandler(behavior = BehaviorEnum.ADD_MEMBER)
	public Result addMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.addMember(CrmEnum.CONTACTS, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/updateMembers")
	@ApiOperation("Add team members")
	@SysLogHandler(behavior = BehaviorEnum.ADD_MEMBER)
	public Result updateMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.addMember(CrmEnum.CONTACTS, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/deleteMembers")
	@ApiOperation("Delete team member")
	@SysLogHandler
	public Result deleteMembers(@RequestBody CrmMemberSaveBO crmMemberSaveBO) {
		teamMembersService.deleteMember(CrmEnum.CONTACTS, crmMemberSaveBO);
		return R.ok();
	}

	@PostMapping("/exitTeam/{contactsId}")
	@ApiOperation("Leave Team")
	@SysLogHandler
	public Result exitTeam(@PathVariable("contactsId") @ApiParam("contact ID") Integer contactsId) {
		teamMembersService.exitTeam(CrmEnum.CONTACTS, contactsId);
		return R.ok();
	}
}
