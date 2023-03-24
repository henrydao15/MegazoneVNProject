package com.megazone.bi.mapper;

import com.megazone.bi.entity.VO.ProductStatisticsVO;
import com.megazone.core.common.JSONObject;
import com.megazone.core.utils.BiTimeUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiMapper {

	List<ProductStatisticsVO> queryProductSell(BiTimeUtil.BiTimeEntity entity);

	JSONObject queryProductSellCount(BiTimeUtil.BiTimeEntity entity);

	List<JSONObject> taskCompleteStatistics(JSONObject entity);
}
