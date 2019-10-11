package fengfei.studio.concurrence;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {
    public static void main(String[] args) throws InterruptedException {

        // 多线程写入map时，最终的size会错误
        testConcurrentWrite();
    }

    private static void testConcurrentWrite() throws InterruptedException {
        Map<String, String> map = new HashMap<>();

        Thread[] writer = new Thread[10];
        for (int i=0; i< writer.length; i++){
            writer[i] = new Thread(new WriterRunnable(map));
            writer[i].start();
        }

        for (int i=0; i< writer.length; i++){
            writer[i].join();
        }

        System.out.println("size: " + map.size());
    }

    private static class WriterRunnable implements Runnable {
        private Map map;
        public WriterRunnable(Map<String, String> map) {
            this.map = map;
        }

        @Override
        public void run() {
            for (int i=0; i<1000; i++){
                map.put(Thread.currentThread().getName() + i, "value");
            }
        }
    }
}
