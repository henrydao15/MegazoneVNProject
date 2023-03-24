package com.megazone.oa.common.log;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.megazone.core.feign.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class SysLogUtil {


	private static Map<String, Dict> propertiesMap = new HashMap<>();

	static {
		propertiesMap.put("log", Dict.create().set("content", "Current work content").set("tomorrow", "Next day work content").set("question", "Encounter The problem"));
	}

	@Autowired
	private AdminService adminService;

	public List<String> searchChange(Map<String, Object> oldObj, Map<String, Object> newObj, String type) {
		List<String> textList = new ArrayList<>();
		for (String oldKey : oldObj.keySet()) {
			for (String newKey : newObj.keySet()) {
				if (propertiesMap.get(type).containsKey(oldKey)) {
					Object oldValue = oldObj.get(oldKey);
					Object newValue = newObj.get(newKey);
					if (oldValue instanceof Date) {
						oldValue = DateUtil.formatDateTime((Date) oldValue);
					}
					if (newValue instanceof Date) {
						newValue = DateUtil.formatDateTime((Date) newValue);
					}
					if (oldValue instanceof BigDecimal || newValue instanceof BigDecimal) {
						oldValue = Convert.toBigDecimal(oldValue, new BigDecimal(0)).setScale(2, BigDecimal.ROUND_UP).toString();
						newValue = Convert.toBigDecimal(newValue, new BigDecimal(0)).setScale(2, BigDecimal.ROUND_UP).toString();
					}
					if (newKey.equals(oldKey) && !Objects.equals(oldValue, newValue)) {
						if (ObjectUtil.isEmpty(oldValue)) {
							oldValue = "null";
						}
						if (ObjectUtil.isEmpty(newValue)) {
							newValue = "null";
						}
						textList.add("Modify " + propertiesMap.get(type).get(oldKey) + " from " + oldValue + " to " + newValue + ".");
					}
				}
			}
		}
		return textList;
	}

}
