package fengfei.studio.net.websocket;

import okhttp3.*;
import okhttp3.internal.Internal;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class OKHttpClientRunnable implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(OKHttpClientRunnable.class);

    private Configuration config;

    public OKHttpClientRunnable(Configuration config) {
        this.config = config;
    }

    @Override
    public void run() {
        connect();

        long stayAliveDurationMs = Integer.MAX_VALUE; // very long time
        if (config.containsKey("stayAliveDurationMs")){
            stayAliveDurationMs = config.getLong("stayAliveDurationMs");
        }

        try {
            Thread.sleep(stayAliveDurationMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        String url = config.getString("connectStr");
        Request request = new Request.Builder().url(url)
                .header("Upgrade", "websocket")
                .header("Connection", "Upgrade")
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .pingInterval(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(okhttp3.WebSocket webSocket, Response response) {
                enterChatRoom(webSocket);
            }

            @Override
            public void onMessage(okhttp3.WebSocket webSocket, String text) {
                /*logger.info(text);*/
            }

            @Override
            public void onClosed(okhttp3.WebSocket webSocket, int code, String reason) {
                logger.error("closed: " + reason);
            }

            @Override
            public void onFailure(okhttp3.WebSocket webSocket, Throwable t, Response response) {
                logger.error("failure: " + Thread.currentThread().getId() + " --- " + response.toString());
                t.printStackTrace();

                connect();
            }
        });

        client.dispatcher().executorService().shutdown();
    }

    private void enterChatRoom(WebSocket clientEndPoint) {
        String chatRoomAddr = this.config.getString("chatRoomAddr");
        String message = "{\"category\": \"PRESENCE\", \"to\": \"" + chatRoomAddr + "\"}";

        clientEndPoint.send(message);
    }
}
