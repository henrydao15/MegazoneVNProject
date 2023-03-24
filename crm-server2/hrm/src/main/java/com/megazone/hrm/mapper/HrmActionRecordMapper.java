package com.megazone.hrm.mapper;

import cn.hutool.core.lang.Dict;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.PO.HrmActionRecord;
import com.megazone.hrm.entity.VO.HrmModelFiledVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * hrm Mapper
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface HrmActionRecordMapper extends BaseMapper<HrmActionRecord> {
	/**
	 * @param kv
	 * @return
	 */
	List<HrmModelFiledVO> queryFieldValue(@Param("data") Dict kv);
}
