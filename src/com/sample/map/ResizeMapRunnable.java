package com.sample.map;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jiek on 2020/6/7.
 * <p>
 * 模拟线程不停添加新成员，导致不停扩容，而产生多线程时的死锁
 */
public class ResizeMapRunnable implements Runnable {
    private static HashMap<Integer, Integer> map = new HashMap<>(2);

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    @Override
    public void run() {
        for (int i = 0; i < 100_0000; i++) {
            map.put(atomicInteger.get(), atomicInteger.get());
            atomicInteger.incrementAndGet();
            if (i % 1000 == 0) {
                System.out.println(Thread.currentThread().getName()+"   \t "+i);
            }
        }
    }
}
