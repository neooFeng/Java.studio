package fengfei.studio.net.websocket;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class IMClientRunnable implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(IMClientRunnable.class);

    private Configuration config;
    private AtomicInteger sendCounter;
    private boolean isSender;

    public IMClientRunnable(AtomicInteger sendCounter, Configuration config){
        this.isSender = true;
        this.sendCounter = sendCounter;
        this.config = config;
    }

    public IMClientRunnable(Configuration config) {
        this.config = config;
    }

    @Override
    public void run() {
        final WebsocketClientEndpoint clientEndPoint;
        try {
            clientEndPoint = new WebsocketClientEndpoint(new URI(this.config.getString("connectStr")));

            /*clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                @Override
                public void handleMessage(String message) {
                    logger.error("recv: " + message);
                }
            });*/

            enterChatRoom(clientEndPoint);

            if (isSender){
                int sendMessageIntervalMs = this.config.getInt("sendMessageIntervalMs");
                Thread.sleep(RandomUtils.nextInt(sendMessageIntervalMs));

                while (true){
                    sendMessage(clientEndPoint, "msg-" + Thread.currentThread().getName());
                    Thread.sleep(sendMessageIntervalMs);
                }
            }

            long stayAliveDurationMs = Integer.MAX_VALUE; // very long time
            if (config.containsKey("stayAliveDurationMs")){
                stayAliveDurationMs = config.getLong("stayAliveDurationMs");
            }

            Thread.sleep(stayAliveDurationMs);
            // thread exit, connection will closed automatically.
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private void enterChatRoom(WebsocketClientEndpoint clientEndPoint) {
        String chatRoomAddr = this.config.getString("chatRoomAddr");
        String message = "{\"id\": \"" + UUID.randomUUID().toString() + "\", \"category\": \"PRESENCE\", \"to\": \"" + chatRoomAddr + "\"}";
        sendText(clientEndPoint, message);
    }

    private void sendMessage(WebsocketClientEndpoint clientEndPoint, String messageBody) {
        String chatRoomAddr = this.config.getString("chatRoomAddr");
        String message = "{\"id\": \"" + UUID.randomUUID().toString() + "\", \"category\": \"MESSAGE\", \"to\": \"" + chatRoomAddr + "\", \"type\": \"groupchat\",\"body\": {\"text\": \"" + messageBody + "\"}}";
        sendText(clientEndPoint, message);
    }

    private void sendText(WebsocketClientEndpoint clientEndPoint, String text) {
        try {
            clientEndPoint.sendMessage(text);
            if (text.contains("MESSAGE")){
                logger.info("Thread: " + Thread.currentThread().getId() + ", send: " + this.sendCounter.incrementAndGet());
            }else {
                logger.info("Thread: " + Thread.currentThread().getId() + " presence.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
