package org.charlie.gateway.config;

import java.io.IOException;
import java.util.Properties;

/**
 * 网关配置信息
 *
 * @author Charlie
 * @date 2021/1/27
 */
public class GatewayEnvironment {

	private final static Properties resource = new Properties();

	static {
		try {
			resource.load(GatewayEnvironment.class.getResourceAsStream("/META-INF/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String propertyName) {
		return resource.getProperty(propertyName);
	}

}
