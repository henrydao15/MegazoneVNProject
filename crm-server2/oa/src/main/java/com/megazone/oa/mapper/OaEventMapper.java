package com.megazone.oa.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.oa.entity.BO.QueryEventListBO;
import com.megazone.oa.entity.PO.OaEvent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OaEventMapper extends BaseMapper<OaEvent> {

	List<OaEvent> queryList(@Param("queryEventListBO") QueryEventListBO queryEventListBO);
}
