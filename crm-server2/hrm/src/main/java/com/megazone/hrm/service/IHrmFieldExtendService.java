package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmFieldExtend;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2021-05-07
 */
public interface IHrmFieldExtendService extends BaseService<HrmFieldExtend> {
	/**
	 *
	 */
	List<HrmFieldExtend> queryHrmFieldExtend(Integer parentFieldId);

	/**
	 *
	 */
	boolean saveOrUpdateHrmFieldExtend(List<HrmFieldExtend> hrmFieldExtendList, Integer parentFieldId, boolean isUpdate);

	/**
	 *
	 */
	boolean deleteHrmFieldExtend(Integer parentFieldId);
}
