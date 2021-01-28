package org.charlie.gateway.outbound;

import org.charlie.gateway.config.GatewayEnvironment;

/**
 * 入栈处理器工厂类
 *
 * @author Charlie
 * @date 2021/1/27
 */
public class HttpOutboundHandlerFactory {

	public HttpOutboundHandler getHttpOutboundHandler() {
		if ("netty".equals(GatewayEnvironment.getProperty("outbound.handler"))) {
			return new HttpOutboundNettyHandler();
		} else {
			return new HttpOutboundHttpClientHandler();
		}
	}
}
