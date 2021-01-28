package org.charlie.gateway.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import org.charlie.gateway.filter.HttpRequestFilter;
import org.charlie.gateway.outbound.HttpOutboundHandler;

/**
 * 入站处理器
 *
 * @author Charlie
 * @date 2021/1/24
 */

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private HttpOutboundHandler httpOutboundHandler;

    private HttpRequestFilter filter;

    public HttpInboundHandler(HttpOutboundHandler httpOutboundHandler,
                              HttpRequestFilter filter) {
        this.httpOutboundHandler = httpOutboundHandler;
        this.filter = filter;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
    	filter.filter(request, ctx);
        httpOutboundHandler.handle(request);
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
