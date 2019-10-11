package fengfei.studio.net.websocket;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class IMHandshakeDOS {
    private final static Logger logger = LoggerFactory.getLogger(IMHandshakeDOS.class);

    private static Configuration config;
    private static URI uri;

    static {
        try {
            config = new PropertiesConfiguration("config.properties");
            uri = new URI(config.getString("dosConnectStr2"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0; i<30; i++){
           new Thread(new DOSRunable()).start();
        }

        Thread.sleep(3600 * 1000);  // sleep 1h.
    }

    static class DOSRunable implements Runnable{
        @Override
        public void run() {
            boolean connSuccess = false;
            while (!connSuccess){
                try {
                    new WebsocketClientEndpoint(uri);
                    connSuccess = true;
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
    }
}
