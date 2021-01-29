package org.charlie.gateway.outbound;

import com.google.common.collect.Lists;
import org.charlie.gateway.config.GatewayEnvironment;
import org.charlie.gateway.filter.HeaderHttpResponseFilter;
import org.charlie.gateway.outbound.httpclient.HttpOutboundHttpClientHandler;
import org.charlie.gateway.outbound.netty.HttpOutboundNettyHandler;

/**
 * 入站处理器工厂类
 *
 * @author Charlie
 * @date 2021/1/27
 */
public class HttpOutboundHandlerFactory {

	public HttpOutboundHandler getHttpOutboundHandler() {
		if ("netty".equals(GatewayEnvironment.getProperty("outbound.handler"))) {
			return new HttpOutboundNettyHandler(Lists.newArrayList(new HeaderHttpResponseFilter()));
		} else {
			return new HttpOutboundHttpClientHandler(Lists.newArrayList(new HeaderHttpResponseFilter()));
		}
	}
}
