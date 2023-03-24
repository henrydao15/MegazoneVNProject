package com.megazone.crm.common;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.utils.BaseUtil;
import com.megazone.core.utils.TagUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.PO.CrmActionRecord;
import com.megazone.crm.entity.PO.CrmCustomer;
import com.megazone.crm.entity.PO.CrmMarketing;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class ActionRecordUtil {

	static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(10, 20, 5L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(2048), new ThreadPoolExecutor.AbortPolicy());
	public static Map<Integer, Dict> propertiesMap = new HashMap<>();

	static {
		propertiesMap.put(CrmEnum.LEADS.getType(), Dict.create().set("leadsName", "lead name").set("address", "address").set("mobile", "mobile").set("nextTime", "Next Contact Time").set("remark", "Remarks").set("email", "Email").set("telephone", "Telephone"));
		propertiesMap.put(CrmEnum.CUSTOMER.getType(), Dict.create().set("customerName", "customer name").set("address", "province").set("location", " Detailed address").set("mobile", "mobile phone").set("nextTime", "next time").set("remark", "remarks").set("telephone", "telephone").set("website", "URL"));
		propertiesMap.put(CrmEnum.CONTACTS.getType(), Dict.create().set("name", "name").set("customerId", "customer name").set("mobile", "mobile phone").set("nextTime", "Next Contact Time").set("remark", "Remarks").set("telephone", "Telephone").set("email", "Email").set("post", "post").set("address", "address"));
		propertiesMap.put(CrmEnum.BUSINESS.getType(), Dict.create().set("businessName", "Opportunity Name").set(
				"customerId", "Customer Name").set("money", "Opportunity Amount").set("dealDate", "Estimated Closing " +
				"Date").set("remark", "Remarks").set("typeId", "Opportunity Status Group").set("statusId",
				"Opportunity Stage").set("totalPrice", "Total Amount").set("discountRate", "Discount for the whole order (%)"));
		propertiesMap.put(CrmEnum.CONTRACT.getType(), Dict.create().set("num", "Contract ID").set("name", "Contract Name").set("customerId", "Customer Name").set("contactsId", "Customer Contractor").set("businessId", "Opportunity Name").set("orderDate", "Order Time").set("money", "Contract Amount").set("startTime", "Contract Start Time").set("endTime", "Contract End Time").set("companyUserId", "Company Contractor").set("remark", " Remarks").set("totalPrice", "Total Amount").set("discountRate", "Discount for the whole order (%)"));
		propertiesMap.put(CrmEnum.RECEIVABLES.getType(), Dict.create().set("number", "receipt number").set("customerId", "customer name").set("contractId", " Contract No.").set("returnTime", "Return Date").set("money", "Return Amount").set("receivablesPlanId", "Number of Periods").set("remark", " Remark"));
		propertiesMap.put(CrmEnum.RECEIVABLES_PLAN.getType(), Dict.create().set("money", "planned payment amount").set("return_date", "planned payment date").set("return_type ", "Planned payment method").set("remind", "Remind a few days in advance").set("remark", "Remark"));
		propertiesMap.put(CrmEnum.PRODUCT.getType(), Dict.create().set("name", "Product Name").set("categoryId", "Product Type").set("num", "Product code").set("price", "price").set("description", "product description"));
		propertiesMap.put(CrmEnum.MARKETING.getType(), Dict.create().set("marketingName", "Activity Name").set("crmType", "Relation Object").set("relationUserId", "Participation Person").set("marketingType", "Activity Type").set("startTime", "Start Time").set("endTime", "End Time").set("browse", "Views").set("submitNum", "Number of submissions").set("marketingMoney", "campaign budget").set("address", "campaign address").set("synopsis", "campaign profile"));
		propertiesMap.put(CrmEnum.RETURN_VISIT.getType(), Dict.create().set("visitNumber", "Visit Number").set("visitTime", "Visit Time").set("ownerUserId", "Visit Person").set("customerId", "Customer Name").set("contractId", "Contract ID").set("contactsId", "Contacts"));
		propertiesMap.put(CrmEnum.INVOICE.getType(), Dict.create().set("invoiceApplyNumber", "Invoice Application Number").set("customerId", "Customer Name").set("contractId", " Contract Number").set("invoiceMoney", "Invoice Amount").set("invoiceDate", "Invoice Date").set("invoiceType", "Invoice Type").set("remark", "Remarks").set("titleType", "Header Type").set("invoiceTitle", "Invoice Header").set("taxNumber", "Tax Identification Number").set("depositBank", "DepositBank").set("depositAccount", "opening account").set("depositAddress", "billing address").set("contactsName", "contact name").set("contactsTelephone", "contact information").set("contactsAddress", "mailing address"));
	}

	private List<String> textList = new ArrayList<>();
	@Autowired
	private ICrmCustomerService crmCustomerService;

	@SuppressWarnings("unchecked")
	public static Object parseValue(Object value, Integer type, boolean isNeedStr) {
		if (ObjectUtil.isEmpty(value) && !type.equals(FieldEnum.DETAIL_TABLE.getType())) {
			return isNeedStr ? "null" : "";
		}
		FieldEnum fieldEnum = FieldEnum.parse(type);
		switch (fieldEnum) {
			case BOOLEAN_VALUE: {
				return "1".equals(value) ? "Yes" : "No";
			}
			case AREA_POSITION: {
				StringBuilder stringBuilder = new StringBuilder();
				if (value instanceof CharSequence) {
					value = JSON.parseArray(value.toString());
				}
				for (Map<String, Object> map : ((List<Map<String, Object>>) value)) {
					stringBuilder.append(map.get("name")).append(" ");
				}
				return stringBuilder.toString();
			}
			case CURRENT_POSITION: {
				if (value instanceof CharSequence) {
					value = JSON.parseObject(value.toString());
				}
				return Optional.ofNullable(((Map<String, Object>) value).get("address")).orElse("").toString();
			}
			case DATE_INTERVAL: {
				if (value instanceof Collection) {
					value = StrUtil.join(Const.SEPARATOR, value);
				}
				return isNeedStr ? value.toString() : value;
			}
			case SINGLE_USER:
			case USER:
				List<String> ids = StrUtil.splitTrim(value.toString(), Const.SEPARATOR);
				return ids.stream().map(id -> UserCacheUtil.getUserName(Long.valueOf(id))).collect(Collectors.joining(Const.SEPARATOR));
			case STRUCTURE:
				List<String> deptIds = StrUtil.splitTrim(value.toString(), Const.SEPARATOR);
				return deptIds.stream().map(id -> UserCacheUtil.getDeptName(Integer.valueOf(id))).collect(Collectors.joining(Const.SEPARATOR));
			case DETAIL_TABLE: {
				if (value == null) {
					value = new ArrayList<>();
				}
				if (value instanceof String) {
					if ("".equals(value)) {
						value = new ArrayList<>();
					} else {
						value = JSON.parseArray((String) value);
					}
				}
				List<Map<String, Object>> list = new ArrayList<>();
				JSONArray array = new JSONArray((List<Object>) value);
				for (int i = 0; i < array.size(); i++) {
					JSONArray objects = array.getJSONArray(i);
					for (int j = 0; j < objects.size(); j++) {
						Map<String, Object> map = new HashMap<>();
						JSONObject data = objects.getJSONObject(j);
						map.put("name", data.get("name"));
						map.put("value", parseValue(data.get("value"), data.getInteger("type"), false));
						list.add(map);
					}
				}
				return list;
			}
			default:
				return isNeedStr ? value.toString() : value;
		}

	}

	@SuppressWarnings("unchecked")
	public static void parseDetailTable(Object oldFieldValue, Object newFieldValue, String name, Integer type, List<String> textList) {
		List<Map<String, Object>> oldFieldValues = (List<Map<String, Object>>) ActionRecordUtil.parseValue(oldFieldValue, type, false);
		List<Map<String, Object>> newFieldValues = (List<Map<String, Object>>) ActionRecordUtil.parseValue(newFieldValue, type, false);
		int size = oldFieldValues.size() >= newFieldValues.size() ? oldFieldValues.size() : newFieldValues.size();
		for (int i = 0; i < size; i++) {
			Object oldValue = oldFieldValues.size() > i ? oldFieldValues.get(i).get("value") : "";
			Object newValue = newFieldValues.size() > i ? newFieldValues.get(i).get("value") : "";
			if (!Objects.equals(oldValue, newValue)) {
				Map<String, Object> map = oldFieldValues.size() >= newFieldValues.size() ? oldFieldValues.get(i) : newFieldValues.get(i);
				textList.add("Modify " + name + " " + map.get("name") + " from " + oldValue + " to " + newValue + ".");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void updateRecord(Map<String, Object> oldObj, Map<String, Object> newObj, CrmEnum crmEnum, String name, Integer actionId) {
		try {
			CrmActionRecord crmActionRecord = new CrmActionRecord();
			crmActionRecord.setCreateUserId(UserUtil.getUserId());
			crmActionRecord.setCreateTime(new Date());
			searchChange(textList, oldObj, newObj, crmEnum.getType());
			crmActionRecord.setTypes(crmEnum.getType());
			crmActionRecord.setActionId(actionId);
			crmActionRecord.setContent(JSON.toJSONString(textList));
			crmActionRecord.setIpAddress("127.0.0.1");
			crmActionRecord.setDetail(StrUtil.join("", textList));
			crmActionRecord.setBehavior(BehaviorEnum.UPDATE.getType());
			crmActionRecord.setObject(name);
			if (textList.size() > 0) {
				ActionRecordTask actionRecordTask = new ActionRecordTask(crmActionRecord);
				THREAD_POOL.execute(actionRecordTask);
			}
		} finally {
			textList.clear();
		}

	}

	public void addRecord(Integer actionId, CrmEnum crmEnum, String name) {
		CrmActionRecord crmActionRecord = new CrmActionRecord();
		crmActionRecord.setCreateUserId(UserUtil.getUserId());
		crmActionRecord.setCreateTime(new Date());
		crmActionRecord.setTypes(crmEnum.getType());
		crmActionRecord.setActionId(actionId);
		ArrayList<String> strings = new ArrayList<>();
		strings.add("New" + crmEnum.getRemarks());
		crmActionRecord.setContent(JSON.toJSONString(strings));
		crmActionRecord.setIpAddress("127.0.0.1");
		crmActionRecord.setDetail("New" + crmEnum.getRemarks() + ":" + name);
		crmActionRecord.setBehavior(BehaviorEnum.SAVE.getType());
		crmActionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(crmActionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	@SuppressWarnings("unchecked")
	public void updateRecord(List<CrmModelFiledVO> newFieldList, Dict kv) {
		textList.clear();
		if (newFieldList == null) {
			return;
		}
		List<CrmModelFiledVO> oldFieldList = ApplicationContextHolder.getBean(ICrmActionRecordService.class).queryFieldValue(kv);
		newFieldList.forEach(newField -> {
			for (CrmModelFiledVO oldField : oldFieldList) {
				if (oldField.getFieldId().equals(newField.getFieldId())) {
					if (ObjectUtil.isEmpty(oldField.getValue()) && ObjectUtil.isEmpty(newField.getValue())) {
						continue;
					}
					if (Objects.equals(FieldEnum.parse(oldField.getType()), FieldEnum.DETAIL_TABLE)) {
						parseDetailTable(oldField.getValue(), newField.getValue(), oldField.getName(), oldField.getType(), textList);
					} else {
						String oldFieldValue = (String) ActionRecordUtil.parseValue(oldField.getValue(), oldField.getType(), true);
						String newFieldValue = (String) ActionRecordUtil.parseValue(newField.getValue(), newField.getType(), true);
						if (!oldFieldValue.equals(newFieldValue)) {
							textList.add("Modify " + oldField.getName() + " from " + oldFieldValue + " to " + newFieldValue + ".");
						}
					}
				}
			}
		});
	}

	private void searchChange(List<String> textList, Map<String, Object> oldObj, Map<String, Object> newObj, Integer crmTypes) {
		for (String oldKey : oldObj.keySet()) {
			for (String newKey : newObj.keySet()) {
				if (propertiesMap.get(crmTypes).containsKey(oldKey)) {
					Object oldValue = oldObj.get(oldKey);
					Object newValue = newObj.get(newKey);
					if (oldValue instanceof Date) {
						oldValue = DateUtil.formatDateTime((Date) oldValue);
					}
					if (newValue instanceof Date) {
						newValue = DateUtil.formatDateTime((Date) newValue);
					}
					if (ObjectUtil.isEmpty(oldValue) || ("address".equals(oldKey))) {
						oldValue = "null";
					}
					if (ObjectUtil.isEmpty(newValue) || ("address".equals(newKey))) {
						newValue = "null";
					}
					if (oldValue instanceof BigDecimal || newValue instanceof BigDecimal) {
						oldValue = Convert.toBigDecimal(oldValue, new BigDecimal(0)).setScale(2, BigDecimal.ROUND_UP).toString();
						newValue = Convert.toBigDecimal(newValue, new BigDecimal(0)).setScale(2, BigDecimal.ROUND_UP).toString();
					}
					if (newKey.equals(oldKey) && !Objects.equals(oldValue, newValue)) {
						switch (oldKey) {
							case "companyUserId":
								if (!"null".equals(newValue)) {
									newValue = StrUtil.splitTrim(newValue.toString(), Const.SEPARATOR)
											.stream().map(id -> UserCacheUtil.getUserName(Long.valueOf(id))).collect(Collectors.joining(Const.SEPARATOR));
								}
								if (!"null".equals(oldValue)) {
									oldValue = StrUtil.splitTrim(oldValue.toString(), Const.SEPARATOR)
											.stream().map(id -> UserCacheUtil.getUserName(Long.valueOf(id))).collect(Collectors.joining(Const.SEPARATOR));
								}
								break;
							case "customerId":
								if (!"null".equals(newValue)) {
									newValue = ApplicationContextHolder.getBean(ICrmCustomerService.class).getCustomerName(Integer.valueOf(newValue.toString()));
								}
								if (!"null".equals(oldValue)) {
									oldValue = ApplicationContextHolder.getBean(ICrmCustomerService.class).getCustomerName(Integer.valueOf(oldValue.toString()));
								}
								break;
							case "businessId":
								if (!"null".equals(newValue)) {
									newValue = ApplicationContextHolder.getBean(ICrmBusinessService.class).getBusinessName(Integer.parseInt(newValue.toString()));
								}
								if (!"null".equals(oldValue)) {
									oldValue = ApplicationContextHolder.getBean(ICrmBusinessService.class).getBusinessName(Integer.parseInt(oldValue.toString()));
								}
								break;
							case "contractId":
								if (!"null".equals(newValue)) {
									newValue = ApplicationContextHolder.getBean(ICrmContractService.class).getContractName(Integer.parseInt(newValue.toString()));
								}
								if (!"null".equals(oldValue)) {
									oldValue = ApplicationContextHolder.getBean(ICrmContractService.class).getContractName(Integer.parseInt(oldValue.toString()));
								}
								break;
							case "contactsId":
								if (!"null".equals(newValue)) {
									newValue = ApplicationContextHolder.getBean(ICrmContactsService.class).getContactsName(Integer.parseInt(newValue.toString()));
								}
								if (!"null".equals(oldValue)) {
									oldValue = ApplicationContextHolder.getBean(ICrmContactsService.class).getContactsName(Integer.parseInt(oldValue.toString()));
								}
								break;
							case "typeId":
								if (!"null".equals(newValue)) {
									newValue = ApplicationContextHolder.getBean(ICrmBusinessTypeService.class).getBusinessTypeName(Integer.parseInt(newValue.toString()));
								}
								if (!"null".equals(oldValue)) {
									oldValue = ApplicationContextHolder.getBean(ICrmBusinessTypeService.class).getBusinessTypeName(Integer.parseInt(oldValue.toString()));
								}
								break;
							case "statusId":
								if (!"null".equals(newValue)) {
									newValue = ApplicationContextHolder.getBean(ICrmBusinessStatusService.class).getBusinessStatusName(Integer.parseInt(newValue.toString()));
								}
								if (!"null".equals(oldValue)) {
									oldValue = ApplicationContextHolder.getBean(ICrmBusinessStatusService.class).getBusinessStatusName(Integer.parseInt(oldValue.toString()));
								}
								break;
							case "receivablesPlanId":
								if (!"null".equals(newValue)) {
									newValue = ApplicationContextHolder.getBean(ICrmReceivablesPlanService.class).getReceivablesPlanNum(Integer.parseInt(newValue.toString()));
								}
								if (!"null".equals(oldValue)) {
									oldValue = ApplicationContextHolder.getBean(ICrmReceivablesPlanService.class).getReceivablesPlanNum(Integer.parseInt(oldValue.toString()));
								}
								break;
							case "categoryId":
								if (!"null".equals(newValue)) {
									newValue = ApplicationContextHolder.getBean(ICrmProductCategoryService.class).getProductCategoryName(Integer.parseInt(newValue.toString()));
								}
								if (!"null".equals(oldValue)) {
									oldValue = ApplicationContextHolder.getBean(ICrmProductCategoryService.class).getProductCategoryName(Integer.parseInt(oldValue.toString()));
								}
								break;
							case "crmType":
								if (!"null".equals(newValue)) {
									newValue = newValue.equals(1) ? "lead" : "customer";
								}
								if (!"null".equals(oldValue)) {
									oldValue = oldValue.equals(1) ? "lead" : "customer";
								}
								break;
							case "relationUserId":
								if (!"null".equals(newValue)) {
									List<SimpleUser> newList = UserCacheUtil.getSimpleUsers(TagUtil.toLongSet((String) newValue));
									newValue = newList.stream().map(SimpleUser::getRealname).collect(Collectors.joining(","));
								}
								if (!"null".equals(oldValue)) {
									List<SimpleUser> oldList = UserCacheUtil.getSimpleUsers(TagUtil.toLongSet((String) oldValue));
									oldValue = oldList.stream().map(SimpleUser::getRealname).collect(Collectors.joining(","));
								}
								break;
							default:
								break;
						}

						if (ObjectUtil.isEmpty(oldValue)) {
							oldValue = "null";
						}
						if (ObjectUtil.isEmpty(newValue)) {
							newValue = "null";
						}
						textList.add("Modify " + propertiesMap.get(crmTypes).get(oldKey) + " from " + oldValue + " to " + newValue + ".");
					}
				}
			}
		}
	}

	public void publicContentRecord(CrmEnum crmEnum, BehaviorEnum behaviorEnum, Integer actionId, String name, JSONObject record, String value) {
		List<String> textList = new ArrayList<>();
		FieldEnum fieldEnum = FieldEnum.parse(record.getInteger("type"));
		if (fieldEnum == FieldEnum.DETAIL_TABLE) {
			parseDetailTable(value, record.get("value"), record.getString("name"), fieldEnum.getType(), textList);
		} else {
			String oldFieldValue = (String) ActionRecordUtil.parseValue(value, fieldEnum.getType(), true);
			String newFieldValue = (String) ActionRecordUtil.parseValue(record.get("value"), fieldEnum.getType(), true);
			if (!oldFieldValue.equals(newFieldValue)) {
				textList.add("Modify " + record.getString("name") + " from " + oldFieldValue + " to " + newFieldValue + ".");
			}
		}
		if (textList.size() == 0) {
			return;
		}
		CrmActionRecord actionRecord = new CrmActionRecord();
		actionRecord.setCreateUserId(UserUtil.getUserId());
		actionRecord.setCreateTime(new Date());
		actionRecord.setIpAddress(BaseUtil.getIp());
		actionRecord.setTypes(crmEnum.getType());
		actionRecord.setBehavior(behaviorEnum.getType());
		actionRecord.setActionId(actionId);
		actionRecord.setContent(JSON.toJSONString(textList));
		actionRecord.setDetail(StrUtil.join("", textList));
		actionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(actionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}


	public void addConversionRecord(Integer actionId, CrmEnum crmEnum, Long userId, String name) {
		String userName = UserCacheUtil.getUserName(userId);
		CrmActionRecord crmActionRecord = new CrmActionRecord();
		crmActionRecord.setCreateUserId(UserUtil.getUserId());
		crmActionRecord.setCreateTime(new Date());
		crmActionRecord.setTypes(crmEnum.getType());
		crmActionRecord.setActionId(actionId);
		ArrayList<String> strings = new ArrayList<>();
		strings.add("Transfer " + crmEnum.getRemarks() + " to: " + userName);
		crmActionRecord.setContent(JSON.toJSONString(strings));
		crmActionRecord.setIpAddress(BaseUtil.getIp());
		crmActionRecord.setDetail("Transfer " + crmEnum.getRemarks() + ":" + name + " to: " + userName);
		crmActionRecord.setBehavior(BehaviorEnum.CHANGE_OWNER.getType());
		crmActionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(crmActionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public void addIsLockRecord(List<String> ids, CrmEnum crmEnum, Integer isLock) {
		CrmActionRecord crmActionRecord = new CrmActionRecord();
		crmActionRecord.setCreateUserId(UserUtil.getUserId());
		crmActionRecord.setCreateTime(new Date());
		crmActionRecord.setTypes(crmEnum.getType());
		crmActionRecord.setIpAddress(BaseUtil.getIp());
		ArrayList<String> strings = new ArrayList<>();
		if (isLock == 2) {
			strings.add("Lock the client.");
			crmActionRecord.setBehavior(BehaviorEnum.LOCK.getType());
		} else {
			strings.add("Unlock the client.");
			crmActionRecord.setBehavior(BehaviorEnum.UNLOCK.getType());
		}
		crmActionRecord.setContent(JSON.toJSONString(strings));
		for (String actionId : ids) {
			String name = crmCustomerService.lambdaQuery().select(CrmCustomer::getCustomerName).eq(CrmCustomer::getCustomerId, actionId).one().getCustomerName();
			String detail;
			if (isLock == 2) {
				detail = "Customer:" + name + "lock";
			} else {
				detail = "Unlock customer:" + name + "unlock";
			}
			crmActionRecord.setDetail(detail);
			crmActionRecord.setId(null);
			crmActionRecord.setActionId(Integer.valueOf(actionId));
			crmActionRecord.setObject(name);
			ActionRecordTask actionRecordTask = new ActionRecordTask(crmActionRecord);
			THREAD_POOL.execute(actionRecordTask);
		}
	}

	public void addConversionCustomerRecord(Integer actionId, CrmEnum crmEnum, String name) {
		CrmActionRecord crmActionRecord = new CrmActionRecord();
		crmActionRecord.setCreateUserId(UserUtil.getUserId());
		crmActionRecord.setCreateTime(new Date());
		crmActionRecord.setTypes(crmEnum.getType());
		crmActionRecord.setActionId(actionId);
		ArrayList<String> strings = new ArrayList<>();
		strings.add("Convert leads\"" + name + "\" to customers");
		crmActionRecord.setContent(JSON.toJSONString(strings));
		crmActionRecord.setIpAddress(BaseUtil.getIp());
		crmActionRecord.setDetail(strings.get(0));
		crmActionRecord.setBehavior(BehaviorEnum.TRANSFER.getType());
		crmActionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(crmActionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public void addPutIntoTheOpenSeaRecord(Integer actionId, CrmEnum crmEnum, String name) {
		CrmActionRecord crmActionRecord = new CrmActionRecord();
		crmActionRecord.setCreateUserId(UserUtil.getUserId());
		crmActionRecord.setCreateTime(new Date());
		crmActionRecord.setTypes(crmEnum.getType());
		ArrayList<String> strings = new ArrayList<>();
		strings.add("Put customers into the open sea");
		crmActionRecord.setContent(JSON.toJSONString(strings));
		crmActionRecord.setIpAddress(BaseUtil.getIp());
		crmActionRecord.setBehavior(BehaviorEnum.PUT_IN_POOL.getType());
		crmActionRecord.setDetail("Put customer:" + name + "into the open sea");
		crmActionRecord.setId(null);
		crmActionRecord.setActionId(actionId);
		crmActionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(crmActionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public void addDistributionRecord(Integer actionId, CrmEnum crmEnum, Long userId, String name) {

		ArrayList<String> strings = new ArrayList<>();
		CrmActionRecord crmActionRecord = new CrmActionRecord();
		crmActionRecord.setCreateUserId(UserUtil.getUserId());
		crmActionRecord.setCreateTime(new Date());
		crmActionRecord.setTypes(crmEnum.getType());
		crmActionRecord.setActionId(actionId);
		if (userId == null) {
			//receive
			strings.add("Received customer");
			crmActionRecord.setDetail("Received customer: " + name);
			crmActionRecord.setBehavior(BehaviorEnum.RECEIVE.getType());
		} else {
			String userName = UserCacheUtil.getUserName(userId);
			// admin assignment
			strings.add("Assign customer to: " + userName);
			crmActionRecord.setDetail("Assign customer: " + name + " to: " + userName);
			crmActionRecord.setBehavior(BehaviorEnum.DISTRIBUTE.getType());
		}
		crmActionRecord.setContent(JSON.toJSONString(strings));
		crmActionRecord.setIpAddress(BaseUtil.getIp());
		crmActionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(crmActionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public void addDeleteActionRecord(CrmEnum crmEnum, String name, Integer actionId) {
		CrmActionRecord actionRecord = new CrmActionRecord();
		actionRecord.setCreateUserId(UserUtil.getUserId());
		actionRecord.setCreateTime(new Date());
		actionRecord.setIpAddress(BaseUtil.getIp());
		actionRecord.setTypes(crmEnum.getType());
		actionRecord.setBehavior(BehaviorEnum.DELETE.getType());
		actionRecord.setActionId(Integer.valueOf(actionId));
		actionRecord.setDetail("Deleted" + crmEnum.getRemarks() + ":" + name);
		actionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(actionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public void addMemberActionRecord(CrmEnum crmEnum, Integer actionId, Long userId, String name) {
		CrmActionRecord actionRecord = new CrmActionRecord();
		actionRecord.setCreateUserId(UserUtil.getUserId());
		actionRecord.setCreateTime(new Date());
		actionRecord.setIpAddress(BaseUtil.getIp());
		actionRecord.setTypes(crmEnum.getType());
		actionRecord.setBehavior(BehaviorEnum.ADD_MEMBER.getType());
		actionRecord.setActionId(actionId);
		String userName = UserCacheUtil.getUserName(userId);
		actionRecord.setDetail("Added team members to " + crmEnum.getRemarks() + ":" + name + ": " + userName);
		actionRecord.setContent(JSON.toJSONString(Collections.singletonList(actionRecord.getDetail())));
		actionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(actionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public void addMemberActionRecord(CrmEnum crmEnum, Integer actionId, List<Long> userIds, String name) {
		CrmActionRecord actionRecord = new CrmActionRecord();
		actionRecord.setCreateUserId(UserUtil.getUserId());
		actionRecord.setCreateTime(new Date());
		actionRecord.setIpAddress(BaseUtil.getIp());
		actionRecord.setTypes(crmEnum.getType());
		actionRecord.setBehavior(BehaviorEnum.ADD_MEMBER.getType());
		actionRecord.setActionId(actionId);
		String userName = UserCacheUtil.getUserNameList(userIds);
		actionRecord.setDetail("Added team members to " + crmEnum.getRemarks() + ":" + name + ": " + userName);
		actionRecord.setContent(JSON.toJSONString(Collections.singletonList(actionRecord.getDetail())));
		actionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(actionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public void addDeleteMemberActionRecord(CrmEnum crmEnum, Integer actionId, Long userId, boolean isSelf, String name) {
		CrmActionRecord actionRecord = new CrmActionRecord();
		actionRecord.setCreateUserId(UserUtil.getUserId());
		actionRecord.setCreateTime(new Date());
		actionRecord.setIpAddress(BaseUtil.getIp());
		actionRecord.setTypes(crmEnum.getType());
		actionRecord.setActionId(actionId);
		if (isSelf) {
			actionRecord.setBehavior(BehaviorEnum.EXIT_MEMBER.getType());
			actionRecord.setDetail("Exited" + crmEnum.getRemarks() + ":" + name + "Team member");
		} else {
			actionRecord.setBehavior(BehaviorEnum.REMOVE_MEMBER.getType());
			String userName = UserCacheUtil.getUserName(userId);
			actionRecord.setDetail("Removed " + crmEnum.getRemarks() + ": " + name + " team member: " + userName);
		}
		actionRecord.setContent(JSON.toJSONString(Collections.singletonList(actionRecord.getDetail())));
		actionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(actionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public void addExportActionRecord(CrmEnum crmEnum, Integer number) {
		CrmActionRecord actionRecord = new CrmActionRecord();
		actionRecord.setCreateUserId(UserUtil.getUserId());
		actionRecord.setCreateTime(new Date());
		actionRecord.setIpAddress(BaseUtil.getIp());
		actionRecord.setTypes(crmEnum.getType());
		actionRecord.setBehavior(BehaviorEnum.EXCEL_EXPORT.getType());
		actionRecord.setDetail("exported" + number + "bar" + crmEnum.getRemarks());
		ActionRecordTask actionRecordTask = new ActionRecordTask(actionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public void addFollowupActionRecord(Integer crmType, Integer actionId, String name) {
		CrmEnum crmEnum = CrmEnum.parse(crmType);
		CrmActionRecord actionRecord = new CrmActionRecord();
		actionRecord.setCreateUserId(UserUtil.getUserId());
		actionRecord.setCreateTime(new Date());
		actionRecord.setIpAddress(BaseUtil.getIp());
		actionRecord.setTypes(crmEnum.getType());
		actionRecord.setActionId(actionId);
		actionRecord.setBehavior(BehaviorEnum.FOLLOW_UP.getType());
		actionRecord.setDetail("Give" + crmEnum.getRemarks() + ":" + name + "New follow-up record");
		actionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(actionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public void addCancelActionRecord(CrmEnum crmEnum, Integer actionId, String name) {
		CrmActionRecord actionRecord = new CrmActionRecord();
		actionRecord.setCreateUserId(UserUtil.getUserId());
		actionRecord.setCreateTime(new Date());
		actionRecord.setIpAddress(BaseUtil.getIp());
		actionRecord.setTypes(crmEnum.getType());
		actionRecord.setBehavior(BehaviorEnum.CANCEL_EXAMINE.getType());
		actionRecord.setActionId(actionId);
		actionRecord.setDetail("will" + crmEnum.getRemarks() + ":" + name + "void");
		actionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(actionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public void addObjectSaveRecord(CrmEnum crmEnum, Integer actionId, String name) {
		CrmActionRecord actionRecord = new CrmActionRecord();
		actionRecord.setCreateUserId(UserUtil.getUserId());
		actionRecord.setCreateTime(new Date());
		actionRecord.setIpAddress(BaseUtil.getIp());
		actionRecord.setTypes(crmEnum.getType());
		actionRecord.setBehavior(BehaviorEnum.SAVE.getType());
		actionRecord.setActionId(actionId);
		actionRecord.setDetail("New" + crmEnum.getRemarks() + ":" + name);
		actionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(actionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public void addMarketingUpdateStatusRecord(String[] ids, CrmEnum crmEnum, Integer status) {
		CrmActionRecord crmActionRecord = new CrmActionRecord();
		crmActionRecord.setCreateUserId(UserUtil.getUserId());
		crmActionRecord.setCreateTime(new Date());
		crmActionRecord.setTypes(crmEnum.getType());
		crmActionRecord.setIpAddress(BaseUtil.getIp());
		if (status == 1) {
			crmActionRecord.setBehavior(BehaviorEnum.START.getType());
		} else {
			crmActionRecord.setBehavior(BehaviorEnum.STOP.getType());
		}
		for (String actionId : ids) {
			CrmMarketing marketing = ApplicationContextHolder.getBean(ICrmMarketingService.class).getById(actionId);
			String name = marketing.getMarketingName();
			String detail;
			if (status == 1) {
				detail = "Activate:" + name + "enable";
			} else {
				detail = "Activate:" + name + "deactivate";
			}
			crmActionRecord.setDetail(detail);
			crmActionRecord.setId(null);
			crmActionRecord.setActionId(Integer.valueOf(actionId));
			crmActionRecord.setObject(name);
			ActionRecordTask actionRecordTask = new ActionRecordTask(crmActionRecord);
			THREAD_POOL.execute(actionRecordTask);
		}
	}

	public void addOaLogSaveRecord(CrmEnum crmEnum, Integer actionId) {
		CrmActionRecord actionRecord = new CrmActionRecord();
		actionRecord.setCreateUserId(UserUtil.getUserId());
		actionRecord.setCreateTime(new Date());
		actionRecord.setIpAddress(BaseUtil.getIp());
		actionRecord.setTypes(crmEnum.getType());
		actionRecord.setBehavior(BehaviorEnum.SAVE.getType());
		actionRecord.setActionId(actionId);
		actionRecord.setDetail("New" + crmEnum.getRemarks() + ":" + DateUtil.formatDate(new Date()));
		actionRecord.setObject(DateUtil.formatDate(new Date()));
		ActionRecordTask actionRecordTask = new ActionRecordTask(actionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public void addCrmExamineActionRecord(CrmEnum crmEnum, Integer actionId, BehaviorEnum behaviorEnum, String number) {
		CrmActionRecord actionRecord = new CrmActionRecord();
		actionRecord.setCreateUserId(UserUtil.getUserId());
		actionRecord.setCreateTime(new Date());
		actionRecord.setIpAddress(BaseUtil.getIp());
		actionRecord.setTypes(crmEnum.getType());
		actionRecord.setBehavior(behaviorEnum.getType());
		actionRecord.setActionId(actionId);
		String prefix = "";
		switch (behaviorEnum) {
			case SUBMIT_EXAMINE:
				prefix = "Submitted";
				break;
			case RECHECK_EXAMINE:
				prefix = "Withdrawn";
				break;
			case PASS_EXAMINE:
				prefix = "passed";
				break;
			case REJECT_EXAMINE:
				prefix = "rejected";
				break;
			default:
				break;
		}
		actionRecord.setDetail(prefix + crmEnum.getRemarks() + ":" + number);
		actionRecord.setObject(number);
		ActionRecordTask actionRecordTask = new ActionRecordTask(actionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	/**
	 * Generic template, applicable to operation records that do not require special handling
	 *
	 * @param crmEnum
	 * @param actionId
	 * @param behaviorEnum
	 */
	public void addObjectActionRecord(CrmEnum crmEnum, Integer actionId, BehaviorEnum behaviorEnum, String name) {
		CrmActionRecord actionRecord = new CrmActionRecord();
		actionRecord.setCreateUserId(UserUtil.getUserId());
		actionRecord.setCreateTime(new Date());
		actionRecord.setIpAddress(BaseUtil.getIp());
		actionRecord.setTypes(crmEnum.getType());
		actionRecord.setBehavior(behaviorEnum.getType());
		actionRecord.setActionId(actionId);
		String detail;
		switch (behaviorEnum) {
			case CANCEL_EXAMINE:
				detail = "will" + crmEnum.getRemarks() + ":" + name + "void";
				break;
			case FOLLOW_UP:
				detail = "Give" + crmEnum.getRemarks() + ":" + name + "New follow-up record";
				break;
			default:
				detail = behaviorEnum.getName() + "re" + crmEnum.getRemarks() + ":" + name;
				break;
		}
		actionRecord.setDetail(detail);
		actionRecord.setObject(name);
		ActionRecordTask actionRecordTask = new ActionRecordTask(actionRecord);
		THREAD_POOL.execute(actionRecordTask);
	}

	public static class ActionRecordTask implements Runnable {
		private static final Integer BATCH_NUMBER = 1;
		private List<CrmActionRecord> SQL_LIST = new ArrayList<>(1);
		private UserInfo userInfo;

		public ActionRecordTask(CrmActionRecord actionRecord) {
			if (actionRecord != null) {
				SQL_LIST.add(actionRecord);
			}
			userInfo = UserUtil.getUser();
		}

		@Override
		public void run() {
			if (SQL_LIST.size() >= BATCH_NUMBER) {
				List<CrmActionRecord> list = new ArrayList<>(SQL_LIST);

				try {
					UserUtil.setUser(userInfo);
					ApplicationContextHolder.getBean(ICrmActionRecordService.class).save(list.get(0));
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					UserUtil.removeUser();
					SQL_LIST.clear();
				}
			}
		}
	}
}
