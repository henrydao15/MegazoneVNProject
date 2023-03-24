package com.megazone.hrm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.PO.HrmEmployeeContactsData;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface HrmEmployeeContactsDataMapper extends BaseMapper<HrmEmployeeContactsData> {

	Integer verifyUnique(@Param("fieldId") Integer fieldId, @Param("value") String value, @Param("contactsId") Integer contactsId);
}
