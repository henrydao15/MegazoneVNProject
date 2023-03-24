package com.megazone.crm.mapper;

import org.apache.ibatis.annotations.Param;

public interface CrmAuthMapper {
	/**
	 * @param name       name
	 * @param conditions
	 * @return num
	 */
	public Integer queryAuthNum(@Param("tableName") String name, @Param("conditions") String conditions);

	/**
	 * @param a      a
	 * @param b      b
	 * @param userId ID
	 * @return num
	 */
	public Integer queryReadFollowRecord(@Param("a") Integer a, @Param("b") Integer b, @Param("userId") Long userId);
}
