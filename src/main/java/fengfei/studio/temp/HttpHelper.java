package fengfei.studio.temp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHelper.class);

    private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;
    private CloseableHttpClient closeableHttpClient;
    private RequestConfig requestConfig;

    public final static String HttpSchema = "http";
    public final static String HttpsSchema = "https";

    public static final String APPLICATION_JSON_VALUE = "application/json";
    public static final String APPLICATION_JSON_TEXT_HTML = "application/json,text/html";
    public static final String APPLICATION_URL_ENCODED = "application/x-www-form-urlencoded";

    public static final String HEADS_CONTENT_LENGTH = "Content-Length";

    private static final List<Header> EMPTY_HEADERS = new ArrayList<>();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public HttpHelper() {
        poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(10);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(10);
        closeableHttpClient = HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager).build();
        requestConfig = RequestConfig.custom().setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000).build();
    }

    public HttpResponseEntity post(URI url, List<Header> requestHeaders, Object objectParams, String encodeCharset){
        String body;
        try {
            body = new ObjectMapper().writeValueAsString(objectParams);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error occured while serializing params: ", e);
            return null;
        }

        HttpPost httpPost = new HttpPost(url);
        if (body != null && !body.isEmpty()) {
            httpPost.setEntity(new StringEntity(body, CharEncoding.UTF_8));
        }
        return httpRequestWithConfig(httpPost, requestHeaders, encodeCharset);
    }


    private HttpResponseEntity httpRequestWithConfig(HttpRequestBase httpRequestBase, List<Header> headers, String encodeCharset) {
        httpRequestBase.setConfig(requestConfig);
        return httpRequest(httpRequestBase, headers, encodeCharset);
    }

    private HttpResponseEntity httpRequest(HttpUriRequest httpUriRequest, List<Header> headers, String encodeCharset) {
        CloseableHttpResponse response = null;
        InputStreamReader isr = null;
        BufferedReader rd = null;
        HttpResponseEntity result = new HttpResponseEntity();
        if (CollectionUtils.isNotEmpty(headers)) {
            headers.forEach(httpUriRequest::setHeader);
        }
        try {
            response = closeableHttpClient.execute(httpUriRequest);
            int status = response.getStatusLine().getStatusCode();
            result.setStatusCode(status);
            result.setHeaders(response.getAllHeaders());
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    isr = new InputStreamReader(entity.getContent(), (encodeCharset == null) ? CharEncoding.UTF_8 : encodeCharset);
                    rd = new BufferedReader(isr);

                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    EntityUtils.consume(entity);
                    result.setBody(stringBuilder.toString());
                }
            } else {
                LOGGER.error(httpUriRequest.getURI().toString() + " statusCode:" + status);
            }
        } catch (IOException e) {
            LOGGER.error("Http request error: ", e);
        }finally{
            try {
                if (isr != null) {
                    isr.close();
                }
                if (rd != null) {
                    rd.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("close string reader, resource or response error: ", e);
            }
        }
        return result;
    }
}
