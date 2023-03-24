package com.megazone.crm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.entity.PO.CrmFieldSort;
import com.megazone.crm.entity.VO.CrmFieldSortVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-19
 */
public interface CrmFieldSortMapper extends BaseMapper<CrmFieldSort> {

	/**
	 * @param label label
	 * @return data
	 */
	public List<CrmFieldSortVO> queryListHead(@Param("label") Integer label, @Param("userId") Long userId);
}
