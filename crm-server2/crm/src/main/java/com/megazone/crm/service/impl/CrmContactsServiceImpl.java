package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.megazone.core.common.FieldEnum;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.ExcelParseUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.common.ActionRecordUtil;
import com.megazone.crm.common.AuthUtil;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.common.ElasticUtil;
import com.megazone.crm.constant.CrmActivityEnum;
import com.megazone.crm.constant.CrmAuthEnum;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.entity.VO.CrmFieldSortVO;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmContactsMapper;
import com.megazone.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrmContactsServiceImpl extends BaseServiceImpl<CrmContactsMapper, CrmContacts> implements ICrmContactsService, CrmPageService {

	@Autowired
	private ICrmFieldService crmFieldService;

	@Autowired
	private ICrmContactsDataService crmContactsDataService;

	@Autowired
	private ICrmActivityService crmActivityService;

	@Autowired
	private ICrmActionRecordService crmActionRecordService;

	@Autowired
	private ICrmContactsBusinessService crmContactsBusinessService;

	@Autowired
	private ICrmContactsUserStarService crmContactsUserStarService;

	@Autowired
	private ICrmCustomerService crmCustomerService;

	@Autowired
	private ActionRecordUtil actionRecordUtil;

	@Autowired
	private ICrmBusinessService crmBusinessService;

	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Autowired
	private FieldService fieldService;

	/**
	 * Query field configuration
	 *
	 * @param id primary key ID
	 * @return data
	 */
	@Override
	public List<CrmModelFiledVO> queryField(Integer id) {
		return queryField(id, false);
	}

	private List<CrmModelFiledVO> queryField(Integer id, boolean appendInformation) {
		CrmModel crmModel = queryById(id);
		if (id != null) {
			List<JSONObject> customerList = new ArrayList<>();
			if (crmModel.get("customerId") != null) {
				JSONObject customer = new JSONObject();
				customerList.add(customer.fluentPut("customerId", crmModel.get("customerId")).fluentPut("customerName", crmModel.get("customerName")));
			}
			crmModel.put("customerId", customerList);
		}
		List<CrmModelFiledVO> filedVOS = crmFieldService.queryField(crmModel);
		if (appendInformation) {
			List<CrmModelFiledVO> modelFiledVOS = appendInformation(crmModel);
			filedVOS.addAll(modelFiledVOS);
		}
		return filedVOS;
	}

	@Override
	public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id) {
		CrmModel crmModel = queryById(id);
		if (id != null) {
			List<JSONObject> customerList = new ArrayList<>();
			if (crmModel.get("customerId") != null) {
				JSONObject customer = new JSONObject();
				customerList.add(customer.fluentPut("customerId", crmModel.get("customerId")).fluentPut("customerName", crmModel.get("customerName")));
			}
			crmModel.put("customerId", customerList);
		}
		return crmFieldService.queryFormPositionFieldVO(crmModel);
	}

	/**
	 * Paging query
	 *
	 * @param search search
	 * @return data
	 */
	@Override
	public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search) {
		BasePage<Map<String, Object>> basePage = queryList(search, false);
		Long userId = UserUtil.getUserId();
		List<Integer> starIds = crmContactsUserStarService.starList(userId);
		basePage.getList().forEach(map -> {
			map.put("star", starIds.contains((Integer) map.get("contactsId")) ? 1 : 0);
		});
		return basePage;
	}

	/**
	 * Query field configuration
	 *
	 * @param id primary key ID
	 * @return data
	 */
	@Override
	public CrmModel queryById(Integer id) {
		CrmModel crmModel;
		if (id != null) {
			crmModel = getBaseMapper().queryById(id, UserUtil.getUserId());
			crmModel.setLabel(CrmEnum.CONTACTS.getType());
			crmModel.setOwnerUserName(UserCacheUtil.getUserName(crmModel.getOwnerUserId()));
			crmContactsDataService.setDataByBatchId(crmModel);
			List<String> stringList = ApplicationContextHolder.getBean(ICrmRoleFieldService.class).queryNoAuthField(crmModel.getLabel());
			stringList.forEach(crmModel::remove);
		} else {
			crmModel = new CrmModel(CrmEnum.CONTACTS.getType());
		}
		return crmModel;
	}

	/**
	 * Save or add information
	 *
	 * @param crmModel model
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addOrUpdate(CrmContactsSaveBO crmModel, boolean isExcel) {
		CrmContacts crmContacts = BeanUtil.copyProperties(crmModel.getEntity(), CrmContacts.class);
		String batchId = StrUtil.isNotEmpty(crmContacts.getBatchId()) ? crmContacts.getBatchId() : IdUtil.simpleUUID();
		actionRecordUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", batchId).set("dataTableName", "wk_crm_contacts_data"));
		crmContactsDataService.saveData(crmModel.getField(), batchId);
		if (StrUtil.isEmpty(crmContacts.getEmail())) {
			crmContacts.setEmail(null);
		}
		if (crmContacts.getContactsId() != null) {
			actionRecordUtil.updateRecord(BeanUtil.beanToMap(getById(crmContacts.getContactsId())), BeanUtil.beanToMap(crmContacts), CrmEnum.CONTACTS, crmContacts.getName(), crmContacts.getContactsId());
			crmContacts.setUpdateTime(DateUtil.date());
			updateById(crmContacts);
			crmContacts = getById(crmContacts);
			ElasticUtil.batchUpdateEsData(elasticsearchRestTemplate.getClient(), "contacts", crmContacts.getContactsId().toString(), crmContacts.getName());
		} else {
			crmContacts.setCreateTime(DateUtil.date());
			crmContacts.setUpdateTime(DateUtil.date());
			crmContacts.setCreateUserId(UserUtil.getUserId());
			if (crmContacts.getOwnerUserId() == null) {
				crmContacts.setOwnerUserId(UserUtil.getUserId());
			}
			crmContacts.setBatchId(batchId);
			if (crmContacts.getCustomerId() == null) {
				throw new CrmException(CrmCodeEnum.CRM_CONTACTS_DATA_ERROR);
			}
			save(crmContacts);
			if (crmModel.getBusinessId() != null) {
				crmContactsBusinessService.save(crmModel.getBusinessId(), crmContacts.getContactsId());
			}
			crmActivityService.addActivity(2, CrmActivityEnum.CONTACTS, crmContacts.getContactsId());
			actionRecordUtil.addRecord(crmContacts.getContactsId(), CrmEnum.CONTACTS, crmContacts.getName());
		}
		ICrmCustomerService bean = ApplicationContextHolder.getBean(ICrmCustomerService.class);
		CrmCustomer customer = bean.getById(crmContacts.getCustomerId());
		if (customer != null && customer.getContactsId() == null) {
			customer.setContactsId(crmContacts.getContactsId());
			bean.updateById(customer);
		}
		crmModel.setEntity(BeanUtil.beanToMap(crmContacts));
		savePage(crmModel, crmContacts.getContactsId(), isExcel);
	}

	@Override
	public void setOtherField(Map<String, Object> map) {
		String ownerUserName = UserCacheUtil.getUserName((Long) map.get("ownerUserId"));
		map.put("ownerUserName", ownerUserName);
		String createUserName = UserCacheUtil.getUserName((Long) map.get("createUserId"));
		map.put("createUserName", createUserName);
		String customerName = crmCustomerService.getCustomerName((Integer) map.get("customerId"));
		map.put("customerName", customerName);
	}

	@Override
	public Dict getSearchTransferMap() {
		return Dict.create().set("customerId", "customerName").set("customer_id", "customerId");
	}

	/**
	 * delete contact data
	 *
	 * @param ids ids
	 */
	@Override
	public void deleteByIds(List<Integer> ids) {
		for (Integer id : ids) {
			Integer count = ApplicationContextHolder.getBean(ICrmContractService.class).lambdaQuery().eq(CrmContract::getContactsId, id).ne(CrmContract::getCheckStatus, 7).count();
			if (count > 0) {
				throw new CrmException(CrmCodeEnum.CRM_DATA_JOIN_ERROR);
			}
			CrmContacts contacts = getById(id);
			if (contacts != null) {
				// delete custom fields
				crmContactsBusinessService.removeByContactsId(id);
				LambdaUpdateWrapper<CrmCustomer> wrapper = new LambdaUpdateWrapper<>();
				wrapper.set(CrmCustomer::getContactsId, null).eq(CrmCustomer::getContactsId, contacts.getContactsId());
				ApplicationContextHolder.getBean(ICrmCustomerService.class).update(wrapper);
				LambdaUpdateWrapper<CrmBusiness> businessLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
				businessLambdaUpdateWrapper.set(CrmBusiness::getContactsId, null).eq(CrmBusiness::getContactsId, contacts.getContactsId());
				ApplicationContextHolder.getBean(ICrmBusinessService.class).update(businessLambdaUpdateWrapper);
				// delete the follow-up record
				crmActivityService.deleteActivityRecord(CrmActivityEnum.CONTACTS, Collections.singletonList(contacts.getContactsId()));
				//delete field operation record
				crmActionRecordService.deleteActionRecord(CrmEnum.CONTACTS, Collections.singletonList(contacts.getContactsId()));
				//delete contact opportunity association
				ApplicationContextHolder.getBean(ICrmContactsBusinessService.class).removeByContactsId(id);
				// delete custom fields
				crmContactsDataService.deleteByBatchId(Collections.singletonList(contacts.getBatchId()));
				removeById(id);
			}
		}
		deletePage(ids);
	}

	/**
	 * Modify the contact person in charge
	 *
	 * @param changeOwnerUserBO responsible person changes BO
	 */
	@Override
	public void changeOwnerUser(CrmChangeOwnerUserBO changeOwnerUserBO) {
		Long newOwnerUserId = changeOwnerUserBO.getOwnerUserId();
		List<Integer> ids = changeOwnerUserBO.getIds();
		if (ids.size() == 0) {
			return;
		}
		for (Integer id : ids) {
			if (AuthUtil.isChangeOwnerUserAuth(id, CrmEnum.CONTACTS, CrmAuthEnum.EDIT)) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
			CrmContacts contacts = getById(id);
			actionRecordUtil.addConversionRecord(id, CrmEnum.CONTACTS, newOwnerUserId, contacts.getName());
			if (2 == changeOwnerUserBO.getTransferType() && !changeOwnerUserBO.getOwnerUserId().equals(contacts.getOwnerUserId())) {
				ApplicationContextHolder.getBean(ICrmTeamMembersService.class).addSingleMember(getLabel(), contacts.getContactsId(), contacts.getOwnerUserId(), changeOwnerUserBO.getPower(), changeOwnerUserBO.getExpiresTime(), contacts.getName());
			}
			ApplicationContextHolder.getBean(ICrmTeamMembersService.class).deleteMember(getLabel(), new CrmMemberSaveBO(id, newOwnerUserId));
		}
		LambdaUpdateWrapper<CrmContacts> wrapper = new LambdaUpdateWrapper<>();
		wrapper.in(CrmContacts::getContactsId, ids);
		wrapper.set(CrmContacts::getOwnerUserId, newOwnerUserId);
		update(wrapper);
		//modify es
		String ownerUserName = UserCacheUtil.getUserName(newOwnerUserId);
		Map<String, Object> map = new HashMap<>();
		map.put("ownerUserId", newOwnerUserId);
		map.put("ownerUserName", ownerUserName);
		updateField(map, ids);
	}

	@Override
	public void downloadExcel(HttpServletResponse response) throws IOException {
		List<CrmModelFiledVO> crmModelFiledList = queryField(null);
		int k = 0;
		for (int i = 0; i < crmModelFiledList.size(); i++) {
			if (crmModelFiledList.get(i).getFieldName().equals("name")) {
				k = i;
				break;
			}
		}
		crmModelFiledList.add(k + 1, new CrmModelFiledVO("ownerUserId", FieldEnum.TEXT, "responsible person", 1).setIsNull(1));
		ExcelParseUtil.importExcel(new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Contact";
			}
		}, crmModelFiledList, response, "crm");
	}

	@Override
	public void exportExcel(HttpServletResponse response, CrmSearchBO search) {
		List<Map<String, Object>> dataList = queryList(search, true).getList();
		List<CrmFieldSortVO> headList = crmFieldService.queryListHead(getLabel().getType());
		ExcelParseUtil.exportExcel(dataList, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {
				for (String fieldName : headMap.keySet()) {
					record.put(fieldName, ActionRecordUtil.parseValue(record.get(fieldName), headMap.get(fieldName), false));
				}
			}

			@Override
			public String getExcelName() {
				return "Contact";
			}
		}, headList, response);
	}

	@Override
	public void star(Integer id) {
		LambdaQueryWrapper<CrmContactsUserStar> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CrmContactsUserStar::getContactsId, id);
		wrapper.eq(CrmContactsUserStar::getUserId, UserUtil.getUserId());
		CrmContactsUserStar star = crmContactsUserStarService.getOne(wrapper);
		if (star == null) {
			star = new CrmContactsUserStar();
			star.setContactsId(id);
			star.setUserId(UserUtil.getUserId());
			crmContactsUserStarService.save(star);
		} else {
			crmContactsUserStarService.removeById(star.getId());
		}
	}

	@Override
	public List<CrmModelFiledVO> information(Integer contactsId) {
		return queryField(contactsId, true);
	}

	@Override
	public CrmInfoNumVO num(Integer contactsId) {
		CrmContacts crmContacts = getById(contactsId);
		AdminFileService fileService = ApplicationContextHolder.getBean(AdminFileService.class);
		List<CrmField> crmFields = crmFieldService.queryFileField();
		List<String> batchIdList = new ArrayList<>();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmContactsData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmContactsData::getValue);
			wrapper.eq(CrmContactsData::getBatchId, crmContacts.getBatchId());
			wrapper.in(CrmContactsData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			batchIdList.addAll(crmContactsDataService.listObjs(wrapper, Object::toString));
		}
		batchIdList.add(crmContacts.getBatchId());
		batchIdList.addAll(crmActivityService.queryFileBatchId(crmContacts.getCustomerId(), getLabel().getType()));
		Map<String, Object> map = new HashMap<>();
		map.put("contactsId", contactsId);
		CrmInfoNumVO infoNumVO = getBaseMapper().queryNum(map);
		infoNumVO.setFileCount(fileService.queryNum(batchIdList).getData());
		infoNumVO.setMemberCount(ApplicationContextHolder.getBean(ICrmTeamMembersService.class).queryMemberCount(getLabel(), crmContacts.getContactsId(), crmContacts.getOwnerUserId()));
		return infoNumVO;
	}

	/**
	 * Query file list
	 *
	 * @param contactsId id
	 * @return file
	 */
	@Override
	public List<FileEntity> queryFileList(Integer contactsId) {
		List<FileEntity> fileEntityList = new ArrayList<>();
		CrmContacts crmContacts = getById(contactsId);
		AdminFileService fileService = ApplicationContextHolder.getBean(AdminFileService.class);
		fileService.queryFileList(crmContacts.getBatchId()).getData().forEach(fileEntity -> {
			fileEntity.setSource("Attachment upload");
			fileEntity.setReadOnly(0);
			fileEntityList.add(fileEntity);
		});
		List<CrmField> crmFields = crmFieldService.queryFileField();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmContactsData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmContactsData::getValue);
			wrapper.eq(CrmContactsData::getBatchId, crmContacts.getBatchId());
			wrapper.in(CrmContactsData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			List<FileEntity> data = fileService.queryFileList(crmContactsDataService.listObjs(wrapper, Object::toString)).getData();
			data.forEach(fileEntity -> {
				fileEntity.setSource("Contact Details");
				fileEntity.setReadOnly(1);
				fileEntityList.add(fileEntity);
			});
		}
		List<String> stringList = crmActivityService.queryFileBatchId(crmContacts.getContactsId(), getLabel().getType());
		if (stringList.size() > 0) {
			List<FileEntity> data = fileService.queryFileList(stringList).getData();
			data.forEach(fileEntity -> {
				fileEntity.setSource("Follow-up record");
				fileEntity.setReadOnly(1);
				fileEntityList.add(fileEntity);
			});
		}
		return fileEntityList;
	}

	@Override
	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids) {
		if (ids.size() == 0) {
			return new ArrayList<>();
		}
		List<CrmContacts> list = lambdaQuery().select(CrmContacts::getContactsId, CrmContacts::getName).in(CrmContacts::getContactsId, ids).list();
		return list.stream().map(crmContacts -> {
			SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
			simpleCrmEntity.setId(crmContacts.getContactsId());
			simpleCrmEntity.setName(crmContacts.getName());
			return simpleCrmEntity;
		}).collect(Collectors.toList());
	}

	/**
	 * Search field with large search box
	 *
	 * @return fields
	 */
	@Override
	public String[] appendSearch() {
		return new String[]{"name", "telephone", "mobile"};
	}

	/**
	 * Get crm list type
	 *
	 * @return data
	 */
	@Override
	public CrmEnum getLabel() {
		return CrmEnum.CONTACTS;
	}

	/**
	 * Query all fields
	 *
	 * @return data
	 */
	@Override
	public List<CrmModelFiledVO> queryDefaultField() {
		List<CrmModelFiledVO> filedList = crmFieldService.queryField(getLabel().getType());
		filedList.add(new CrmModelFiledVO("lastTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("updateTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("createTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("ownerUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("createUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("teamMemberIds", FieldEnum.USER, 0));
		return filedList;
	}

	@Override
	public String getContactsName(int contactsId) {
		return lambdaQuery().select(CrmContacts::getName).eq(CrmContacts::getContactsId, contactsId).oneOpt()
				.map(CrmContacts::getName).orElse("");
	}


	@Override
	public void updateInformation(CrmUpdateInformationBO updateInformationBO) {
		String batchId = updateInformationBO.getBatchId();
		Integer contactsId = updateInformationBO.getId();
		updateInformationBO.getList().forEach(record -> {
			CrmContacts oldContacts = getById(updateInformationBO.getId());
			uniqueFieldIsAbnormal(record.getString("name"), record.getInteger("fieldId"), record.getString("value"), batchId);
			Map<String, Object> oldContactsMap = BeanUtil.beanToMap(oldContacts);
			if (record.getInteger("fieldType") == 1) {
				Map<String, Object> crmContactsMap = new HashMap<>(oldContactsMap);
				crmContactsMap.put(record.getString("fieldName"), record.get("value"));
				CrmContacts crmContacts = BeanUtil.mapToBean(crmContactsMap, CrmContacts.class, true);
				actionRecordUtil.updateRecord(oldContactsMap, crmContactsMap, CrmEnum.CONTACTS, crmContacts.getName(), crmContacts.getContactsId());
				update().set(StrUtil.toUnderlineCase(record.getString("fieldName")), record.get("value")).eq("contacts_id", updateInformationBO.getId()).update();
				if ("name".equals(record.getString("fieldName"))) {
					ElasticUtil.batchUpdateEsData(elasticsearchRestTemplate.getClient(), "contacts", crmContacts.getContactsId().toString(), crmContacts.getName());
				}
			} else if (record.getInteger("fieldType") == 0 || record.getInteger("fieldType") == 2) {
				CrmContactsData contactsData = crmContactsDataService.lambdaQuery()
						.select(CrmContactsData::getValue, CrmContactsData::getId).eq(CrmContactsData::getFieldId, record.getInteger("fieldId"))
						.eq(CrmContactsData::getBatchId, batchId).one();
				String value = contactsData != null ? contactsData.getValue() : null;
				actionRecordUtil.publicContentRecord(CrmEnum.CONTACTS, BehaviorEnum.UPDATE, contactsId, oldContacts.getName(), record, value);
				String newValue = fieldService.convertObjectValueToString(record.getInteger("type"), record.get("value"), record.getString("value"));
				CrmContactsData crmContactsData = new CrmContactsData();
				crmContactsData.setId(contactsData != null ? contactsData.getId() : null);
				crmContactsData.setFieldId(record.getInteger("fieldId"));
				crmContactsData.setName(record.getString("fieldName"));
				crmContactsData.setValue(newValue);
				crmContactsData.setCreateTime(new Date());
				crmContactsData.setBatchId(batchId);
				crmContactsDataService.saveOrUpdate(crmContactsData);

			}
			updateField(record, contactsId);
		});
		this.lambdaUpdate().set(CrmContacts::getUpdateTime, new Date()).eq(CrmContacts::getContactsId, contactsId).update();
	}

	@Override
	public BasePage<Map<String, Object>> queryBusiness(CrmBusinessPageBO businessPageBO) {
		return getBaseMapper().queryBusiness(businessPageBO.parse(), businessPageBO.getContactsId());
	}

	@Override
	public void relateBusiness(CrmRelateBusinessBO relateBusinessBO) {
		crmContactsBusinessService.lambdaUpdate().eq(CrmContactsBusiness::getContactsId, relateBusinessBO.getContactsId()).remove();
		for (Integer businessId : relateBusinessBO.getBusinessIds()) {
			CrmContactsBusiness crmContactsBusiness = new CrmContactsBusiness();
			crmContactsBusiness.setContactsId(relateBusinessBO.getContactsId());
			crmContactsBusiness.setBusinessId(businessId);
			crmContactsBusinessService.save(crmContactsBusiness);
		}
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void unrelateBusiness(CrmRelateBusinessBO relateBusinessBO) {
		//delete from 72crm_crm_contacts_business where contacts_id = #para(contactsId) and business_id in (#fori(ids))
		crmContactsBusinessService.lambdaUpdate().eq(CrmContactsBusiness::getContactsId, relateBusinessBO.getContactsId())
				.in(CrmContactsBusiness::getBusinessId, relateBusinessBO.getBusinessIds()).remove();
		//UPDATE `72crm_crm_business` SET `contacts_id` = NULL WHERE contacts_id = #para(contactsId) and `business_id` in (#fori(ids))
		crmBusinessService.lambdaUpdate().set(CrmBusiness::getContactsId, null)
				.eq(CrmBusiness::getContactsId, relateBusinessBO.getContactsId())
				.in(CrmBusiness::getBusinessId, relateBusinessBO.getBusinessIds()).update();
	}
}
