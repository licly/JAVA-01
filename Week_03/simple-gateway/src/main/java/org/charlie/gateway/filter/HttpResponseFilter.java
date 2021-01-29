package org.charlie.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;
import org.apache.http.HttpResponse;

/**
 * 响应过滤器
 *
 * @author Charlie
 * @date 2021/1/29
 */
public interface HttpResponseFilter {

	void filter(FullHttpResponse response);

}
