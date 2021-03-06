package org.charlie.gateway.inbound;

import com.google.common.collect.Lists;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.charlie.gateway.filter.HeaderHttpRequestFilter;
import org.charlie.gateway.outbound.HttpOutboundHandlerFactory;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/1/24
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

	private HttpOutboundHandlerFactory hohFactory;

	public HttpInboundInitializer(HttpOutboundHandlerFactory hohFactory) {
		this.hohFactory = hohFactory;
	}

    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline()
                // 处理数据跟在url后面的请求
                .addLast(new HttpServerCodec())
                // 处理数据在Body中的请求
                .addLast(new HttpObjectAggregator(1024 * 1024))
                .addLast(new HttpInboundHandler(
                				hohFactory.getHttpOutboundHandler(),
		                        Lists.newArrayList(new HeaderHttpRequestFilter())));
    }
}
