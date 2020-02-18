package fengfei.studio.net.websocket;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class IMTestEnter {
    private final static Logger logger = LoggerFactory.getLogger(IMTestEnter.class);

    private static Configuration config;

    static {
        try {
            config = new PropertiesConfiguration("testenter.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试聊天室加入性能，最好选用加入后能返回最近消息的聊天室
     * @param args
     */
    public static void main(String[] args){
       int enterIntervalMs = config.getInt("enterIntervalMs");

        ExecutorService executorService = Executors.newCachedThreadPool();

        while (true){
            executorService.execute(new IMClientRunnable(config));

            try {
                Thread.sleep(enterIntervalMs);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
    }
}
