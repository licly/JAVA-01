package org.charlie.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.apache.http.HttpResponse;

/**
 * 配置请求头过滤器
 *
 * @author Charlie
 * @date 2021/1/28
 */
public class HeaderHttpResponseFilter implements HttpResponseFilter {

	@Override
	public void filter(FullHttpResponse response) {
		response.headers().set("response", "response");
	}
}
