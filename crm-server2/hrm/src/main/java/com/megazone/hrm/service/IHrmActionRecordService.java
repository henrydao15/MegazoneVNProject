package com.megazone.hrm.service;

import cn.hutool.core.lang.Dict;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.constant.HrmActionBehaviorEnum;
import com.megazone.hrm.constant.HrmActionTypeEnum;
import com.megazone.hrm.entity.BO.QueryRecordListBO;
import com.megazone.hrm.entity.PO.HrmActionRecord;
import com.megazone.hrm.entity.VO.HrmModelFiledVO;
import com.megazone.hrm.entity.VO.QueryRecordListVO;

import java.util.List;

/**
 * <p>
 * hrm
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmActionRecordService extends BaseService<HrmActionRecord> {
	/**
	 * @param actionTypeEnum
	 * @param behaviorEnum
	 * @param content
	 * @param typeId         id
	 * @return
	 */
	boolean saveRecord(HrmActionTypeEnum actionTypeEnum, HrmActionBehaviorEnum behaviorEnum, List<String> content, Integer typeId);


	/**
	 * @param queryRecordListBO
	 * @return
	 */
	List<QueryRecordListVO> queryRecordList(QueryRecordListBO queryRecordListBO);

	/**
	 * @param kv
	 * @return
	 */
	List<HrmModelFiledVO> queryFieldValue(Dict kv);
}
