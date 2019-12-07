package fengfei.studio.javacore;

// 和CPU指令集类似，Java字节码指令也有 计算指令、访存指令、跳转指令(条件跳转、方法调用等、异常、return等）
/* 不同于CPU指令集的实现，Java字节码执行时的内存环境为局部变量区+操作栈
    1）局部变量区类似于C代码执行时的内存栈，Java字节码指令不能直接访问寄存器，而是使用操作栈模拟寄存器
    2）计算指令执行前需要将局部变量/常量区的变量加载到操作栈，然后执行指令，指令执行后，使用到的栈内变量自动弹出
*/

//  javap -verbose -private fengfei/studio/javacore/ByteCode.class

public class ByteCode {
    public static void main(String[] args) {
        int a = 1;
        int b = 3;
        int c = a + b;

        print(c);
    }

    private static void print(int i){
        System.out.println(i);
    }
}

