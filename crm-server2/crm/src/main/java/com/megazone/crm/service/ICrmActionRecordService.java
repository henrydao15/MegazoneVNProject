package com.megazone.crm.service;

import cn.hutool.core.lang.Dict;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.PO.CrmActionRecord;
import com.megazone.crm.entity.VO.CrmActionRecordVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-25
 */
public interface ICrmActionRecordService extends BaseService<CrmActionRecord> {

	/**
	 * @param crmEnum
	 * @param ids     ids
	 */
	public void deleteActionRecord(CrmEnum crmEnum, List<Integer> ids);

	/**
	 * @return data
	 */
	public List<String> queryRecordOptions();

	/**
	 * @param actionId
	 * @param types    type
	 * @return data
	 */
	public List<CrmActionRecordVO> queryRecordList(Integer actionId, Integer types);

	List<CrmModelFiledVO> queryFieldValue(Dict kv);
}
