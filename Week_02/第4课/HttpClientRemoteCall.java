package org.charlie;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 使用HttpClient调用8081服务
 *
 * @author licly
 * @date 2021/1/21
 */
public class HttpClientRemoteCall {

    public static void main(String[] args) throws IOException {
        HttpGet get = new HttpGet("http://localhost:8081");
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(get)) {

            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
