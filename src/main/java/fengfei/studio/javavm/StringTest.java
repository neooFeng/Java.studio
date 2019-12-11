package fengfei.studio.javavm;

import java.io.UnsupportedEncodingException;

public class StringTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s1 = "你好";
        String s2 = "a";

        char[] c1 = s1.toCharArray();
        byte[] b1 = s1.getBytes("utf-8");

        char[] c2 = s2.toCharArray();
        byte[] b2 = s2.getBytes("utf-8");

        String ss = new String(b1, "gbk");

        // javap 查看编译器优化情况
        System.out.println(s1 + ss + s2);
        System.out.println(s1 + ss + s2 + "hello");
    }
}
