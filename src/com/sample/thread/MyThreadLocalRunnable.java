package com.sample.thread;

/**
 * Created by jiek on 2020/4/16.
 *
 * 测试 ThreadLocal，一线程一关联值
 */
public class MyThreadLocalRunnable implements Runnable {

    ThreadLocal<Integer> threadLocalValue = new ThreadLocal<Integer>();
//    ThreadLocal<String> threadLocalValue_string = new ThreadLocal<>();
    private int mInt = 0;

    public void run() {

        mInt += 1;
        threadLocalValue.set(mInt);
        mInt += 10;

        System.out.println(Thread.currentThread().getName()
                + " \t mInt:" + mInt
                + "   \t threadLocal:" + threadLocalValue.get());

        try {
            Thread.sleep(2000);//延时可能会导致线程过多时，线程池无法分配执行，导致抛出异常java.util.concurrent.RejectedExecutionException
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()
                + " \t mInt:" + mInt
                + "   \t threadLocal:" + threadLocalValue.get());
    }
}
