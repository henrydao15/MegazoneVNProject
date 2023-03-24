package com.megazone.gateway.service.impl;


import com.megazone.core.common.JSON;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.gateway.config.GatewayConst;
import com.megazone.gateway.entity.GatewayRoute;
import com.megazone.gateway.mapper.GatewayRouterMapper;
import com.megazone.gateway.service.GatewayRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 */
@Service
@Slf4j
public class GatewayRouteServiceImpl extends BaseServiceImpl<GatewayRouterMapper, GatewayRoute> implements GatewayRouteService {

	private final Map<String, RouteDefinition> ROUTES = new ConcurrentHashMap<>();

	/**
	 * @param routerId ID
	 */
	@Override
	public void removeRouter(String routerId) {
		ROUTES.remove(routerId);
	}

	/**
	 * @param routeDefinition
	 */
	@Override
	public void saveRouter(RouteDefinition routeDefinition) {
		ROUTES.put(routeDefinition.getId(), routeDefinition);
	}

	/**
	 *
	 */
	@Override
	public Collection<RouteDefinition> getRouteDefinitions() {
		return ROUTES.values();
	}

	/**
	 *
	 */
	@Override
	@PostConstruct
	public void loadConfig() {
		List<GatewayRoute> routeList = list();
		routeList.forEach(router -> {
			log.debug("，id:{},uri:{},filters:{},predicates:{}", router.getRouteId(), router.getUri(), router.getFilters(), router.getPredicates());
			RouteDefinition routeDefinition = new RouteDefinition();
			routeDefinition.setId(router.getRouteId());
			routeDefinition.setOrder(router.getOrders());
			routeDefinition.setUri(URI.create(router.getUri()));
			routeDefinition.setFilters(JSON.parseArray(router.getFilters(), FilterDefinition.class));
			routeDefinition.setPredicates(JSON.parseArray(router.getPredicates(), PredicateDefinition.class));
			Map<String, Object> metadata = new HashMap<>(6, 0.5f);
			metadata.put(GatewayConst.ROUTER_INTERCEPT, router.getIntercept());
			metadata.put(GatewayConst.ROUTER_DESC, router.getDescription());
			routeDefinition.setMetadata(metadata);
			ROUTES.put(routeDefinition.getId(), routeDefinition);
		});
	}
}
