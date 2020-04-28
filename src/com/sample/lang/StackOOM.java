package com.sample.lang;

/**
 * 测试方法栈内存溢出 OOM
 * <p>
 * Created by jiek on 2020/4/14.
 */
public class StackOOM {

    int stackDeeps = 0;

    public static void main(String[] args) {
        MemoryInfo.printMemoryInfo();
        StackOOM stackOOM = new StackOOM();
        MemoryInfo.printMemoryInfo();
        try {
            stackOOM.fibonacci(123, "hello");
        } catch (Throwable e) {
//            e.printStackTrace();
            System.out.println("stack OOM ==> stackDeeps: " + stackOOM.stackDeeps);

            MemoryInfo.printMemoryInfo();
        }
    }

    private void fibonacci(int num, String msg) {
        stackDeeps++;
        fibonacci(num, msg);
    }
}
