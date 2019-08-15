package fengfei.studio.websocket;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

public class IMClientRunable implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(IMClientRunable.class);

    private long stopTime;
    private Configuration config;
    private AtomicInteger sendCounter;
    public IMClientRunable(long stopTime, AtomicInteger sendCounter, Configuration config){
        this.stopTime = stopTime;
        this.sendCounter = sendCounter;
        this.config = config;
    }
    
    @Override
    public void run() {
        final WebsocketClientEndpoint clientEndPoint;
        try {
            clientEndPoint = new WebsocketClientEndpoint(new URI(this.config.getString("connectStr")));

            enterChatRoom(clientEndPoint);

            long sendMessageInterval = this.config.getLong("sendMessageInterval");
            while (System.currentTimeMillis() < stopTime){
                Thread.sleep(sendMessageInterval);
                sendMessage(clientEndPoint, "msg-001");
            }

            if (this.config.getBoolean("keepAlive")){
                Thread.sleep(180 * 1000);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private void enterChatRoom(WebsocketClientEndpoint clientEndPoint) {
        String chatRoomAddr = this.config.getString("chatRoomAddr");
        String message = "{\"category\": \"PRESENCE\", \"to\": \"" + chatRoomAddr + "\"}";
        sendText(clientEndPoint, message);
    }

    private void sendMessage(WebsocketClientEndpoint clientEndPoint, String messageBody) {
        String chatRoomAddr = this.config.getString("chatRoomAddr");
        String message = "{\"category\": \"MESSAGE\", \"to\": \"" + chatRoomAddr + "\", \"type\": \"groupchat\",\"body\": {\"text\": \"" + messageBody + "\"}}";
        sendText(clientEndPoint, message);
    }

    private void sendText(WebsocketClientEndpoint clientEndPoint, String text) {
        try {
            clientEndPoint.sendMessage(text);
            if (text.contains("MESSAGE")){
                logger.debug("Thread: " + Thread.currentThread().getId() + ", send: " + this.sendCounter.incrementAndGet());
            }else {
                logger.debug("Thread: " + Thread.currentThread().getId() + " presence.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
