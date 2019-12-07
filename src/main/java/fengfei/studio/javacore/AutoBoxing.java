package fengfei.studio.javacore;

import java.util.ArrayList;

public class AutoBoxing {
    public static void main(String[] args) {
        Integer i = 127;

        ArrayList<Integer> list = new ArrayList<>();
        list.add(i);

        System.out.println(i == 127);
    }
}