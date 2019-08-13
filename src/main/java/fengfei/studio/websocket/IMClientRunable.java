package fengfei.studio.websocket;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class IMClientRunable implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(IMClientRunable.class);

    private Configuration config;
    private AtomicInteger sendCounter;
    public IMClientRunable(AtomicInteger sendCounter, Configuration config){
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
            while (true){
                Thread.sleep(sendMessageInterval);
                sendMessage(clientEndPoint, "msg-001");
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
        Future<Void> future = clientEndPoint.sendMessageAsync(text);
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (text.contains("MESSAGE")){
            logger.debug("Thread: " + Thread.currentThread().getId() + ", send: " + this.sendCounter.incrementAndGet());
        }else {
            logger.debug("Thread: " + Thread.currentThread().getId() + " presence.");
        }
    }


}
