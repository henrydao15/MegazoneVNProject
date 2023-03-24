package com.megazone.gateway.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.gateway.entity.GatewayRoute;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GatewayRouterMapper extends BaseMapper<GatewayRoute> {
}
