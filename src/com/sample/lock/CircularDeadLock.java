package com.sample.lock;

/**
 * 1. 循环死锁: A 锁中要 B 的锁，B 锁中要 A 的锁
 * 2. 饥饿死锁: 一般为线程池的新任务得不到执行
 * 3. 活锁:    相互礼让导致都无法执行 | 失败重试之循环
 *
 * ref: https://segmentfault.com/a/1190000017134766
 *
 * <p>
 *
 *     命令行：
 *          jstack pid       # 看线程
 *          jmap -histo pid  # 看内存
 *          jstat pid        # 看性能
 *
 *
 * Created by jiek on 2020/4/18.
 */
public class CircularDeadLock {
    Object leftLock = new Object();
    Object rightLock = new Object();

    public static void main(String[] args) {

        int whileTime = 20;
        final CircularDeadLock test = new CircularDeadLock();

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < whileTime) {
                    test.leftRight();
                    i++;
//                    Thread.currentThread().holdsLock(CircularDeadLock.class);//判断当前线程是否持有该类锁
                }
            }
        }, "aThread");
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < whileTime) {
                    test.rightleft();
                    i++;
                }
            }
        }, "bThread");
        a.start();
        b.start();
    }

    public void leftRight() {
        synchronized (leftLock) {
            System.out.println(Thread.currentThread().getName() + ":leftRight:get left");
            synchronized (rightLock) {
                System.out.println(Thread.currentThread().getName() + ":leftRight:get right");
            }
        }
    }

    public void rightleft() {
        synchronized (rightLock) {
            System.out.println(Thread.currentThread().getName() + ":rightleft: get right");
            synchronized (leftLock) {
                System.out.println(Thread.currentThread().getName() + ":rightleft: get left");
            }
        }
    }
}
