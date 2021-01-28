package org.charlie.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * HTTP请求过滤器
 *
 * @author Charlie
 * @date 2021/1/28
 */
public interface HttpRequestFilter {

	void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

}
