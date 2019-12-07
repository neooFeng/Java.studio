package fengfei.studio.javacore;

//$ javac fengfei/studio/javacore/Exception.java
//$ javap -verbose fengfei/studio/javacore/Exception.class

public class Exception {
    public static void main(String[] args) {
        String tryBlock, catchBlock, finallyBlock;

        try {
            tryBlock = "tryBlock";
            throw new RuntimeException("throw me");
        }catch (RuntimeException e){
            catchBlock = "RuntimeException Block";
            e.printStackTrace();
        }catch (java.lang.Exception e){
            catchBlock = "Exception Block";
        }finally {
            finallyBlock = "finallyBlock";
        }
    }
}
