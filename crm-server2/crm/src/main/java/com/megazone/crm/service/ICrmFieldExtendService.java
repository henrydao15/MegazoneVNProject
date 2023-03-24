package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmFieldExtend;

import java.util.List;


/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2021-03-04
 */
public interface ICrmFieldExtendService extends BaseService<CrmFieldExtend> {

	/**
	 *
	 */
	List<CrmFieldExtend> queryCrmFieldExtend(Integer parentFieldId);


	/**
	 *
	 */
	boolean saveOrUpdateCrmFieldExtend(List<CrmFieldExtend> crmFieldExtendList, Integer parentFieldId, boolean isUpdate);


	/**
	 *
	 */
	boolean deleteCrmFieldExtend(Integer parentFieldId);


}
