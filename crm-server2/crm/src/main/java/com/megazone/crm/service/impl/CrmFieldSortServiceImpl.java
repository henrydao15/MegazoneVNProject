package com.megazone.crm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.common.FieldEnum;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.PO.CrmField;
import com.megazone.crm.entity.PO.CrmFieldSort;
import com.megazone.crm.entity.VO.CrmFieldSortVO;
import com.megazone.crm.mapper.CrmFieldSortMapper;
import com.megazone.crm.service.ICrmFieldService;
import com.megazone.crm.service.ICrmFieldSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Field sorting table Service implementation class
 * </p>
 *
 * @author
 * @since 2020-05-19
 */
@Service
public class CrmFieldSortServiceImpl extends BaseServiceImpl<CrmFieldSortMapper, CrmFieldSort> implements ICrmFieldSortService {

	@Autowired
	private ICrmFieldService crmFieldService;

	/**
	 * Query module field list
	 *
	 * @param label label
	 * @return data
	 */
	@Override
	public List<CrmFieldSortVO> queryListHead(Integer label) {
		Long userId = UserUtil.getUserId();
		QueryWrapper<CrmFieldSort> wrapper = new QueryWrapper<>();
		wrapper.eq("user_id", userId).eq("label", label);
		int number = count(wrapper);
		if (number == 0) {
			saveUserFieldSort(label, userId);
		}
		return getBaseMapper().queryListHead(label, userId);
	}

	@Override
	public List<CrmFieldSort> queryAllFieldSortList(Integer label, Long userId) {
		List<CrmField> crmFieldList = crmFieldService.list(label, false);
		CrmEnum crmEnum = CrmEnum.parse(label);
		switch (crmEnum) {
			case CUSTOMER:
				crmFieldList.add(new CrmField("status", "Lock status", FieldEnum.NUMBER));
				crmFieldList.add(new CrmField("dealStatus", "Status", FieldEnum.SELECT));
				crmFieldList.add(new CrmField("lastTime", "Last follow-up time", FieldEnum.DATETIME));
				crmFieldList.add(new CrmField("lastContent", "Last follow-up record", FieldEnum.TEXTAREA));
				crmFieldList.add(new CrmField("detailAddress", "Address", FieldEnum.TEXT));
				crmFieldList.add(new CrmField("address", "Region", FieldEnum.MAP_ADDRESS));
				crmFieldList.add(new CrmField("poolDay", "Days to enter the open sea", FieldEnum.NUMBER));
				crmFieldList.add(new CrmField("ownerUserName", "PIC", FieldEnum.TEXT));
				crmFieldList.add(new CrmField("teamMemberIds", "Related Teams", FieldEnum.USER));
				break;
			case BUSINESS:
				crmFieldList.add(new CrmField("typeName", "Business Group", FieldEnum.SELECT));
				crmFieldList.add(new CrmField("statusName", "Status", FieldEnum.SELECT));
				crmFieldList.add(new CrmField("lastTime", "Last follow-up time", FieldEnum.DATETIME));
				crmFieldList.add(new CrmField("ownerUserName", "PIC", FieldEnum.TEXT));
				crmFieldList.add(new CrmField("teamMemberIds", "Related Teams", FieldEnum.USER));
				break;
			case CONTRACT:
				crmFieldList.add(new CrmField("checkStatus", "Review Status", FieldEnum.NUMBER));
				crmFieldList.add(new CrmField("receivedMoney", "Received amount", FieldEnum.FLOATNUMBER));
				crmFieldList.add(new CrmField("unreceivedMoney", "Missing amount", FieldEnum.FLOATNUMBER));
				crmFieldList.add(new CrmField("lastTime", "Last follow-up time", FieldEnum.DATETIME));
				crmFieldList.add(new CrmField("ownerUserName", "PIC", FieldEnum.TEXT));
				crmFieldList.add(new CrmField("teamMemberIds", "Related Teams", FieldEnum.USER));
				break;
			case RECEIVABLES:
				crmFieldList.add(new CrmField("checkStatus", "Review Status", FieldEnum.NUMBER));
				crmFieldList.add(new CrmField("contractMoney", "Contract Amount", FieldEnum.FLOATNUMBER));
				crmFieldList.add(new CrmField("ownerUserName", "PIC", FieldEnum.TEXT));
				crmFieldList.add(new CrmField("teamMemberIds", "Related Teams", FieldEnum.USER));
				break;
			case RECEIVABLES_PLAN:
				crmFieldList.add(new CrmField("num", "Number of Periods", FieldEnum.TEXT));
				crmFieldList.add(new CrmField("ownerUserName", "PIC", FieldEnum.TEXT));
				crmFieldList.add(new CrmField("realReceivedMoney", "Actual Receipt Amount", FieldEnum.FLOATNUMBER));
				crmFieldList.add(new CrmField("realReturnDate", "Actual collection time", FieldEnum.DATETIME));
				crmFieldList.add(new CrmField("unreceivedMoney", "Missing Amount", FieldEnum.FLOATNUMBER));
				crmFieldList.add(new CrmField("receivedStatus", "Receipt Status", FieldEnum.SELECT));
				break;
			case LEADS:
				crmFieldList.add(new CrmField("lastTime", "last follow-up time", FieldEnum.DATE));
				crmFieldList.add(new CrmField("lastContent", "Last follow up record", FieldEnum.TEXTAREA));
				crmFieldList.add(new CrmField("ownerUserName", "PIC", FieldEnum.TEXT));
				break;
			case PRODUCT:
				crmFieldList.add(new CrmField("status", "Status", FieldEnum.NUMBER));
				crmFieldList.add(new CrmField("ownerUserName", "PIC", FieldEnum.TEXT));
				break;
			case CONTACTS:
				crmFieldList.add(new CrmField("lastTime", "Last follow-up time", FieldEnum.DATETIME));
				crmFieldList.add(new CrmField("ownerUserName", "PIC", FieldEnum.TEXT));
				crmFieldList.add(new CrmField("teamMemberIds", "Related Teams", FieldEnum.USER));
				break;
			case CUSTOMER_POOL:
				crmFieldList.add(new CrmField("lastContent", "Last follow-up record", FieldEnum.TEXTAREA));
				crmFieldList.add(new CrmField("ownerUserName", "PIC", FieldEnum.TEXT));
				break;
			case RETURN_VISIT:
				break;
			case INVOICE:
				crmFieldList.add(new CrmField("ownerUserName", "PIC", FieldEnum.TEXT));
				crmFieldList.add(new CrmField("checkStatus", "Review Status", FieldEnum.NUMBER));
				crmFieldList.add(new CrmField("invoiceStatus", "Invoice Status", FieldEnum.NUMBER));
				crmFieldList.add(new CrmField("invoiceNumber", "Invoice Number", FieldEnum.TEXT));
				crmFieldList.add(new CrmField("realInvoiceDate", "Actual Invoice Date", FieldEnum.DATE));
				crmFieldList.add(new CrmField("logisticsNumber", "Logistics Number", FieldEnum.TEXT));
			default:
				break;
		}
		if (!CrmEnum.RECEIVABLES_PLAN.getType().equals(label) && !CrmEnum.RETURN_VISIT.getType().equals(label)) {
			crmFieldList.add(new CrmField("ownerDeptName", "Department", FieldEnum.TEXT));
		}
		crmFieldList.add(new CrmField("updateTime", "Update time", FieldEnum.DATETIME));
		crmFieldList.add(new CrmField("createTime", "Create time", FieldEnum.DATETIME));
		crmFieldList.add(new CrmField("createUserName", "Creator", FieldEnum.TEXT));

		List<CrmFieldSort> fieldSortList = new ArrayList<>();
		for (int i = 0; i < crmFieldList.size(); i++) {
			CrmFieldSort fieldSort = new CrmFieldSort();
			fieldSort.setFieldId(crmFieldList.get(i).getFieldId());
			fieldSort.setFieldName(parseFieldName(crmFieldList.get(i).getFieldName()));
			fieldSort.setName(crmFieldList.get(i).getName());
			fieldSort.setSort(i);
			fieldSort.setUserId(userId);
			fieldSort.setStyle(100);
			fieldSort.setIsHide(0);
			fieldSort.setLabel(label);
			fieldSort.setType(crmFieldList.get(i).getType());
			fieldSortList.add(fieldSort);
		}
		return fieldSortList;
	}

	/**
	 * Save user sort
	 *
	 * @param label  label
	 * @param userId User ID
	 */
	private void saveUserFieldSort(Integer label, Long userId) {
		List<CrmFieldSort> fieldSortList = queryAllFieldSortList(label, userId);
		saveBatch(fieldSortList, Const.BATCH_SAVE_SIZE);
	}

	private String parseFieldName(String fieldName) {
		if ("contract_id".equals(fieldName)) {
			return "contractNum";
		}
		if ("receivables_plan_id".equals(fieldName)) {
			return "planNum";
		}
		if (fieldName.endsWith("_id")) {
			fieldName = fieldName.substring(0, fieldName.lastIndexOf("_id")).concat("_name");
		}
		return StrUtil.toCamelCase(fieldName);
	}
}
