package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.megazone.core.common.*;
import com.megazone.core.exception.CrmException;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.constant.CrmSceneEnum;
import com.megazone.crm.entity.BO.CrmSceneConfigBO;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmSceneMapper;
import com.megazone.crm.service.ICrmBusinessTypeService;
import com.megazone.crm.service.ICrmFieldService;
import com.megazone.crm.service.ICrmSceneDefaultService;
import com.megazone.crm.service.ICrmSceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CrmSceneServiceImpl extends BaseServiceImpl<CrmSceneMapper, CrmScene> implements ICrmSceneService {

	@Autowired
	private ICrmSceneDefaultService crmSceneDefaultService;

	@Autowired
	private ICrmFieldService crmFieldService;

	@Override
	public List<CrmScene> queryScene(CrmEnum crmEnum) {
		Long userId = UserUtil.getUserId();
		List<CrmScene> sceneList = new ArrayList<>();
		//Query whether there is a system scene under userId, if not, insert
		Integer number = lambdaQuery().eq(CrmScene::getIsSystem, 1).eq(CrmScene::getType, crmEnum.getType()).eq(CrmScene::getUserId, UserUtil.getUserId()).count();
		if (number.equals(0)) {
			if (CrmEnum.LEADS == crmEnum) {
				sceneList.add(new CrmScene(crmEnum.getType(), "All leads", userId, 0, "", 0, 1, CrmSceneEnum.ALL.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "My leads", userId, 0, "", 0, 1, CrmSceneEnum.SELF.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "Subordinate's leads", userId, 0, "", 0, 1, CrmSceneEnum.CHILD.getName()));
				JSONArray array = new JSONArray();
				array.add(new JSONObject().fluentPut("name", "isTransform").fluentPut("type", 1).fluentPut("values", Collections.singletonList("1")));
				sceneList.add(new CrmScene(crmEnum.getType(), "Transformed leads", userId, 0, array.toJSONString(), 0, 1, "transform"));
				sceneList.add(new CrmScene(crmEnum.getType(), "Leads followed", userId, 0, array.toJSONString(), 0, 1, CrmSceneEnum.STAR.getName()));
			} else if (CrmEnum.CUSTOMER == crmEnum) {
				sceneList.add(new CrmScene(crmEnum.getType(), "All customers", userId, 0, "", 0, 1, CrmSceneEnum.ALL.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "My customers", userId, 0, "", 0, 1, CrmSceneEnum.SELF.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "Subordinate's customers", userId, 0, "", 0, 1, CrmSceneEnum.CHILD.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "Customers followed", userId, 0, "", 0, 1, CrmSceneEnum.STAR.getName()));
			} else if (CrmEnum.CONTACTS == crmEnum) {
				sceneList.add(new CrmScene(crmEnum.getType(), "All contacts", userId, 0, "", 0, 1, CrmSceneEnum.ALL.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "My contacts", userId, 0, "", 0, 1, CrmSceneEnum.SELF.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "Subordinate's contacts", userId, 0, "", 0, 1, CrmSceneEnum.CHILD.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "Contacts followed", userId, 0, "", 0, 1, CrmSceneEnum.STAR.getName()));
			} else if (CrmEnum.PRODUCT == crmEnum) {
				sceneList.add(new CrmScene(crmEnum.getType(), "All Products", userId, 0, "", 0, 1, CrmSceneEnum.ALL.getName()));
				JSONArray array = new JSONArray();
				array.add(new JSONObject().fluentPut("name", "status").fluentPut("type", 1).fluentPut("values", Collections.singletonList(1)));
				sceneList.add(new CrmScene(crmEnum.getType(), "Listed products", userId, 0, array.toJSONString(), 0, 1, ""));
				array.clear();
				array.add(new JSONObject().fluentPut("name", "status").fluentPut("type", 1).fluentPut("values", Collections.singletonList(0)));
				sceneList.add(new CrmScene(crmEnum.getType(), "Removed product", userId, 0, array.toJSONString(), 0, 1, ""));
			} else if (CrmEnum.BUSINESS == crmEnum) {
				sceneList.add(new CrmScene(crmEnum.getType(), "All businesses", userId, 0, "", 0, 1, CrmSceneEnum.ALL.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "My businesses", userId, 0, "", 0, 1, CrmSceneEnum.SELF.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "Subordinate's businesses", userId, 0, "", 0, 1, CrmSceneEnum.CHILD.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "Businesses followed", userId, 0, "", 0, 1, CrmSceneEnum.STAR.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "Winning businesses", userId, 0, new JSONArray().fluentAdd(new JSONObject().fluentPut("name", "isEnd").fluentPut("type ", 1).fluentPut("values", Collections.singletonList(1))).toJSONString(), 0, 1, ""));
				sceneList.add(new CrmScene(crmEnum.getType(), "Losing businesses", userId, 0, new JSONArray().fluentAdd(new JSONObject().fluentPut("name", "isEnd").fluentPut("type ", 1).fluentPut("values", Collections.singletonList(2))).toJSONString(), 0, 1, ""));
				sceneList.add(new CrmScene(crmEnum.getType(), "Invalid businesses", userId, 0, new JSONArray().fluentAdd(new JSONObject().fluentPut("name", "isEnd").fluentPut("type", 1).fluentPut("values", Collections.singletonList(3))).toJSONString(), 0, 1, ""));
				sceneList.add(new CrmScene(crmEnum.getType(), "Ongoing businesses", userId, 0, new JSONArray().fluentAdd(new JSONObject().fluentPut("name", "isEnd").fluentPut(" type", 1).fluentPut("values", Collections.singletonList(0))).toJSONString(), 0, 1, ""));
			} else if (CrmEnum.CONTRACT == crmEnum) {
				sceneList.add(new CrmScene(crmEnum.getType(), "All contracts", userId, 0, "", 0, 1, CrmSceneEnum.ALL.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "My contracts", userId, 0, "", 0, 1, CrmSceneEnum.SELF.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "Subordinate's contracts", userId, 0, "", 0, 1, CrmSceneEnum.CHILD.getName()));
			} else if (CrmEnum.RECEIVABLES == crmEnum) {
				sceneList.add(new CrmScene(crmEnum.getType(), "All payments", userId, 0, "", 0, 1, CrmSceneEnum.ALL.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "My payments", userId, 0, "", 0, 1, CrmSceneEnum.SELF.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "Subordinate's payments", userId, 0, "", 0, 1, CrmSceneEnum.CHILD.getName()));
			} else if (CrmEnum.RECEIVABLES_PLAN == crmEnum) {
				sceneList.add(new CrmScene(crmEnum.getType(), "All payment plans", userId, 0, "", 0, 1, CrmSceneEnum.ALL.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "My payment plans", userId, 1, "", 0, 1, CrmSceneEnum.SELF.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "Subordinate's payment plans'", userId, 2, "", 0, 1, CrmSceneEnum.CHILD.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "All overdue payments", userId, 0, new JSONArray().fluentAdd(new JSONObject().fluentPut("name", "receivedStatus").fluentPut("type", 1).fluentPut("values", Collections.singletonList(4))).toJSONString(), 0, 1, ""));
				sceneList.add(new CrmScene(crmEnum.getType(), "This month's payments", userId, 0, new JSONArray().fluentAdd(new JSONObject().fluentPut("name", "receivedStatus").fluentPut("type", 1).fluentPut("values", Collections.singletonList(4))).fluentAdd(new JSONObject().fluentPut("name", "returnDate").fluentPut("type", 14).fluentPut("values", Collections.singletonList("month"))).toJSONString(), 0, 1, ""));
				sceneList.add(new CrmScene(crmEnum.getType(), "This week's payments", userId, 0, new JSONArray().fluentAdd(new JSONObject().fluentPut("name", "receivedStatus").fluentPut("type", 1).fluentPut("values", Collections.singletonList(4))).fluentAdd(new JSONObject().fluentPut("name", "returnDate").fluentPut("type", 14).fluentPut("values", Collections.singletonList("week"))).toJSONString(), 0, 1, ""));
				sceneList.add(new CrmScene(crmEnum.getType(), "Received payment", userId, 0, new JSONArray().fluentAdd(new JSONObject().fluentPut("name", "receivedStatus").fluentPut("type ", 1).fluentPut("values", Collections.singletonList(1))).toJSONString(), 0, 1, ""));
				sceneList.add(new CrmScene(crmEnum.getType(), "This month's payments (received)", userId, 0, new JSONArray().fluentAdd(new JSONObject().fluentPut("name", "receivedStatus").fluentPut("type", 1).fluentPut("values", Collections.singletonList(1))).fluentAdd(new JSONObject().fluentPut("name", "returnDate").fluentPut("type", 14).fluentPut("values", Collections.singletonList("month"))).toJSONString(), 0, 1, ""));
				sceneList.add(new CrmScene(crmEnum.getType(), "This week's payments (received)", userId, 0, new JSONArray().fluentAdd(new JSONObject().fluentPut("name", "receivedStatus").fluentPut("type", 1).fluentPut("values", Collections.singletonList(1))).fluentAdd(new JSONObject().fluentPut("name", "returnDate").fluentPut("type", 14).fluentPut("values", Collections.singletonList("week"))).toJSONString(), 0, 1, ""));
				sceneList.add(new CrmScene(crmEnum.getType(), "Partial payments", userId, 0, new JSONArray().fluentAdd(new JSONObject().fluentPut("name", "receivedStatus").fluentPut("type ", 1).fluentPut("values", Collections.singletonList(2))).toJSONString(), 0, 1, ""));
			} else if (CrmEnum.RETURN_VISIT == crmEnum) {
				sceneList.add(new CrmScene(crmEnum.getType(), "All return visits", userId, 0, "", 0, 1, CrmSceneEnum.ALL.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "My return visits", userId, 0, "", 0, 1, CrmSceneEnum.SELF.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "Subordinate's return visits", userId, 0, "", 0, 1, CrmSceneEnum.CHILD.getName()));
			} else if (CrmEnum.INVOICE == crmEnum) {
				sceneList.add(new CrmScene(crmEnum.getType(), "All invoices", userId, 0, "", 0, 1, CrmSceneEnum.ALL.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "My invoices", userId, 0, "", 0, 1, CrmSceneEnum.SELF.getName()));
				sceneList.add(new CrmScene(crmEnum.getType(), "Subordinate's invoices", userId, 0, "", 0, 1, CrmSceneEnum.CHILD.getName()));
			}
			saveBatch(sceneList, Const.BATCH_SAVE_SIZE);
		} else {
			sceneList.addAll(lambdaQuery().eq(CrmScene::getType, crmEnum.getType()).eq(CrmScene::getUserId, UserUtil.getUserId()).eq(CrmScene::getIsHide, 0).list());
		}
		List<CrmSceneDefault> defaults = crmSceneDefaultService.lambdaQuery().eq(CrmSceneDefault::getType, crmEnum.getType()).eq(CrmSceneDefault::getUserId, userId).list();
		for (CrmSceneDefault sceneDefault : defaults) {
			Integer sceneId = sceneDefault.getSceneId();
			for (CrmScene crmScene : sceneList) {
				if (Objects.equals(sceneId, crmScene.getSceneId())) {
					crmScene.setIsDefault(1);
				}
			}
		}
		return sceneList;
	}

	@Override
	public List<CrmModelFiledVO> queryField(Integer label) {
		LambdaQueryWrapper<CrmField> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CrmField::getLabel, label);
		wrapper.eq(CrmField::getFieldType, 1);
		wrapper.eq(CrmField::getIsHidden, 0);
		wrapper.select(CrmField::getOptions, CrmField::getType, CrmField::getName, CrmField::getFieldName, CrmField::getPrecisions);
		List<CrmModelFiledVO> fieldList = crmFieldService.list(wrapper).stream()
				.map(field -> {
					CrmModelFiledVO filedVO = BeanUtil.copyProperties(field, CrmModelFiledVO.class);
					if (ListUtil.toList(14, 15, 16, 17, 20).contains(filedVO.getType())) {
						filedVO.setType(1);
					}
					crmFieldService.recordToFormType(filedVO, FieldEnum.parse(filedVO.getType()));
					return filedVO;
				}).collect(Collectors.toList());
		CrmEnum crmEnum = CrmEnum.parse(label);
		if (crmEnum == CrmEnum.NULL) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}
		if (CrmEnum.RETURN_VISIT != crmEnum) {
			fieldList.add(new CrmModelFiledVO("owner_user_id", FieldEnum.USER, "PIC", 1));
			fieldList.add(new CrmModelFiledVO("owner_dept_id", FieldEnum.STRUCTURE, "Department", 1));
		}
		fieldList.add(new CrmModelFiledVO("create_user_id", FieldEnum.USER, "Creator", 1));
		fieldList.add(new CrmModelFiledVO("update_time", FieldEnum.DATETIME, "Update time", 1));
		fieldList.add(new CrmModelFiledVO("create_time", FieldEnum.DATETIME, "Created time", 1));
		if (CrmEnum.LEADS == crmEnum) {
			fieldList.add(new CrmModelFiledVO("last_time", FieldEnum.DATETIME, "Last follow-up time", 1));
		} else if (CrmEnum.CUSTOMER == crmEnum) {
			List<Object> dealStatusList = new ArrayList<>();
			dealStatusList.add(new JSONObject().fluentPut("name", "Not dealt").fluentPut("value", 0));
			dealStatusList.add(new JSONObject().fluentPut("name", "Dealed").fluentPut("value", 1));
			fieldList.add(new CrmModelFiledVO("deal_status", FieldEnum.BUSINESS, "Deal Status", 1).setSetting(dealStatusList).setFormType("dealStatus").setType(null));
			fieldList.add(new CrmModelFiledVO("last_time", FieldEnum.DATETIME, "last follow-up time", 1));
			fieldList.add(new CrmModelFiledVO("detail_address", FieldEnum.TEXT, "detailed address", 1));
			fieldList.add(new CrmModelFiledVO("address", FieldEnum.MAP_ADDRESS, "Region Positioning", 1));
			fieldList.add(new CrmModelFiledVO("teamMemberIds", FieldEnum.USER, "Related Team", 1));
		} else if (CrmEnum.CONTACTS == crmEnum) {
			fieldList.add(new CrmModelFiledVO("last_time", FieldEnum.DATETIME, "last follow-up time", 1));
			fieldList.add(new CrmModelFiledVO("teamMemberIds", FieldEnum.USER, "Related Team", 1));
		} else if (CrmEnum.PRODUCT == crmEnum) {
			List<Object> statusList = new ArrayList<>();
			statusList.add(new JSONObject().fluentPut("name", "Listed").fluentPut("value", 1));
			statusList.add(new JSONObject().fluentPut("name", "Unavailable").fluentPut("value", 0));
			fieldList.add(new CrmModelFiledVO("status", FieldEnum.SELECT, "Whether to leave the shelf or not", 1).setSetting(statusList));
		} else if (CrmEnum.BUSINESS == crmEnum) {
			List<CrmBusinessType> crmBusinessTypes = ApplicationContextHolder.getBean(ICrmBusinessTypeService.class).queryBusinessStatusOptions();
			crmBusinessTypes.forEach(record -> {
				record.getStatusList().add(new CrmBusinessStatus().setName("Win").setTypeId(record.getTypeId()).setStatusId(-1));
				record.getStatusList().add(new CrmBusinessStatus().setName("Lost").setTypeId(record.getTypeId()).setStatusId(-2));
				record.getStatusList().add(new CrmBusinessStatus().setName("invalid").setTypeId(record.getTypeId()).setStatusId(-3));
			});
			fieldList.add(new CrmModelFiledVO("type_id", FieldEnum.BUSINESS, "Opportunity Status Group", 1).setFormType("business_type").setSetting(new ArrayList<>(crmBusinessTypes)));
			fieldList.add(new CrmModelFiledVO("last_time", FieldEnum.DATETIME, "last follow-up time", 1));
			fieldList.add(new CrmModelFiledVO("teamMemberIds", FieldEnum.USER, "Related Team", 1));
		} else if (CrmEnum.CONTRACT == crmEnum) {
			List<Object> checkList = new ArrayList<>();
			checkList.add(new JSONObject().fluentPut("name", "to be reviewed").fluentPut("value", 0));
			checkList.add(new JSONObject().fluentPut("name", "pass").fluentPut("value", 1));
			checkList.add(new JSONObject().fluentPut("name", "reject").fluentPut("value", 2));
			checkList.add(new JSONObject().fluentPut("name", "under review").fluentPut("value", 3));
			checkList.add(new JSONObject().fluentPut("name", "withdrawn").fluentPut("value", 4));
			checkList.add(new JSONObject().fluentPut("name", "Uncommitted").fluentPut("value", 5));
			checkList.add(new JSONObject().fluentPut("name", "obsolete").fluentPut("value", 8));
			fieldList.add(new CrmModelFiledVO("check_status", FieldEnum.CHECKBOX, "Check Status", 1).setFormType("checkStatus").setType(null).setSetting(checkList));
			fieldList.add(new CrmModelFiledVO("last_time", FieldEnum.DATETIME, "last follow-up time", 1));
			fieldList.add(new CrmModelFiledVO("teamMemberIds", FieldEnum.USER, "Related Team", 1));
		} else if (CrmEnum.RECEIVABLES == crmEnum) {
			List<Object> checkList = new ArrayList<>();
			checkList.add(new JSONObject().fluentPut("name", "to be reviewed").fluentPut("value", 0));
			checkList.add(new JSONObject().fluentPut("name", "pass").fluentPut("value", 1));
			checkList.add(new JSONObject().fluentPut("name", "reject").fluentPut("value", 2));
			checkList.add(new JSONObject().fluentPut("name", "under review").fluentPut("value", 3));
			checkList.add(new JSONObject().fluentPut("name", "Uncommitted").fluentPut("value", 5));
			fieldList.add(new CrmModelFiledVO("check_status", FieldEnum.CHECKBOX, "Check Status", 1).setFormType("checkStatus").setType(null).setSetting(checkList));
			fieldList.add(new CrmModelFiledVO("teamMemberIds", FieldEnum.USER, "Related Team", 1));
		} else if (CrmEnum.RECEIVABLES_PLAN == crmEnum) {
			fieldList.add(new CrmModelFiledVO("realReceivedMoney", FieldEnum.FLOATNUMBER, "Actual Receipt Amount", 1));
			fieldList.add(new CrmModelFiledVO("realReturnDate", FieldEnum.DATE, "Actual collection date", 1));
			List<Object> checkList = new ArrayList<>();
			checkList.add(new JSONObject().fluentPut("name", "Pending payment").fluentPut("value", 0));
			checkList.add(new JSONObject().fluentPut("name", "Payment collection completed").fluentPut("value", 1));
			checkList.add(new JSONObject().fluentPut("name", "Partial payment").fluentPut("value", 2));
			checkList.add(new JSONObject().fluentPut("name", "obsolete").fluentPut("value", 3));
			checkList.add(new JSONObject().fluentPut("name", "overdue").fluentPut("value", 4));
			checkList.add(new JSONObject().fluentPut("name", "to be effective").fluentPut("value", 5));
			fieldList.add(new CrmModelFiledVO("receivedStatus", FieldEnum.CHECKBOX, "Receipt Status", 1).setSetting(checkList));
		} else if (CrmEnum.INVOICE == crmEnum) {
			for (CrmModelFiledVO crmModelFiledVO : fieldList) {
				if ("invoiceType".equals(crmModelFiledVO.getFieldName())) {
					List<Object> checkList = new ArrayList<>();
					checkList.add(new JSONObject().fluentPut("name", "VAT special invoice").fluentPut("value", 1));
					checkList.add(new JSONObject().fluentPut("name", "VAT ordinary invoice").fluentPut("value", 2));
					checkList.add(new JSONObject().fluentPut("name", "National Tax General Machine-Printed Invoice").fluentPut("value", 3));
					checkList.add(new JSONObject().fluentPut("name", "Land Tax General Machine Printing Invoice").fluentPut("value", 4));
					checkList.add(new JSONObject().fluentPut("name", "receipt").fluentPut("value", 5));
					crmModelFiledVO.setSetting(checkList);
					break;
				}
			}
			List<Object> settingList = new ArrayList<>(2);
			settingList.add(new JSONObject().fluentPut("name", "Not invoiced").fluentPut("value", 0));
			settingList.add(new JSONObject().fluentPut("name", "Invoiced").fluentPut("value", 1));
			fieldList.add(new CrmModelFiledVO("invoiceStatus", FieldEnum.SELECT, "Invoice Status", 1).setSetting(settingList));
			fieldList.add(new CrmModelFiledVO("invoiceNumber", FieldEnum.TEXT, "Invoice Number", 1));
			fieldList.add(new CrmModelFiledVO("realInvoiceDate", FieldEnum.DATE, "Actual Invoice Date", 1));
			fieldList.add(new CrmModelFiledVO("logisticsNumber", FieldEnum.TEXT, "logistics number", 1));
		}
		LambdaQueryWrapper<CrmField> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CrmField::getLabel, label);
		queryWrapper.ne(CrmField::getFieldType, 1);
		queryWrapper.eq(CrmField::getIsHidden, 0);
		queryWrapper.select(CrmField::getFieldName, CrmField::getName, CrmField::getType, CrmField::getOptions, CrmField::getRemark, CrmField::getPrecisions);
		List<CrmField> crmFields = crmFieldService.list(queryWrapper);
		List<CrmModelFiledVO> records = crmFields.stream()
				.map(field -> {
					CrmModelFiledVO filedVO = BeanUtil.copyProperties(field, CrmModelFiledVO.class);
					crmFieldService.recordToFormType(filedVO, FieldEnum.parse(filedVO.getType()));
					return filedVO;
				}).collect(Collectors.toList());
		List<FieldEnum> fieldEnums = Arrays.asList(FieldEnum.FILE, FieldEnum.DATE_INTERVAL,
				FieldEnum.HANDWRITING_SIGN, FieldEnum.DESC_TEXT, FieldEnum.DETAIL_TABLE, FieldEnum.CALCULATION_FUNCTION);
		records.removeIf(record -> fieldEnums.contains(FieldEnum.parse(record.getType())));
		fieldList.addAll(records);
		return fieldList;
	}

	/**
	 * Added scene
	 *
	 * @param crmScene data
	 */
	@Override
	public void addScene(CrmScene crmScene) {
		Long userId = UserUtil.getUserId();
		try {
			JSON.parse(crmScene.getData());
		} catch (Exception e) {
			return;
		}
		crmScene.setIsHide(0).setSort(99999).setIsSystem(0).setCreateTime(DateUtil.date()).setUserId(userId);
		save(crmScene);
		if (Objects.equals(1, crmScene.getIsDefault())) {
			crmSceneDefaultService.lambdaUpdate().eq(CrmSceneDefault::getType, crmScene.getType()).eq(CrmSceneDefault::getUserId, userId).remove();
			CrmSceneDefault adminSceneDefault = new CrmSceneDefault();
			adminSceneDefault.setSceneId(crmScene.getSceneId()).setType(crmScene.getType()).setUserId(userId);
			crmSceneDefaultService.save(adminSceneDefault);
		}
	}

	/**
	 * Modify the scene
	 *
	 * @param crmScene data
	 */
	@Override
	public void updateScene(CrmScene crmScene) {
		Long userId = UserUtil.getUserId();
		CrmScene oldAdminScene = getById(crmScene.getSceneId());
		if (oldAdminScene == null) {
			return;
		}
		try {
			JSON.parse(crmScene.getData());
		} catch (Exception e) {
			return;
		}
		if (Objects.equals(1, crmScene.getIsDefault())) {
			LambdaUpdateWrapper<CrmSceneDefault> wrapper = new LambdaUpdateWrapper<>();
			wrapper.set(CrmSceneDefault::getSceneId, crmScene.getSceneId());
			wrapper.eq(CrmSceneDefault::getUserId, userId);
			wrapper.eq(CrmSceneDefault::getType, oldAdminScene.getType());
			crmSceneDefaultService.update(wrapper);
		} else {
			LambdaQueryWrapper<CrmSceneDefault> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(CrmSceneDefault::getSceneId, crmScene.getSceneId())
					.eq(CrmSceneDefault::getUserId, userId)
					.eq(CrmSceneDefault::getType, oldAdminScene.getType());
			crmSceneDefaultService.remove(wrapper);
		}
		crmScene.setUserId(userId).setType(oldAdminScene.getType()).setSort(oldAdminScene.getSort()).setIsSystem(oldAdminScene.getIsSystem()).setUpdateTime(DateUtil.date());
		updateById(crmScene);
	}

	/**
	 * Save default scene
	 *
	 * @param sceneId
	 */
	@Override
	public void setDefaultScene(Integer sceneId) {
		Long userId = UserUtil.getUserId();
		CrmScene oldAdminScene = getById(sceneId);
		if (oldAdminScene != null) {
			crmSceneDefaultService.removeByMap(new JSONObject().fluentPut("user_id", userId).fluentPut("type", oldAdminScene.getType()).getInnerMapObject());
			CrmSceneDefault adminSceneDefault = new CrmSceneDefault();
			adminSceneDefault.setSceneId(sceneId).setType(oldAdminScene.getType()).setUserId(userId);
			crmSceneDefaultService.save(adminSceneDefault);
		}
	}

	/**
	 * delete scene
	 *
	 * @param sceneId sceneId
	 */
	@Override
	public void deleteScene(Integer sceneId) {
		CrmScene crmScene = getById(sceneId);
		if (crmScene != null && !Objects.equals(1, crmScene.getIsSystem())) {
			removeById(sceneId);
		}
	}

	/**
	 * Query scene settings
	 *
	 * @param type type
	 * @return data
	 */
	@Override
	public JSONObject querySceneConfig(Integer type) {
		Long userId = UserUtil.getUserId();
		List<CrmScene> crmSceneList = lambdaQuery().eq(CrmScene::getUserId, userId).eq(CrmScene::getType, type).list();
		Map<Integer, List<CrmScene>> collect = crmSceneList.stream().collect(Collectors.groupingBy(CrmScene::getIsHide));
		List<CrmSceneDefault> defaults = crmSceneDefaultService.lambdaQuery().eq(CrmSceneDefault::getType, type).eq(CrmSceneDefault::getUserId, userId).list();

		if (!collect.containsKey(1)) {
			collect.put(1, new ArrayList<>());
		}
		if (!collect.containsKey(0)) {
			collect.put(0, new ArrayList<>());
		}
		for (CrmSceneDefault sceneDefault : defaults) {
			Integer sceneId = sceneDefault.getSceneId();
			for (CrmScene crmScene : collect.get(0)) {
				if (Objects.equals(sceneId, crmScene.getSceneId())) {
					crmScene.setIsDefault(1);
				}
			}
		}
		return new JSONObject().fluentPut("value", collect.get(0)).fluentPut("hide_value", collect.get(1));
	}

	/**
	 * Set the scene
	 */
	@Override
	public void sceneConfig(CrmSceneConfigBO config) {
		Long userId = UserUtil.getUserId();
		List<CrmScene> crmSceneList = lambdaQuery().eq(CrmScene::getUserId, userId).eq(CrmScene::getType, config.getType()).list();
		Map<Integer, CrmScene> crmSceneMap = new HashMap<>(crmSceneList.size());
		for (CrmScene crmScene : crmSceneList) {
			crmSceneMap.put(crmScene.getSceneId(), crmScene);
		}
		for (int i = 0; i < config.getNoHideIds().size(); i++) {
			Integer id = config.getNoHideIds().get(i);
			if (crmSceneMap.containsKey(id)) {
				crmSceneMap.get(id).setSort(i).setIsHide(0);
			}
		}
		for (int i = 0; i < config.getHideIds().size(); i++) {
			Integer id = config.getHideIds().get(i);
			if (crmSceneMap.containsKey(id)) {
				CrmScene scene = crmSceneMap.get(id);
				scene.setSort(0);
				if (scene.getIsSystem() == 1) {
					scene.setIsHide(0);
				} else {
					scene.setIsHide(1);
				}

			}
		}
		updateBatchById(crmSceneMap.values());
	}


}
