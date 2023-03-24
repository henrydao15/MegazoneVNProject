package com.megazone.gateway.service;


import com.megazone.core.servlet.BaseService;
import com.megazone.gateway.entity.GatewayRoute;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.Collection;

public interface GatewayRouteService extends BaseService<GatewayRoute> {
	void removeRouter(String routerId);

	void saveRouter(RouteDefinition routeDefinition);

	Collection<RouteDefinition> getRouteDefinitions();

	void loadConfig();
}
