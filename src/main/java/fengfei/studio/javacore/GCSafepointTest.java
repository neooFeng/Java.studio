package fengfei.studio.javacore;

public class GCSafepointTest {
    public static void main(String[] args) {
        new Thread(GCSafepointTest::foo).start();
        new Thread(GCSafepointTest::bar).start();
    }

    // -XX:+PrintGC
    // -XX:+PrintGCApplicationStoppedTime
    // -XX:+PrintSafepointStatistics
    // -XX:+UseCountedLoopSafepoints
    static double sum = 0;

    public static void foo() {
        for (int i = 0; i < 0x77777777; i++) {
            sum += Math.sqrt(i);
        }
    }

    public static void bar() {
        for (int i = 0; i < 50_000_000; i++) {
            new Object().hashCode();
        }
    }
}
