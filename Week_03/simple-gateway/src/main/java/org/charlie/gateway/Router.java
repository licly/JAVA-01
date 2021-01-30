package org.charlie.gateway;

import org.charlie.gateway.config.Environment;

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

	private Map<String, String> routeTable = Environment.getRouteTable();

	private List<String> backendUrl = Environment.getProxyServers();

	public String route(String uri) {
		uri = convertUri(uri);
		Random random = new Random();
		int i = random.nextInt(backendUrl.size());
		return backendUrl.get(i) + routeTable.get(uri);
	}

	/**
	 * 转换uri为配置文件中的属性名
	 * @param rawUri 原始
	 */
	private String convertUri(String rawUri) {
		String s = rawUri.replaceAll("/", ".");
		return "route.uri" + s;
	}
}
