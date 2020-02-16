package fengfei.studio.net.websocket;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class IMTest{
    private final static Logger logger = LoggerFactory.getLogger(IMTest.class);

    private static Configuration config;

    static {
        try {
            config = new PropertiesConfiguration("config.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        // add sender
        final AtomicInteger sendCounter = new AtomicInteger(0);
        int senderCount = config.getInt("senderCount");
        for (int i=0; i<senderCount; i++){
            new Thread(new IMClientRunnable(sendCounter, config)).start();
        }

        // add more member
        int enterIntervalMs = config.getInt("enterIntervalMs");
        int memberCount = config.getInt("memberCount");
        for (int i=0; i< memberCount; i++){
            Thread t = new Thread(new IMClientRunnable(config));
            t.start();

            try {
                Thread.sleep(enterIntervalMs);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }

        logger.error("allin.");

        try {
            Thread.sleep(3600 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
