package com.megazone.hrm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.PO.HrmEmployeeContacts;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface HrmEmployeeContactsMapper extends BaseMapper<HrmEmployeeContacts> {

	Integer verifyUnique(@Param("fieldName") String fieldName, @Param("value") String value, @Param("contactsId") Integer contactsId);

}
