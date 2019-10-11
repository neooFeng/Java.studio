package fengfei.studio.concurrence;

import org.apache.commons.lang.math.RandomUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentHashMapTest {
    static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    static CopyOnWriteArrayList<String> array = new CopyOnWriteArrayList<>();

    static {
        for (int i=0; i<20; i++){
            map.put("key" + i, "value" + i);
            array.add("string" + i);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        new Thread(new WorkerRunnable()).start();

        Thread.sleep(1000);
        Object[] temp = map.values().toArray();
        for (Object value : temp){
            System.out.println(value);
            Thread.sleep(200);
        }

        new Thread(new ReaderRunnable()).start();
        Thread.sleep(1000);
        new Thread(new ReaderRunnable()).start();
    }

    private static class WorkerRunnable implements Runnable {
        @Override
        public void run() {
            while (true){
                int keyIndex = RandomUtils.nextInt(20);
                map.remove("key" + keyIndex);

                map.put("new key" + keyIndex, "new value" + keyIndex);

                array.add("new string" + keyIndex);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ReaderRunnable implements Runnable {
        @Override
        public void run() {
            for (String value : array){
                System.out.println(Thread.currentThread().getId() +  " > " + value);

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
