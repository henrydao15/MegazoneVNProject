package com.megazone.crm.mapper;

import cn.hutool.core.lang.Dict;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.entity.PO.CrmActionRecord;
import com.megazone.crm.entity.VO.CrmActionRecordVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-25
 */
public interface CrmActionRecordMapper extends BaseMapper<CrmActionRecord> {

	/**
	 * @param actionId
	 * @param types    type
	 * @return data
	 */
	public List<CrmActionRecordVO> queryRecordList(@Param("actionId") Integer actionId, @Param("types") Integer types);

	List<CrmModelFiledVO> queryFieldValue(@Param("data") Dict kv);
}
