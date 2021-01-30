package org.charlie.gateway;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.charlie.gateway.inbound.HttpInboundInitializer;
import org.charlie.gateway.outbound.HttpOutboundHandlerFactory;

import static org.charlie.gateway.config.Environment.*;

/**
 * 启动类
 *
 * @author Charlie
 * @date 2021/1/24
 */
public class SimpleGatewayApplication {

	private final static String GATEWAY_NAME = "NIOGateway";
	private final static String GATEWAY_VERSION = "3.0.0";

    public static void main(String[] args) {
	    System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new HttpInboundInitializer(new HttpOutboundHandlerFactory()));

        try {
            Channel channel = bootstrap
		            .bind(Integer.parseInt(getProperty("server.port")))
		            .sync().channel();
	        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" started at http://localhost:" + getProperty("server.port") + " for server:" + getProxyServers());
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
