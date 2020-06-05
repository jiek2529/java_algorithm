package com.sample.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jiek on 2020/5/28.
 * <p>
 * 测试 volatile 成员自增的多线程问题，一般只用于单例、标志位、内存屏障等功能上
 *
 * 使用命令行工具看JVM 调用栈情况 # jstack -l  {pid} >>  stack.dump
 */
public class VolatileIncrease {
    volatile int inc = 0;
    AtomicInteger ai = new AtomicInteger();

    public static void main(String[] args) {
        VolatileIncrease volatileIncrease = new VolatileIncrease();
        for (int i = 0; i < 100; i++) {
            new Thread() {
                public void run() {
                    for (int j = 0; j < 10000; j++)
                        volatileIncrease.increase();
                }
            }.start();
        }
//        while (Thread.activeCount() > 1)  //保证前面的线程都执行完,如果java默认分配多CPU，此处可能会死循环
//            Thread.yield();
        try {
            Thread.sleep(5000);
            while (Thread.activeCount() > 3) {
                Thread.yield();
            }
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("count: "+volatileIncrease.inc);
        System.out.println("count: "+volatileIncrease.ai.get());
    }

    private void increase() {
        inc++;//非原子性操作//多线程执行完此句时，还未同步至主内存并通知其它线程时，导致最后值不一定正确。
        ai.incrementAndGet();//正确
//        System.out.println(inc);
    }
}
