package com.megazone.core.utils;

import cn.hutool.core.bean.BeanUtil;
import com.megazone.core.common.Const;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author
 */
public class RecursionUtil {

	/**
	 * @param allList
	 * @param parentName key
	 * @param parentId
	 * @param idName     IDkey
	 * @param returnName null
	 * @param <R>        list
	 * @param <T>
	 * @return
	 */
	public static <R, T> List<T> getChildList(List<R> allList, String parentName, Object parentId, String idName, String returnName) {
		return getChildList(allList, parentName, parentId, idName, returnName, Const.AUTH_DATA_RECURSION_NUM);
	}

	/**
	 * @param allList
	 * @param parentName key
	 * @param parentId
	 * @param idName     IDkey
	 * @param returnName ,null
	 * @param depth
	 * @param <R>
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <R, T> List<T> getChildList(List<R> allList, String parentName, Object parentId, String idName, String returnName, Integer depth) {
		depth--;
		List<T> arrList = new ArrayList<>();
		if (depth < 0) {
			return arrList;
		}
		for (R r : allList) {
			Map<String, Object> beanMap = BeanUtil.beanToMap(r);
			if (!(beanMap.containsKey(idName) && beanMap.containsKey(parentName))) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_SUCH_PARAMENT_ERROR);
			}
			if (Objects.equals(parentId, beanMap.get(parentName))) {
				if (returnName == null) {
					arrList.add((T) r);
				} else {
					arrList.add((T) beanMap.get(returnName));
				}
				arrList.addAll(getChildList(allList, parentName, beanMap.get(idName), idName, returnName, depth));
			}
		}
		return arrList;
	}

	/**
	 * @param allList
	 * @param parentName key
	 * @param parentId
	 * @param idName     IDkey
	 * @param treeName
	 * @param <R>        list
	 * @return
	 */
	public static <R, T> List<T> getChildListTree(List<R> allList, String parentName, Object parentId, String idName, String treeName, Class<T> clazz) {
		return getChildListTree(allList, parentName, parentId, idName, treeName, clazz, Const.AUTH_DATA_RECURSION_NUM);
	}

	/**
	 * @param allList
	 * @param parentName key
	 * @param parentId
	 * @param idName     IDkey
	 * @param treeName
	 * @param depth
	 * @return data
	 */

	public static <R, T> List<T> getChildListTree(List<R> allList, String parentName, Object parentId, String idName, String treeName, Class<T> clazz, Integer depth) {
		depth--;
		List<T> arrList = new ArrayList<>();
		if (depth < 0) {
			return arrList;
		}
		for (R data : allList) {
			Map<String, Object> beanMap = BeanUtil.beanToMap(data);
			if (!(beanMap.containsKey(idName) && beanMap.containsKey(parentName))) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_SUCH_PARAMENT_ERROR);
			}
			if (Objects.equals(parentId, beanMap.get(parentName))) {
				beanMap.put(treeName, getChildListTree(allList, parentName, beanMap.get(idName), idName, treeName, clazz, depth));
				T toBean = BeanUtil.mapToBean(beanMap, clazz, true);
				arrList.add(toBean);
			}
		}
		return arrList;
	}

}
