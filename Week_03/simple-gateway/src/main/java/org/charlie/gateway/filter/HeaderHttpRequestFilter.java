package org.charlie.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 配置请求头过滤器
 *
 * @author Charlie
 * @date 2021/1/28
 */
public class HeaderHttpRequestFilter implements HttpRequestFilter {

	@Override
	public void filter(FullHttpRequest request, ChannelHandlerContext ctx) {
		request.headers().set("request", "request");
	}
}
