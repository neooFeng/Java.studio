package fengfei.studio.net.http;

import okhttp3.*;
import okio.BufferedSink;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.CharEncoding;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class APP {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static void main(String[] args) throws IOException {
        String url = "https://pay.qingshuxuetang.com/v1_0_5/order/fyCallback?sign=xlsdk&data=tesetsetsetset";

        Request request = new Request.Builder().url(url)
                .post(RequestBody.create(JSON, ""))
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

       try(Response reponse = client.newCall(request).execute()){
            System.out.println(reponse.toString());
        } catch (IOException e) {
           e.printStackTrace();
       }

        testHttpClient4(url);
    }

    private static void testHttpClient4(String url) throws IOException {
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                SSLContexts.createDefault(),
                new String[] { "TLSv1.2" },
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        HttpPost httpPost = new HttpPost(url);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.toString());
    }

    private static void testHttpClient(String url) {
        PostMethod post = new PostMethod(url);

        HttpMethodParams params = new HttpMethodParams();
        params.setVersion(HttpVersion.HTTP_1_1);
        post.setParams(params);
        HttpClient httpClient = new HttpClient();

        try {
           int status = httpClient.executeMethod(post);

            System.out.println(status);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
    }
}
