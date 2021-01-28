package org.charlie.gateway.outbound;

import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 出站处理器Netty实现
 *
 * @author Charlie
 * @date 2021/1/27
 */
public class HttpOutboundNettyHandler
		extends ChannelOutboundHandlerAdapter
		implements HttpOutboundHandler {

	@Override
	public void handle(FullHttpRequest request) {

	}
}
