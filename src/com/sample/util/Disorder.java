package com.sample.util;

/**
 * Created by jiek on 2020/5/27.
 * 指令重排序的验证
 */
public class Disorder {
    static /*volatile*/ int x = 0, y = 0, a = 0, b = 0;//如果此处加上 volatile，将会让线程中的指令不会被重排序，（0，0）结果就不可能发生，否则有概率会发生

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        long t = System.nanoTime();
        for (; ; ) {//1000_0000
            i++;
            a = 0;
            b = 0;
            x = 0;
            y = 0;
            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });
            Thread other = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            one.start();
            other.start();
            one.join();
            other.join();
            if (x == 0 && y == 0) {
                String result = "["+(System.nanoTime() - t) + " miliisecend] 第 " + i + " 次执行结果(x = " + x + ", y = " + y + ")";
                System.out.println(result);
                break;
            }
        }
    }
}
