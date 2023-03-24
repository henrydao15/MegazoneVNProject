package com.megazone.oa.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.oa.entity.BO.QueryEventTaskBO;
import com.megazone.oa.entity.PO.OaCalendarType;
import com.megazone.oa.entity.VO.EventTaskVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OaCalendarTypeMapper extends BaseMapper<OaCalendarType> {

	List<EventTaskVO> queryEventTask(@Param("data") QueryEventTaskBO eventTaskBO);

	List<String> queryFixedTypeByUserId(Long userId);
}
