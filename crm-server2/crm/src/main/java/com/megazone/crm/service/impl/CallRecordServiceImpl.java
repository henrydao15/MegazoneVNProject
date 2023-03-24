package com.megazone.crm.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.servlet.upload.FileServiceFactory;
import com.megazone.core.servlet.upload.UploadEntity;
import com.megazone.core.servlet.upload.UploadFileEnum;
import com.megazone.core.utils.BaseUtil;
import com.megazone.core.utils.BiTimeUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.entity.BO.CallRecordBO;
import com.megazone.crm.entity.PO.CallRecord;
import com.megazone.crm.entity.PO.CrmContacts;
import com.megazone.crm.entity.PO.CrmCustomer;
import com.megazone.crm.entity.PO.CrmLeads;
import com.megazone.crm.mapper.CallRecordMapper;
import com.megazone.crm.mapper.CrmCustomerMapper;
import com.megazone.crm.service.ICallRecordService;
import com.megazone.crm.service.ICrmContactsService;
import com.megazone.crm.service.ICrmCustomerService;
import com.megazone.crm.service.ICrmLeadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Call record management
 *
 * @author Ian
 */
@Service
public class CallRecordServiceImpl extends BaseServiceImpl<CallRecordMapper, CallRecord> implements ICallRecordService {

	@Autowired
	private ICrmCustomerService crmCustomerService;
	@Autowired
	private ICrmContactsService crmContactsService;
	@Autowired
	private ICrmLeadsService crmLeadsService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdminFileService adminFileService;

	@Autowired
	private CrmCustomerMapper crmCustomerMapper;


	/**
	 * Add call log
	 *
	 * @return
	 */
	@Override
	public CallRecord saveRecord(CallRecord callRecord) {
		if (callRecord.getCallRecordId() != null) {
			// Currently does not support modification
			throw new CrmException(CrmCodeEnum.CRM_CALL_DATA_UPDATE_ERROR);
		}
		String model = null;
		Integer modelId = null;
		CrmCustomer customer = this.getCrmCustomerInfo(callRecord.getNumber(), false);
		if (customer != null) {
			model = "customer";
			modelId = customer.getCustomerId();
		} else {
			CrmContacts crmContacts = this.getCrmContactsInfo(callRecord.getNumber(), false);
			if (crmContacts != null) {
				model = "contacts";
				modelId = crmContacts.getContactsId();
			} else {
				CrmLeads crmLeads = this.getCrmLeadsInfo(callRecord.getNumber(), false);
				if (crmLeads != null) {
					model = "leads";
					modelId = crmLeads.getLeadsId();
				}
			}
		}
		callRecord.setModelId(modelId);
		callRecord.setModel(model);
		//The call duration is equal to the call end time minus the connection time
		if (callRecord.getEndTime() != null && callRecord.getAnswerTime() != null) {
			callRecord.setTalkTime(Long.valueOf(DateUtil.between(callRecord.getEndTime(), callRecord.getAnswerTime(), DateUnit.SECOND)).intValue());
		}
		//The off-hook duration is equal to the connection time minus the dialing time
		if (callRecord.getAnswerTime() != null && callRecord.getStartTime() != null) {
			callRecord.setDialTime(Long.valueOf(DateUtil.between(callRecord.getAnswerTime(), callRecord.getStartTime(), DateUnit.SECOND)).intValue());
		}
		if (callRecord.getOwnerUserId() == null) {
			callRecord.setOwnerUserId(UserUtil.getUserId());
		}
		CallRecord record = baseMapper.queryRecord(callRecord.getNumber(), callRecord.getStartTime(), callRecord.getOwnerUserId());
		if (record != null) {
			return record;
		}
		this.save(callRecord);
		return callRecord;
	}


	/**
	 * Get customers
	 *
	 * @param number
	 * @param isLike
	 * @return com.megazone.crm.entity.PO.CrmCustomer
	 * @date 2020/8/27 15:07
	 **/
	private CrmCustomer getCrmCustomerInfo(String number, boolean isLike) {
		LambdaQueryWrapper<CrmCustomer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.ne(CrmCustomer::getStatus, 3);
		if (isLike) {
			lambdaQueryWrapper.and(wrapper -> wrapper.like(CrmCustomer::getMobile, number).or().like(CrmCustomer::getTelephone, number));
		} else {
			lambdaQueryWrapper.and(wrapper -> wrapper.eq(CrmCustomer::getMobile, number).or().eq(CrmCustomer::getTelephone, number));
		}
		lambdaQueryWrapper.last("limit 1");
		return crmCustomerService.getOne(lambdaQueryWrapper);
	}

	/**
	 * Get contacts
	 *
	 * @param number
	 * @param isLike
	 * @return com.megazone.crm.entity.PO.CrmContacts
	 * @date 2020/8/27 15:07
	 **/
	private CrmContacts getCrmContactsInfo(String number, boolean isLike) {
		LambdaQueryWrapper<CrmContacts> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		if (isLike) {
			lambdaQueryWrapper.like(CrmContacts::getMobile, number).or().like(CrmContacts::getTelephone, number);
		} else {
			lambdaQueryWrapper.eq(CrmContacts::getMobile, number).or().eq(CrmContacts::getTelephone, number);
		}
		lambdaQueryWrapper.last("limit 1");
		return crmContactsService.getOne(lambdaQueryWrapper);
	}

	/**
	 * Get clues
	 *
	 * @param number
	 * @param isLike
	 * @return com.megazone.crm.entity.PO.CrmLeads
	 * @date 2020/8/27 15:07
	 **/
	private CrmLeads getCrmLeadsInfo(String number, boolean isLike) {
		LambdaQueryWrapper<CrmLeads> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		if (isLike) {
			lambdaQueryWrapper.like(CrmLeads::getMobile, number).or().like(CrmLeads::getTelephone, number);
		} else {
			lambdaQueryWrapper.eq(CrmLeads::getMobile, number).or().eq(CrmLeads::getTelephone, number);
		}
		lambdaQueryWrapper.last("limit 1");
		return crmLeadsService.getOne(lambdaQueryWrapper);
	}

	/**
	 * Query the call log list
	 *
	 * @param callRecordBO
	 * @return
	 */
	@Override
	public BasePage<JSONObject> pageCallRecordList(CallRecordBO callRecordBO) {
		BasePage<JSONObject> page = new BasePage<>(callRecordBO.getPage(), callRecordBO.getLimit());
		Long talkTime = callRecordBO.getTalkTime();
		String talkTimeCondition = callRecordBO.getTalkTimeCondition();
		if (talkTime != null && StrUtil.isEmpty(talkTimeCondition)) {
			throw new CrmException(CrmCodeEnum.CRM_CALL_DATA_QUERY_ERROR, "Brush condition");
		}
		Integer menuId = adminService.queryMenuId("bi", "call", "index").getData();
		callRecordBO.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(callRecordBO);
		List<Long> userIds = record.getUserIds();
		if (userIds.isEmpty()) {
			return page;
		}
		return baseMapper.pageCallRecordList(page, record.getSqlDateFormat(), userIds, talkTime, talkTimeCondition, record.getBeginTime(), record.getFinalTime());
	}

	/**
	 * upload files
	 *
	 * @param file   file
	 * @param id     id
	 * @param prefix
	 * @return
	 */
	@Override
	public boolean upload(MultipartFile file, String id, String prefix) {
		if (StrUtil.isEmpty(id)) {
			throw new CrmException(CrmCodeEnum.CRM_CALL_DATA_QUERY_ERROR, "Requested data format");
		}
		//Query call records
		CallRecord record = baseMapper.selectById(id);
		if (record == null) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, "This call record");
		}
		String batchId = StrUtil.isNotEmpty(record.getBatchId()) ? record.getBatchId() : IdUtil.simpleUUID();
		UploadEntity entity;
		if (file != null) {
			entity = new UploadEntity(BaseUtil.getNextId() + "", file.getOriginalFilename(), file.getSize(), batchId, "0");
			try {
				entity = FileServiceFactory.build().uploadFile(file.getInputStream(), entity);
			} catch (IOException e) {
				throw new CrmException(CrmCodeEnum.CRM_CALL_UPLOAD_ERROR);
			}
		} else {
			entity = new UploadEntity();
		}
		CallRecord callRecord = new CallRecord();
		callRecord.setCallRecordId(Integer.valueOf(id));
		callRecord.setFilePath(entity.getPath());
		callRecord.setFileName(entity.getName());
		callRecord.setCallUpload(UploadFileEnum.LOCAL.getConfig().equals(entity.getType()) ? entity.getType() : 0);
		callRecord.setSize(Optional.ofNullable(entity.getSize()).orElse(0L).intValue());
		callRecord.setUpdateTime(DateUtil.date());
		callRecord.setBatchId(batchId);
		return baseMapper.updateById(callRecord) > 0;
	}

	/**
	 * Recording download
	 *
	 * @return
	 */
	@Override
	public void download(String id, HttpServletResponse response) {
		if (StrUtil.isEmpty(id)) {
			throw new CrmException(CrmCodeEnum.CRM_CALL_DATA_QUERY_ERROR, "Requested data format");
		}
		//Query call records
		CallRecord callRecord = baseMapper.selectById(id);
		if (callRecord == null) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, "This call log");
		}
		if (StrUtil.isEmpty(callRecord.getFilePath())) {
			throw new CrmException(CrmCodeEnum.CRM_CALL_DOWNLOAD_ERROR);
		}
		if (Objects.equals(UploadFileEnum.LOCAL.getConfig(), callRecord.getCallUpload())) {
			ServletUtil.write(response, FileUtil.file(callRecord.getFilePath()));
			return;
		}
		UploadEntity entity = new UploadEntity(callRecord.getCallRecordId() + "", callRecord.getFileName(), callRecord.getSize().longValue(), callRecord.getBatchId(), "0");
		entity.setPath(callRecord.getFilePath());
		InputStream inputStream = FileServiceFactory.build().downFile(entity);
		if (inputStream != null) {
			final String contentType = ObjectUtil.defaultIfNull(FileUtil.getMimeType(callRecord.getFileName()), "application/octet-stream");
			ServletUtil.write(response, inputStream, contentType, callRecord.getFileName());
		}
	}


	/**
	 * Search for incoming calls to see if there is a record
	 *
	 * @param search
	 * @return
	 */
	@Override
	public JSONObject searchPhone(String search) {
		JSONObject jsonObject = new JSONObject();
		//return incoming call information
		if (StrUtil.isEmpty(search)) {
			throw new CrmException(CrmCodeEnum.CRM_CALL_DATA_QUERY_ERROR, "condition");
		}
		//Check if there is a current incoming call record in the customer
		CrmCustomer crmCustomer = this.getCrmCustomerInfo(search, true);
		if (crmCustomer != null) {
			jsonObject.put("model", "customer");
			jsonObject.put("customer_id", crmCustomer.getCustomerId());
			jsonObject.put("name", crmCustomer.getCustomerName());
			jsonObject.put("owner_user_id_info", UserCacheUtil.getUserName(crmCustomer.getOwnerUserId()));
			return jsonObject;
		}

		//Check whether there is a current incoming call record in the clue
		CrmLeads leadsRecord = this.getCrmLeadsInfo(search, true);
		if (leadsRecord != null) {
			jsonObject.put("model", "leads");
			jsonObject.put("leads_id", leadsRecord.getLeadsId());
			jsonObject.put("name", leadsRecord.getLeadsName());
			jsonObject.put("owner_user_id_info", UserCacheUtil.getUserName(leadsRecord.getOwnerUserId()));
			return jsonObject;
		}

		//Check whether there is an incoming call record in the contact
		CrmContacts contactsRecord = this.getCrmContactsInfo(search, true);
		if (contactsRecord != null) {
			jsonObject.put("model", "contacts");
			jsonObject.put("contacts_id", contactsRecord.getContactsId());
			jsonObject.put("name", contactsRecord.getName());
			jsonObject.put("owner_user_id_info", UserCacheUtil.getUserName(contactsRecord.getOwnerUserId()));
			return jsonObject;
		}

		return jsonObject;
	}

	/**
	 * Check available phones
	 *
	 * @return
	 */
	@Override
	public List<JSONObject> queryPhoneNumber(String model, String modelId) {
		if (StrUtil.isEmpty(model) || StrUtil.isEmpty(modelId)) {
			throw new CrmException(CrmCodeEnum.CRM_CALL_DATA_QUERY_ERROR, "condition");
		}
		List<JSONObject> recordList = new ArrayList<>();
		//When the current option is the customer, query all the contacts responsible for the customer
		switch (model) {
			case "customer":
				CrmModel crmModel = Optional.ofNullable(crmCustomerMapper.queryById(Integer.valueOf(modelId), UserUtil.getUserId())).orElse(new CrmModel());
				if (crmModel.get("mobile") != null && !"".equals(crmModel.get("mobile"))) {
					JSONObject data = new JSONObject();
					data.put("name", "customer-" + crmModel.get("customerName"));
					data.put("phoneNumber", crmModel.get("mobile"));
					data.put("model", "customer");
					data.put("model_id", modelId);
					recordList.add(data);
				}
				if (crmModel.get("telephone") != null && !"".equals(crmModel.get("telephone"))) {
					JSONObject data = new JSONObject();
					data.put("name", "customer-" + crmModel.get("customerName"));
					data.put("phoneNumber", crmModel.get("telephone"));
					data.put("model", "customer");
					data.put("model_id", modelId);
					recordList.add(data);
				}
				//Query the contact person responsible for the customer
				List<JSONObject> contactsList = baseMapper.queryContactsByCustomerId(Integer.valueOf(modelId));
				if (!contactsList.isEmpty()) {
					for (JSONObject record : contactsList) {
						if (record.get("mobile") != null && !"".equals(record.get("mobile"))) {
							JSONObject data = new JSONObject();
							data.put("name", "contact-" + record.get("name"));
							data.put("phoneNumber", record.get("mobile"));
							data.put("model", "customer");
							data.put("model_id", modelId);
							recordList.add(data);
						}
						if (record.get("telephone") != null && !"".equals(record.get("telephone"))) {
							JSONObject data = new JSONObject();
							data.put("name", "contact-" + record.get("name"));
							data.put("phoneNumber", record.get("telephone"));
							data.put("model", "customer");
							data.put("model_id", modelId);
							recordList.add(data);
						}
					}
				}
				break;
			case "leads": {
				//clue
				CrmLeads crmLeads = Optional.ofNullable(crmLeadsService.getById(modelId)).orElse(new CrmLeads());
				if (StrUtil.isNotEmpty(crmLeads.getMobile())) {
					JSONObject data = new JSONObject();
					data.put("name", "lead-" + crmLeads.getLeadsName());
					data.put("phoneNumber", crmLeads.getMobile());
					data.put("model", "leads");
					data.put("model_id", modelId);
					recordList.add(data);
				}
				if (StrUtil.isNotEmpty(crmLeads.getTelephone())) {
					JSONObject data = new JSONObject();
					data.put("name", "lead-" + crmLeads.getLeadsName());
					data.put("phoneNumber", crmLeads.getTelephone());
					data.put("model", "leads");
					data.put("model_id", modelId);
					recordList.add(data);
				}
				//Get the custom mobile phone number field of the clue
				List<JSONObject> fieldList = baseMapper.searchFieldValueByLeadsId(Integer.valueOf(modelId));
				if (!fieldList.isEmpty()) {
					for (JSONObject record : fieldList) {
						JSONObject data = new JSONObject();
						data.put("name", "leads-" + record.get("leadsName"));
						data.put("phoneNumber", record.get("value"));
						data.put("model", "leads");
						data.put("model_id", modelId);
						recordList.add(data);
					}
				}
				break;
			}
			case "contacts": {
				//contact
				CrmContacts crmContacts = Optional.ofNullable(crmContactsService.getById(modelId)).orElse(new CrmContacts());
				if (StrUtil.isNotEmpty(crmContacts.getMobile())) {
					JSONObject data = new JSONObject();
					data.put("name", "Contact-" + crmContacts.getName());
					data.put("phoneNumber", crmContacts.getMobile());
					data.put("model", "contacts");
					data.put("model_id", modelId);
					recordList.add(data);
				}
				if (StrUtil.isNotEmpty(crmContacts.getTelephone())) {
					JSONObject data = new JSONObject();
					data.put("name", "Contact-" + crmContacts.getName());
					data.put("phoneNumber", crmContacts.getTelephone());
					data.put("model", "contacts");
					data.put("model_id", modelId);
					recordList.add(data);
				}

				//Get the custom mobile phone number field of the contact
				List<JSONObject> fieldList = baseMapper.searchFieldValueByContactsId(Integer.valueOf(modelId));
				if (!fieldList.isEmpty()) {
					for (JSONObject record : fieldList) {
						JSONObject data = new JSONObject();
						data.put("name", "contact-" + record.get("name"));
						data.put("phoneNumber", record.get("value"));
						data.put("model", "contacts");
						data.put("model_id", modelId);
						recordList.add(data);
					}
				}
				break;
			}
			default:
				break;
		}
		return recordList;
	}


	/**
	 * Call log analysis
	 * year: this year; lastYear: last year; quarter: this quarter; lastQuarter: last quarter; month: this month; custom time: such as start_time: 2019-04-19; end_time: 2019-04-22
	 * user_id no int user id
	 *
	 * @return
	 */
	@Override
	public BasePage<JSONObject> analysis(BiParams biParams) {
		BasePage<JSONObject> page = new BasePage<>(biParams.getPage(), biParams.getLimit());
		Integer menuId = adminService.queryMenuId("bi", "call", "analysis").getData();
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity record = BiTimeUtil.analyzeType(biParams);
		List<Long> userIds = record.getUserIds();
		if (userIds.isEmpty()) {
			return page;
		}
		BasePage<JSONObject> recordPage = baseMapper.analysis(page, userIds, record.getSqlDateFormat(), record.getBeginTime(), record.getFinalTime());
		recordPage.getList().forEach(recordObj -> {
			recordObj.put("user_info", adminService.getUserInfo(recordObj.getLong("ownerUserId")).getData());
		});
		return recordPage;
	}
}
