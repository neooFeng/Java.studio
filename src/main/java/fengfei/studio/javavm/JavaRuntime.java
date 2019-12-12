package fengfei.studio.javavm;

public class JavaRuntime {
    public static void main(String[] args) {

        Runtime.getRuntime().traceMethodCalls(true);

        // print a normal message
        System.out.println("Hello world!");

        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.println(Runtime.getRuntime().maxMemory());
        System.out.println(Runtime.getRuntime().freeMemory());

        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().runFinalization();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                shutdownHook();
            }
        }));
    }

    public static void shutdownHook(){
        System.out.println("good bye.");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("I'm finalize. runFinalization() will not run me.");
    }
}
