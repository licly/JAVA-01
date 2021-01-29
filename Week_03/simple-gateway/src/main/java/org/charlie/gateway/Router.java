package org.charlie.gateway;

import org.charlie.gateway.config.GatewayEnvironment;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 *
 * @author Charlie
 * @date 2021/1/29
 */
public class Router {

	private Map<String, String> routeTable = GatewayEnvironment.getRouteTable();

	private List<String> backendUrl = GatewayEnvironment.getProxyServers();

	public String route(String uri) {
		Random random = new Random();
		int i = random.nextInt(backendUrl.size());
		return backendUrl.get(i) + routeTable.get(uri);
	}
}
