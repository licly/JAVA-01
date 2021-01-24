package org.charlie.gateway;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.charlie.gateway.inbound.HttpInboundInitializer;

/**
 * 启动类
 *
 * @author Charlie
 * @date 2021/1/24
 */
public class SimpleGatewayApplication {

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new HttpInboundInitializer());

        try {
            Channel channel = bootstrap.bind(8888).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
