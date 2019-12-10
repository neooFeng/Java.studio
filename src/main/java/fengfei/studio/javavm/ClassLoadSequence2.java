package fengfei.studio.javavm;

public class ClassLoadSequence2 {
    static {
        System.out.println("ClassLoadSequence2.<clinit>");
    }

    public static void hello() {
        System.out.println("hello");
    }
}
