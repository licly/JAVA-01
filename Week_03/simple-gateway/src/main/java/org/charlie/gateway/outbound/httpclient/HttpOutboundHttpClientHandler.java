package org.charlie.gateway.outbound.httpclient;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.util.EntityUtils;
import org.charlie.gateway.Router;
import org.charlie.gateway.filter.HttpResponseFilter;
import org.charlie.gateway.outbound.HttpOutboundHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

/**
 * 出站处理器HttpClient实现
 *
 * @author Charlie
 * @date 2021/1/27
 */
public class HttpOutboundHttpClientHandler
		implements HttpOutboundHandler {

	private CloseableHttpAsyncClient client;

	private Router router = new Router();

	private List<HttpResponseFilter> filters;

	private ExecutorService proxyService;

	public HttpOutboundHttpClientHandler(List<HttpResponseFilter> filters) {
		this.filters = filters;

		int cores = Runtime.getRuntime().availableProcessors();
		proxyService = new ThreadPoolExecutor(cores, cores, 1000,
				TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(2048),
				new NamedThreadFactory("proxyService"),
				new ThreadPoolExecutor.CallerRunsPolicy());

		IOReactorConfig config = IOReactorConfig.custom()
				.setConnectTimeout(1000)
				.setSoTimeout(1000)
				.setIoThreadCount(cores)
				.setRcvBufSize(32 * 1024)
				.build();

		client = HttpAsyncClients.custom()
				.setMaxConnTotal(40)
				.setMaxConnPerRoute(8)
				.setDefaultIOReactorConfig(config)
				.setKeepAliveStrategy(((response, context) -> 6000))
				.build();
	}

	@Override
	public void handle(FullHttpRequest request, ChannelHandlerContext ctx) {
		String backendUrl = router.route(request.uri());
		proxyService.execute(() -> doGet(request, ctx, backendUrl));
	}

	private void doGet(FullHttpRequest request, ChannelHandlerContext ctx, String url) {
		HttpGet get = new HttpGet(url);
		client.start();
		client.execute(get, new FutureCallback<HttpResponse>() {
			@Override
			public void completed(HttpResponse response) {
				handleResponse(request, response, ctx);
			}

			@Override
			public void failed(Exception ex) {
				get.abort();
				ctx.close();
				ex.printStackTrace();
			}

			@Override
			public void cancelled() {
				get.abort();
				ctx.close();
			}
		});
	}

	private void handleResponse(FullHttpRequest request, HttpResponse response, ChannelHandlerContext ctx) {
		DefaultFullHttpResponse resp = null;
		try {
			byte[] body = EntityUtils.toByteArray(response.getEntity());
			resp = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
			resp.headers().set("Content-Type", "application/json");
			resp.headers().set("Content-Length", body.length);

			for (HttpResponseFilter filter : filters) {
				filter.filter(resp);
			}
		} catch (IOException e) {
			e.printStackTrace();
			resp = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);

		} finally {
			if (request != null) {
				if (HttpUtil.isKeepAlive(request)) {
					ctx.write(resp).addListener(ChannelFutureListener.CLOSE);
				} else {
					ctx.write(resp);
				}
			}
			ctx.flush();
		}
	}

	private static class NamedThreadFactory implements ThreadFactory {

		private ThreadGroup group;

		private String namePrefix;

		private AtomicInteger threadNumber = new AtomicInteger();

		public NamedThreadFactory(String prefix) {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() :
					Thread.currentThread().getThreadGroup();
			namePrefix = prefix;
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r,
					namePrefix + "-thread-" + threadNumber.getAndIncrement());
			t.setDaemon(false);
			return t;
		}
	}
}
