package fengfei.studio.net.http;

import io.netty.util.concurrent.DefaultThreadFactory;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class TomcatThreadTest {
    private final static Logger logger = LoggerFactory.getLogger(TomcatThreadTest.class);

    private static Configuration config;

    static {
        try {
            config = new PropertiesConfiguration("tomcat-test.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ThreadFactory threadFactory = new DefaultThreadFactory("tomcat-test-poll");
        ExecutorService pool = new ThreadPoolExecutor(10, 1000,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100), threadFactory, new ThreadPoolExecutor.DiscardPolicy());

        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .readTimeout(0, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool())
                .build();
        String url = config.getString("endpoint");
        Request request = new Request.Builder().url(url).get().build();


        int rps = config.getInt("rps");
        int count=0;
        while(true){

            for (int i=0; i<rps; i++){
                pool.execute(new TomcatRequestSender(httpClient, request));
            }

            Thread.sleep(1000);
            System.out.println(count++);
        }
    }
}
