package fengfei.studio.websocket;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class IMTest{
    private final static Logger logger = LoggerFactory.getLogger(IMTest.class);

    private static int threadCount;
    private static Configuration config;

    static {
        try {
            config = new PropertiesConfiguration("config.properties");
            threadCount = config.getInt("threadCount");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Thread[] threads = new Thread[threadCount];

        final AtomicInteger sendCounter = new AtomicInteger(0);

        long pushDurationMs = config.getLong("pushDurationMs");
        long stopTime = System.currentTimeMillis() + pushDurationMs;

        for (int i=0; i<threadCount; i++){
            Thread t = new Thread(new IMClientRunable(stopTime, sendCounter, config));
            t.start();

            threads[i] = t;

            try {
                Thread.sleep(888);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }

        for (int i=0; i<threadCount; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }

        System.out.println("can't arrive");
    }
}
