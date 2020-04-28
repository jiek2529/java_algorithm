package com.sample.lock;

/**
 * Volatile 的使用场景与作用
 * <p>
 * 参考 https://www.cnblogs.com/dolphin0520/p/3920373.html
 * <p>
 *
 * 个人觉得使用场景，
 *     1. 制造内存屏障，防止 memory barries前的代码不会指令重排序到后，或相反
 *     2. double check 单例时的实例声明
 *     3. 状态标记，如 context 初始化完成的标识，在多线程中的作用
 * Created by jiek on 2020/4/16.
 */
public class VolatileTest {
    int num = 0;

    volatile boolean stop = false;

    public static void main(String[] args) {
        VolatileTest test = new VolatileTest();
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!test.stop) {
                        test.num++;
                        if (test.num % 100000 == 0)
                            System.out.println("while num: " + test.num);
                        ;
                    }
                    System.out.println("num : " + test.num);
                }
            }).start();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(100);
                    test.stop = true;
                    System.out.println("set stop = true");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        /*while (Thread.activeCount() > 1)  //保证前面的线程都执行完
            Thread.yield();*/
        System.out.println("最终结果： "+test.num);
    }
}
