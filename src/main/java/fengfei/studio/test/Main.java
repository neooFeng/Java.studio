package fengfei.studio.test;

import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

public class Main {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
       /* double cur = System.currentTimeMillis();
        System.out.println(cur);

        System.out.println(System.currentTimeMillis());
*/

        String te = "lskdkdlsskd";
        char[] chars = te.toCharArray();
        chars[9] = 'a';

        System.out.println(chars);
    }
}
