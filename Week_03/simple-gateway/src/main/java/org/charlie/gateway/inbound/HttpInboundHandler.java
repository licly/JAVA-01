package org.charlie.gateway.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import org.charlie.gateway.outbound.HttpOutboundHandler;

/**
 * 入站处理器
 *
 * @author Charlie
 * @date 2021/1/24
 */

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private HttpOutboundHandler httpOutboundHandler;

    public HttpInboundHandler(HttpOutboundHandler httpOutboundHandler) {
        this.httpOutboundHandler = httpOutboundHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
        httpOutboundHandler.handle();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
