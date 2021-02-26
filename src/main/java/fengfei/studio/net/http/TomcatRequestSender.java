package fengfei.studio.net.http;

import fengfei.studio.net.websocket.WebsocketClientEndpoint;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TomcatRequestSender implements Runnable {
    private Request request;
    private OkHttpClient httpClient;

    public TomcatRequestSender(OkHttpClient httpClient, Request request) {
        this.httpClient = httpClient;
        this.request = request;
    }

    @Override
    public void run() {
        try (Response response = this.httpClient.newCall(this.request).execute()) {
            assert response.body() != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
