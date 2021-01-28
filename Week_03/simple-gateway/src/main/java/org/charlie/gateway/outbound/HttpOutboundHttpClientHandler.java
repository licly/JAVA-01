package org.charlie.gateway.outbound;

import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 出站处理器HttpClient实现
 *
 * @author Charlie
 * @date 2021/1/27
 */
public class HttpOutboundHttpClientHandler
		extends ChannelOutboundHandlerAdapter
		implements HttpOutboundHandler {

	private CloseableHttpAsyncClient client;

	private int cores = Runtime.getRuntime().availableProcessors();

	public HttpOutboundHttpClientHandler() {
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
	public void handle(FullHttpRequest request) {
		HttpGet get = new HttpGet(request.uri());
		client.execute(get, new FutureCallback<HttpResponse>() {
			@Override
			public void completed(HttpResponse result) {
				handleResponse(result);
			}

			@Override
			public void failed(Exception ex) {

			}

			@Override
			public void cancelled() {

			}
		});
	}

	private void handleResponse(HttpResponse response) {

	}
}
