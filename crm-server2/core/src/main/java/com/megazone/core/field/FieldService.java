package com.megazone.core.field;

import com.megazone.core.common.FieldEnum;

import java.util.List;
import java.util.function.Function;

/**
 * @author
 * @date 2021/1/13
 */
public interface FieldService {

	/**
	 *
	 */
	Object convertValueByFormType(Object value, FieldEnum typeEnum);

	/**
	 *
	 */
	<T> List<List<T>> convertFormPositionFieldList(List<T> fieldList, Function<T, Integer> groupMapper,
												   Function<T, Integer> sortMapper, Function<T, Integer> defaultSortMapper);


	/**
	 *
	 */
	boolean verifyStrForNumRestrict(String maxNumRestrict, String minNumRestrict);


	/**
	 *
	 */
	String convertObjectValueToString(Integer type, Object value, String defaultValue);


	/**
	 *
	 */
	boolean equalsByType(Object type);

	/**
	 *
	 */
	boolean equalsByType(Object type, FieldEnum... fieldEnums);
}
