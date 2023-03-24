package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmFieldSort;
import com.megazone.crm.entity.VO.CrmFieldSortVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-19
 */
public interface ICrmFieldSortService extends BaseService<CrmFieldSort> {

	/**
	 * @param label label
	 * @return data
	 */
	public List<CrmFieldSortVO> queryListHead(Integer label);

	/**
	 * @param label  label
	 * @param userId ID
	 * @return data
	 */
	public List<CrmFieldSort> queryAllFieldSortList(Integer label, Long userId);
}
