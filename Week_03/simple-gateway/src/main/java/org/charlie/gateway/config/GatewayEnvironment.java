package org.charlie.gateway.config;

import java.io.IOException;
import java.util.*;

/**
 * 网关配置信息
 *
 * @author Charlie
 * @date 2021/1/27
 */
public class GatewayEnvironment {

	private final static Properties resource = new Properties();

	private final static Map<String, String> routeTable = new HashMap<>();

	/**
	 * 后端的服务地址
	 */
	private final static List<String> proxyServers = new ArrayList<>();

	static {
		try {
			resource.load(GatewayEnvironment.class.getResourceAsStream("/META-INF/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		parseRouteTable();
		parseProxyServer();
	}

	private static void parseProxyServer() {
		proxyServers.addAll(Arrays.asList(resource.get("proxy.server").toString().split(",")));
	}

	private static void parseRouteTable() {
		resource.keySet().stream()
				.filter(k -> k.toString().startsWith("route"))
				.forEach(k -> routeTable.put(k.toString(), resource.get(k).toString()));
	}

	public static String getProperty(String propertyName) {
		return resource.getProperty(propertyName);
	}

	public static Map<String, String> getRouteTable() {
		return routeTable;
	}

	public static List<String> getProxyServers() {
		return proxyServers;
	}

}
