package org.charlie.gateway.outbound;

import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 出站处理器
 *
 * @author Charlie
 * @date 2021/1/24
 */
public interface HttpOutboundHandler {

    void handle(FullHttpRequest request);
}
