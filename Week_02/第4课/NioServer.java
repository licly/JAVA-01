package org.charlie.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @author licly
 * @date 2021/1/17
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        ServerSocketChannel ssc = ServerSocketChannel.open().bind(new InetSocketAddress(8084));
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 每次请求这里select都会循环多次，不应该只select一次吗？
            selector.select();
            Set<SelectionKey> sks = selector.selectedKeys();
            Iterator<SelectionKey> iterator = sks.iterator();

            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                if (sk.isAcceptable()) {
                    SocketChannel channel = ((ServerSocketChannel) sk.channel()).accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_WRITE);

                } else if (sk.isWritable()) {
                    executor.execute(() -> service((SocketChannel)sk.channel()));
                    iterator.remove();
                }
            }
        }
    }

    private static void service(SocketChannel channel) {
        try {
            Thread.sleep(20);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("HTTP/1.1 200 OK\n\r".getBytes());
            buffer.put("Content-Type:text/html;charset=utf-8\n\r".getBytes());
            buffer.put("Content-Length: 12\n\r".getBytes());
            buffer.put("\n\r".getBytes());
            buffer.put("hello world".getBytes());
            buffer.flip();
            channel.write(buffer);
            channel.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
