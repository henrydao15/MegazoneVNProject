package com.megazone.crm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.megazone.core.common.FieldEnum;
import com.megazone.core.common.JSONArray;
import com.megazone.core.common.JSONObject;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.PO.CrmField;
import com.megazone.crm.entity.PO.CrmRoleField;
import com.megazone.crm.mapper.CrmRoleFieldMapper;
import com.megazone.crm.service.ICrmFieldService;
import com.megazone.crm.service.ICrmRoleFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CrmRoleFieldServiceImpl extends BaseServiceImpl<CrmRoleFieldMapper, CrmRoleField> implements ICrmRoleFieldService {

	@Autowired
	private AdminService adminService;

	@Autowired
	private ICrmFieldService crmFieldService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<CrmRoleField> queryUserFieldAuth(Integer label, Integer authLevel) {
		List<CrmRoleField> list = queryUserRoleField(label);
		list.removeIf(crmRoleField -> crmRoleField.getAuthLevel() < authLevel);
		return list;
	}

	private List<CrmRoleField> queryUserRoleField(Integer label) {
		QueryWrapper<CrmRoleField> queryWrapper = new QueryWrapper<>();
		List<Integer> roleIds = UserUtil.getUser().getRoles();
		if (roleIds.isEmpty()) {
			return Collections.emptyList();
		}
		queryWrapper.select("field_id", "field_name", "max(auth_level) as auth_level", "max(mask_type) AS mask_type", "field_type")
				.in("role_id", roleIds)
				.eq("label", label)
				.groupBy("field_name");
		List<CrmRoleField> list = list(queryWrapper);
		if (list.size() == 0) {
			List<Integer> data = adminService.queryRoleByRoleType(2).getData();
			data.retainAll(UserUtil.getUser().getRoles());
			data.forEach(roleId -> {
				queryRoleField(roleId, label);
			});
			list = list(queryWrapper);
		}
		return list;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<String> queryNoAuthField(Integer label) {
		List<CrmRoleField> list = queryUserRoleField(label);
		list.removeIf(crmRoleField -> crmRoleField.getAuthLevel() > 1);
		List<String> collect = crmFieldService.lambdaQuery().select(CrmField::getFieldName).eq(CrmField::getIsHidden, 1).eq(CrmField::getLabel, label).list()
				.stream().map(field -> StrUtil.toCamelCase(field.getFieldName())).collect(Collectors.toList());
		collect.addAll(list.stream().map(field -> StrUtil.toCamelCase(field.getFieldName())).collect(Collectors.toList()));
		return collect;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<CrmRoleField> queryRoleField(Integer roleId, Integer label) {
		Integer num = lambdaQuery().eq(CrmRoleField::getRoleId, roleId).eq(CrmRoleField::getLabel, label).count();

		if (num == 0) {
			List<CrmRoleField> fields = new ArrayList<>();
			LambdaQueryWrapper<CrmField> fieldQueryWrapper = new LambdaQueryWrapper<>();
			fieldQueryWrapper.select(CrmField::getFieldId, CrmField::getFieldName, CrmField::getName, CrmField::getFieldType);
			fieldQueryWrapper.eq(CrmField::getLabel, label);

			fieldQueryWrapper.ne(CrmField::getType, FieldEnum.DESC_TEXT.getType());
			List<CrmField> list = crmFieldService.list(fieldQueryWrapper);
			list.forEach(crmField -> {
				CrmRoleField roleField = new CrmRoleField();
				roleField.setRoleId(roleId);
				roleField.setLabel(label);
				roleField.setFieldId(crmField.getFieldId());
				roleField.setFieldName(crmField.getFieldName());
				roleField.setName(crmField.getName());
				roleField.setAuthLevel(3);
				roleField.setMaskType(0);
				switch (label) {
					case 1: {
						if ("leads_name".equals(crmField.getFieldName())) {
							roleField.setOperateType(3);
						} else if ("next_time".equals(crmField.getFieldName())) {
							roleField.setOperateType(4);
						} else {
							roleField.setOperateType(1);
						}
						break;
					}
					case 2: {
						if ("customer_name".equals(crmField.getFieldName())) {
							roleField.setOperateType(3);
						} else if ("next_time".equals(crmField.getFieldName())) {
							roleField.setOperateType(4);
						} else {
							roleField.setOperateType(1);
						}
						break;
					}
					case 3: {
						if ("customer_id".equals(crmField.getFieldName())) {
							roleField.setOperateType(4);
						} else if ("next_time".equals(crmField.getFieldName())) {
							roleField.setOperateType(4);
						} else {
							roleField.setOperateType(1);
						}
						break;
					}
					case 4: {
						if (Arrays.asList("name", "unit", "price", "category_id").contains(crmField.getFieldName())) {
							roleField.setOperateType(3);
						} else {
							roleField.setOperateType(1);
						}
						break;
					}
					case 5: {
						if ("customer_id".equals(crmField.getFieldName())) {
							roleField.setOperateType(4);
						} else {
							roleField.setOperateType(1);
						}
						break;
					}
					case 6: {
						if ("num".equals(crmField.getFieldName())) {
							roleField.setOperateType(3);
						} else {
							roleField.setOperateType(1);
						}
						break;
					}
					case 7: {
						roleField.setOperateType(1);
						break;
					}
					case 8: {
						roleField.setOperateType(1);
						break;
					}
					case 17: {
						if (Arrays.asList("owner_user_id", "customer_id", "contract_id").contains(crmField.getFieldName())) {
							roleField.setOperateType(4);
						} else {
							roleField.setOperateType(1);
						}
						break;
					}
					case 18: {
						if (Arrays.asList("contract_money", "contract_id", "customer_id").contains(crmField.getFieldName())) {
							roleField.setOperateType(4);
						} else {
							roleField.setOperateType(1);
						}
						break;
					}
				}

				roleField.setFieldType(crmField.getFieldType());
				fields.add(roleField);
			});
			fields.add(new CrmRoleField(label, roleId, "create_user_name", "Creator", 2, 2, 1));
			fields.add(new CrmRoleField(label, roleId, "create_time", "create time", 2, 2, 1));
			fields.add(new CrmRoleField(label, roleId, "update_time", "update time", 2, 2, 1));
			if (!label.equals(CrmEnum.PRODUCT.getType())) {
				fields.add(new CrmRoleField(label, roleId, "owner_user_name", "responsible person", 2, 4, 1));
				fields.add(new CrmRoleField(label, roleId, "owner_dept_name", "Department", 2, 4, 1));
			}
			switch (label) {
				case 1: {
					fields.add(new CrmRoleField(label, roleId, "last_content", "last follow-up record", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "last_time", "last follow-up time", 2, 2, 1));
					break;
				}
				case 2: {
					fields.add(new CrmRoleField(label, roleId, "last_content", "last follow-up record", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "last_time", "last follow-up time", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "receive_time", "The person in charge gets the customer time", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "deal_status", "Deal status", 2, 4, 1));
					fields.add(new CrmRoleField(label, roleId, "status", "lock status", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "pool_day", "Days to enter the open sea", 2, 2, 1));
					break;
				}
				case 3: {
					fields.add(new CrmRoleField(label, roleId, "last_time", "last follow-up time", 2, 2, 1));
					break;
				}
				case 4: {
					fields.add(new CrmRoleField(label, roleId, "status", "Whether to leave the shelf or not", 3, 4, 1));
					break;
				}
				case 5: {
					fields.add(new CrmRoleField(label, roleId, "type_name", "Opportunity Stage", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "status_name", "Opportunity Status Group", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "last_time", "last follow-up time", 2, 2, 1));
					break;
				}
				case 6: {
					fields.add(new CrmRoleField(label, roleId, "last_time", "last follow-up time", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "last_content", "last follow-up record", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "received_money", "received amount", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "unreceived_money", "unreceived amount", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "check_status", "Review Status", 2, 4, 1));
					break;
				}
				case 7: {
					fields.add(new CrmRoleField(label, roleId, "contractMoney", "Contract Money", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "check_status", "Review Status", 2, 4, 1));
					break;
				}
				case 8: {
					fields.add(new CrmRoleField(label, roleId, "num", "Number of Periods", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "real_return_date", "Actual collection time", 2, 2, 1));
					fields.add(new CrmRoleField(label, roleId, "real_received_money", "Actual collection amount", 2, 2, 1, 6));
					fields.add(new CrmRoleField(label, roleId, "unreceived_money", "unreceived amount", 2, 2, 1, 6));
					fields.add(new CrmRoleField(label, roleId, "received_status", "received_status", 2, 2, 1));
					break;
				}
				default:
					break;
			}
			saveBatch(fields);
		}
		return getBaseMapper().queryRoleFieldList(roleId, label);
	}


	/**
	 * Mask replacement value tool class
	 *
	 * @param crmEnum  crm type
	 * @param list     data list
	 * @param maskType mask level 0 hide none 1 list hide details not hide 2 hide both
	 */
	@Override
	public void replaceMaskFieldValue(CrmEnum crmEnum, List<? extends Map<String, Object>> list, Integer maskType) {
		List<CrmRoleField> roleFields = getBaseMapper().queryMaskFieldList(maskType, crmEnum.getType());
		if (roleFields.size() == 0 || maskType < 1 || UserUtil.isAdmin()) {
			return;
		}
		Map<String, CrmRoleField> collect = roleFields.stream().collect(Collectors.toMap(roleField -> StrUtil.toCamelCase(roleField.getFieldName()), Function.identity()));
		for (Map<String, Object> map : list) {
			for (String key : collect.keySet()) {
				if (map.containsKey(key)) {
					map.put(key, parseValue(collect.get(key).getType(), map.get(key)));
				}
			}
		}

	}

	/**
	 * format field value
	 *
	 * @param type  field type
	 * @param value field value
	 * @return formatted value
	 */
	@Override
	public Object parseValue(Integer type, Object value) {
		if (ObjectUtil.isEmpty(value)) {
			return null;
		}
		switch (type) {
			case 6: {
				return "***";
			}
			case 7: {
				String s = value.toString();
				if (s.length() <= 7) {
					return "***";
				}
				return StrUtil.replace(s, 3, s.length() - 4, '*');
			}
			case 14: {
				String s = value.toString();
				String[] split = StrUtil.split(s, "@");
				if (split.length < 2) {
					return "***";
				}
				if (split[0].length() <= 2) {
					return "***@" + split[1];
				}
				return StrUtil.replace(split[0], 2, split[0].length(), '*') + split[1];
			}
			case 43: {
				if (value instanceof JSONArray && ((JSONArray) value).size() >= 4) {
					JSONObject jsonObject = ((JSONArray) value).getJSONObject(3);
					jsonObject.put("name", "***");
				}
				return value;
			}
			default:
				return value;
		}
	}

	/**
	 * Save field settings
	 *
	 * @param fields field list
	 */
	@Override
	public void saveRoleField(List<CrmRoleField> fields) {
		for (CrmRoleField field : fields) {
			if (!Objects.equals(0, field.getMaskType()) && !Arrays.asList(6, 7, 14, 43).contains(field.getType()) && !Arrays.asList(8).contains(field.getLabel())) {
				field.setMaskType(0);
			} else if (Arrays.asList(8).contains(field.getLabel())) {
				if (!"unreceived_money".equals(field.getFieldName()) && !"real_received_money".equals(field.getFieldName()) && !Objects.equals(0, field.getMaskType()) && !Arrays.asList(6, 7, 14, 43).contains(field.getType())) {
					field.setMaskType(0);
				}
			}
			//The standard price of the product, as well as the business opportunity amount and contract amount are not allowed to set masks
			if (("money".equals(field.getFieldName()) && Arrays.asList(5, 6).contains(field.getLabel())) || ("price".equals(field.getFieldName()) && field.getLabel() == 4)) {
				field.setMaskType(0);
			}
		}
		updateBatchById(fields);
	}

	/**
	 * Internal call to save custom fields
	 *
	 * @param crmField custom field
	 */
	@Override
	public void saveRoleField(CrmField crmField) {
		List<CrmRoleField> list = lambdaQuery().select(CrmRoleField::getRoleId).eq(CrmRoleField::getLabel, crmField.getLabel()).groupBy(CrmRoleField::getRoleId).list();
		List<CrmRoleField> roleFieldList = new ArrayList<>();
		list.forEach(field -> {
			CrmRoleField roleField = new CrmRoleField();
			roleField.setRoleId(field.getRoleId());
			roleField.setLabel(crmField.getLabel());
			roleField.setFieldId(crmField.getFieldId());
			roleField.setFieldName(crmField.getFieldName());
			roleField.setName(crmField.getName());
			roleField.setAuthLevel(3);
			roleField.setOperateType(1);
			roleField.setFieldType(crmField.getFieldType());
			roleFieldList.add(roleField);
		});
		saveBatch(roleFieldList);
	}

	/**
	 * Internal call to delete custom fields
	 *
	 * @param fieldId field
	 */
	@Override
	public void deleteRoleField(Integer fieldId) {
		removeByMap(new JSONObject().fluentPut("field_id", fieldId).getInnerMapObject());
	}
}
