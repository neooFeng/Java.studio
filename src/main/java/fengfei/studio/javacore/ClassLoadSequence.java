package fengfei.studio.javacore;

// 加载、链接（验证、准备、符号解析）、初始化

public class ClassLoadSequence {
    private ClassLoadSequence() {}
    private static class LazyHolder {
        static final ClassLoadSequence INSTANCE = new ClassLoadSequence();
        static {
            System.out.println("LazyHolder.<clinit>");
        }
    }
    public static Object getInstance(boolean flag) {
        if (flag) return new LazyHolder[2];
        return LazyHolder.INSTANCE;
    }
    public static void main(String[] args) {
        System.out.println("main start");
        ClassLoadSequence2.hello();

        System.out.println("ref in array");
        getInstance(true);

        System.out.println("ref in static field");
        getInstance(false);
    }
}
