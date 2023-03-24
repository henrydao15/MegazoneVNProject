package com.megazone.crm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmContacts;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface CrmContactsMapper extends BaseMapper<CrmContacts> {

	/**
	 * @param id     id
	 * @param userId ID
	 * @return data
	 */
	public CrmModel queryById(@Param("id") Integer id, @Param("userId") Long userId);

	/**
	 * @param map map
	 */
	public CrmInfoNumVO queryNum(Map<String, Object> map);

	BasePage<Map<String, Object>> queryBusiness(BasePage<Object> parse, @Param("contactsId") Integer contactsId);
}
