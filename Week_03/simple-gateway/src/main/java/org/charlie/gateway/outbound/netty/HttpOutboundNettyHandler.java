package org.charlie.gateway.outbound.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import org.charlie.gateway.Router;
import org.charlie.gateway.filter.HttpResponseFilter;
import org.charlie.gateway.outbound.HttpOutboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 出站处理器Netty实现
 *
 * @author Charlie
 * @date 2021/1/27
 */
public class HttpOutboundNettyHandler
		extends ChannelOutboundHandlerAdapter
		implements HttpOutboundHandler {

	private List<HttpResponseFilter> filters;

	private Router router;

	public HttpOutboundNettyHandler(ArrayList<HttpResponseFilter> filters) {
		this.filters = filters;
	}

	@Override
	public void handle(FullHttpRequest request, ChannelHandlerContext ctx) {
		String backendUrl = router.route(request.uri());
		doGet(request, ctx, backendUrl);
	}

	private void doGet(FullHttpRequest request, ChannelHandlerContext ctx, String url) {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workerGroup).channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new HttpResponseDecoder())
						.addLast(new HttpRequestEncoder()).addLast();
			}
		});
	}
}
