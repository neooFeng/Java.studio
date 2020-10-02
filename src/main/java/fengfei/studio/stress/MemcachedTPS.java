package fengfei.studio.stress;

public class MemcachedTPS {
    public static void main(String[] args) {

        CacheManager.instance().set("ke", "");
    }
}
