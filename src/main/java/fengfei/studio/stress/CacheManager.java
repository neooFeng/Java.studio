package fengfei.studio.stress;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CacheManager {
    private static Logger logger = LoggerFactory.getLogger(CacheManager.class);

    private MemcachedClient cacheClient = null;
    private boolean logEnabled = false;
    private int exp = 864000;
    private static volatile CacheManager singleton;

    private CacheManager() {
        List<String> servers = new ArrayList<>();
        servers.add("127.0.0.1:11211");

        try {
            XMemcachedClientBuilder builder = new XMemcachedClientBuilder(String.join(" ", servers));
            builder.setConnectionPoolSize(10);
            cacheClient = builder.build();
            cacheClient.setEnableHeartBeat(false);  // right ?
            cacheClient.setOptimizeGet(true);
        } catch (IOException e) {
            logger.error("Memcache Client init error!   " + e.getMessage());
        }
    }

    public static CacheManager instance() {
        if (singleton == null) {
            synchronized (CacheManager.class) {
                if (singleton == null) {
                    singleton = new CacheManager();
                }
            }
        }
        return singleton;
    }

    public void set(String key, Object value) {
        try {
            cacheClient.setWithNoReply(key, exp, value);

            if (logEnabled) {
                logger.info(String.format("Cache for %s", key));
            }
        } catch (Exception e) {
            logger.error(String.format("Cache for %s error: ", key), e);
        }
    }

    public void set(String key, Object value, Date expiry) {
        long d = 0;
        if (expiry != null) {
            d = (expiry.getTime() - System.currentTimeMillis()) / 1000;
        }
        try {
            cacheClient.setWithNoReply(key, d > 0 ? (int) d : exp, value);
            if (logEnabled) {
                logger.info(String.format("Cache for %s", key));
            }
        } catch (Exception e) {
            logger.error(String.format("Cache for %s error: ", key), e);
        }
    }

    public void set(String key, Object value, long expiry) {
        long d = expiry / 1000;
        try {
            cacheClient.setWithNoReply(key, d > 0 ? (int) d : exp, value);
            if (logEnabled) {
                logger.info(String.format("Cache for %s", key));
            }
        } catch (Exception e) {
            logger.error(String.format("Cache for %s error: ", key), e);
        }
    }

    public Object get(String key) {
        try {
            Object result = cacheClient.get(key);
            if (logEnabled) {
                logger.info(String.format("Retrive Cache for %s is %s", key, (result == null ? "missed" : "hit")));
            }
            return result;
        } catch (Exception e) {
            logger.error(String.format("Retrive Cache for %s error: ", key), e);
            return null;
        }

    }

    public boolean remove(String key) {
        try {
            boolean result = cacheClient.delete(key);
            if (logEnabled) {
                logger.info(String.format("Remove cache for %s with result: %b", key, result));
            }
            return result;
        } catch (Exception e) {
            logger.error(String.format("Remove cache for %s for error: ", key), e);
            return false;
        }
    }
}
