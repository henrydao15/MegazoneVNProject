package com.megazone.hrm.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.megazone.hrm.constant.ConditionType;
import com.megazone.hrm.constant.FieldTypeEnum;
import com.megazone.hrm.constant.IsEnum;
import com.megazone.hrm.entity.BO.QueryFilterFieldBO;

import java.util.ArrayList;
import java.util.List;

public final class FieldUtil {

	public static Object convertFieldValue(Integer type, Object objValue, Integer isFixed) {
		Object value;
		if (type == FieldTypeEnum.NUMBER.getValue()
				|| (type == FieldTypeEnum.SELECT.getValue() && isFixed == IsEnum.YES.getValue())) {
			value = Convert.toInt(objValue);
		} else {
			value = Convert.toStr(objValue);
		}
		return value;
	}

	public static List<String> transferSqlCondition(List<QueryFilterFieldBO> filterList) {
		List<String> queryList = new ArrayList<>();
		if (CollUtil.isEmpty(filterList)) {
			return queryList;
		}
		for (QueryFilterFieldBO filterFieldBO : filterList) {
			Integer conditionType = filterFieldBO.getConditionType();
			List<String> values = filterFieldBO.getValue();
			String name = filterFieldBO.getName();
			ConditionType conditionTypeEnum = ConditionType.parse(conditionType);
			FieldTypeEnum fieldTypeEnum = FieldTypeEnum.parse(filterFieldBO.getType());
			StringBuilder conditions = new StringBuilder();
			if (CollUtil.isNotEmpty(values) || conditionTypeEnum.equals(ConditionType.DATE) || conditionTypeEnum.equals(ConditionType.DATETIME)
					|| conditionTypeEnum.equals(ConditionType.IS_NULL) || conditionTypeEnum.equals(ConditionType.IS_NOT_NULL)) {
				conditions.append(" and ").append(name);
				if (fieldTypeEnum.equals(FieldTypeEnum.CHECKBOX)) {
					if (conditionTypeEnum.equals(ConditionType.CONTAINS)) {
						for (int i = 0; i < values.size(); i++) {
							String option = values.get(i);
							if (i == 0) {
								conditions.append(" is not null and find_in_set('").append(option).append("',").append(name).append(")");
							} else {
								conditions.append(" and find_in_set('").append(option).append("',").append(name).append(")");
							}
						}
						conditions.append(")");
					} else if (conditionTypeEnum.equals(ConditionType.IS)) {
						CollectionUtil.sortByPinyin(values);
						conditions.append(" = '").append(CollectionUtil.join(values, ",")).append("'");
					}
				}
				switch (conditionTypeEnum) {
					case IS:
						conditions.append(" = '").append(values.get(0)).append("'");
						break;
					case IS_NOT:
						conditions.append(" != '").append(values.get(0)).append("'");
						break;
					case CONTAINS:
						conditions.append(" like '%").append(values.get(0)).append("%'");
						break;
					case NOT_CONTAINS:
						conditions.append(" not like '%").append(values.get(0)).append("%'");
						break;
					case IS_NULL:
						conditions.append(" is null");
						break;
					case IS_NOT_NULL:
						conditions.append(" is not null");
						break;
					case GT:
						conditions.append(" > ").append(values.get(0));
						break;
					case EGT:
						conditions.append(" >= ").append(values.get(0));
						break;
					case LT:
						conditions.append(" < ").append(values.get(0));
						break;
					case ELT:
						conditions.append(" <= ").append(values.get(0));
						break;
					case IN:
						conditions.append(" in (").append(values.get(0)).append(")");
						break;
					case NOT_IN:
						conditions.append("not in (").append(values.get(0)).append(")");
						break;
					case DATE:
						conditions.append(" between '").append(values.get(0)).append("' and '").append(values.get(0)).append("'");
						break;
					case DATETIME:
						conditions.append(" between '").append(values.get(0)).append("' and '").append(values.get(1)).append("'");
						break;
					default:
						break;
				}
			} else {
				continue;
			}
			queryList.add(conditions.toString());
		}
		return queryList;
	}
}
