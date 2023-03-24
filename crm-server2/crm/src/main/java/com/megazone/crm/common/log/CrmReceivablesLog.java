package com.megazone.crm.common.log;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.FieldEnum;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.feign.admin.entity.SimpleDept;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.TagUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmChangeOwnerUserBO;
import com.megazone.crm.entity.BO.CrmContractSaveBO;
import com.megazone.crm.entity.BO.CrmUpdateInformationBO;
import com.megazone.crm.entity.PO.CrmReceivables;
import com.megazone.crm.entity.PO.CrmReceivablesData;
import com.megazone.crm.service.ICrmReceivablesDataService;
import com.megazone.crm.service.ICrmReceivablesService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CrmReceivablesLog {


	private SysLogUtil sysLogUtil = ApplicationContextHolder.getBean(SysLogUtil.class);

	private ICrmReceivablesService crmReceivablesService = ApplicationContextHolder.getBean(ICrmReceivablesService.class);

	private AdminService adminService = ApplicationContextHolder.getBean(AdminService.class);

	private AdminFileService adminFileService = ApplicationContextHolder.getBean(AdminFileService.class);

	private ICrmReceivablesDataService crmReceivablesDataService = ApplicationContextHolder.getBean(ICrmReceivablesDataService.class);

	public Content update(CrmContractSaveBO crmModel) {
		CrmReceivables crmReceivables = BeanUtil.copyProperties(crmModel.getEntity(), CrmReceivables.class);
		sysLogUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", crmReceivables.getBatchId()).set("dataTableName", "wk_crm_contract_data"));
		CrmReceivables receivables = crmReceivablesService.getById(crmReceivables.getReceivablesId());
		return sysLogUtil.updateRecord(BeanUtil.beanToMap(receivables), BeanUtil.beanToMap(crmReceivables), CrmEnum.RECEIVABLES, crmReceivables.getNumber());
	}

	public List<Content> deleteByIds(List<Integer> ids) {
		List<Content> contentList = new ArrayList<>();
		for (Integer id : ids) {
			CrmReceivables receivables = crmReceivablesService.getById(id);
			if (receivables != null) {
				contentList.add(sysLogUtil.addDeleteActionRecord(CrmEnum.RECEIVABLES, receivables.getNumber()));
			}
		}
		return contentList;
	}

	public List<Content> changeOwnerUser(CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
		return crmChangeOwnerUserBO.getIds().stream().map(id -> {
			CrmReceivables receivables = crmReceivablesService.getById(id);
			return sysLogUtil.addConversionRecord(CrmEnum.RECEIVABLES, crmChangeOwnerUserBO.getOwnerUserId(), receivables.getNumber());
		}).collect(Collectors.toList());
	}

	public List<Content> updateInformation(CrmUpdateInformationBO updateInformationBO) {
		List<Content> contentList = new ArrayList<>();
		String batchId = updateInformationBO.getBatchId();
		updateInformationBO.getList().forEach(record -> {
			CrmReceivables oldReceivables = crmReceivablesService.getById(updateInformationBO.getId());
			Map<String, Object> oldReceivablesMap = BeanUtil.beanToMap(oldReceivables);
			if (record.getInteger("fieldType") == 1) {
				Map<String, Object> crmReceivablesMap = new HashMap<>(oldReceivablesMap);
				crmReceivablesMap.put(record.getString("fieldName"), record.get("value"));
				CrmReceivables crmReceivables = BeanUtil.mapToBean(crmReceivablesMap, CrmReceivables.class, true);
				contentList.add(sysLogUtil.updateRecord(oldReceivablesMap, crmReceivablesMap, CrmEnum.RECEIVABLES, crmReceivables.getNumber()));
			} else if (record.getInteger("fieldType") == 0 || record.getInteger("fieldType") == 2) {
				String formType = record.getString("formType");
				if (formType == null) {
					return;
				}
				String oldFieldValue = crmReceivablesDataService.lambdaQuery().select(CrmReceivablesData::getValue).eq(CrmReceivablesData::getFieldId, record.getInteger("fieldId"))
						.eq(CrmReceivablesData::getBatchId, batchId).one().getValue();
				String newValue = record.getString("value");
				if (formType.equals(FieldEnum.USER.getFormType()) || formType.equals(FieldEnum.SINGLE_USER.getFormType())) {
					oldFieldValue = UserCacheUtil.getSimpleUsers(TagUtil.toLongSet(oldFieldValue)).stream().map(SimpleUser::getRealname).collect(Collectors.joining(","));
					newValue = UserCacheUtil.getSimpleUsers(TagUtil.toLongSet(record.getString("value"))).stream().map(SimpleUser::getRealname).collect(Collectors.joining(","));
				} else if (formType.equals(FieldEnum.STRUCTURE.getFormType())) {
					oldFieldValue = adminService.queryDeptByIds(TagUtil.toSet(oldFieldValue)).getData().stream().map(SimpleDept::getName).collect(Collectors.joining(","));
					newValue = adminService.queryDeptByIds(TagUtil.toSet(record.getString("value"))).getData().stream().map(SimpleDept::getName).collect(Collectors.joining(","));
				} else if (formType.equals(FieldEnum.FILE.getFormType())) {
					oldFieldValue = adminFileService.queryFileList(oldFieldValue).getData().stream().map(FileEntity::getName).collect(Collectors.joining(","));
					newValue = adminFileService.queryFileList(record.getString("value")).getData().stream().map(FileEntity::getName).collect(Collectors.joining(","));
				}
				String oldValue = StrUtil.isEmpty(oldFieldValue) ? "empty" : oldFieldValue;
				String detail = "Modify " + record.getString("name") + " from " + oldValue + " to " + newValue + ".";
				contentList.add(new Content(oldReceivables.getNumber(), detail, BehaviorEnum.UPDATE));
			}
		});
		return contentList;
	}
}
