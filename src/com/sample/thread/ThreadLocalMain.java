package com.sample.thread;

import java.util.concurrent.*;

/**
 * Created by jiek on 2020/4/16.
 */
public class ThreadLocalMain {

    private static int core_pool_size = 4;
    private static int max_pool_size = 20;
    //如果空闲立即退出
    private static long keep_alive_time = 0L;
    //队列的容量是0
    private static BlockingQueue queue = new SynchronousQueue();
    //队列容量为1
    private static BlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(1);

    public static ExecutorService initThreadPool() {
        ExecutorService executorService = new ThreadPoolExecutor(
                core_pool_size, max_pool_size, keep_alive_time, TimeUnit.SECONDS, arrayBlockingQueue//queue
        );
        return executorService;
    }


    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        testThreadPool();

        testDaemon();
    }

    /**
     * 测试线程池与 ThreadLocal
     */
    private static void testThreadPool() {
        MyThreadLocalRunnable normalThread = new MyThreadLocalRunnable();
        ExecutorService executorService = initThreadPool();
        for (int i = 0; i < 20; i++) {
            try {
                executorService.execute(normalThread);//立即执行
//                executorService.submit(normalThread);//放入线程池
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
        executorService.shutdown();
    }

    /**
     * 测试守护线程的中断状态。
     */
    private static void testDaemon() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    System.out.println("before for");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("主线程运行结束时，不会触发此休眠被中断异常");
                    } finally {
                        System.out.println("主线程运行结束时，不会触发此休眠被中断的 finally 方法");
                    }
                    System.out.println("in for");
                }
            }
        });
        System.out.println("before thread run");
        thread.setDaemon(true);
        thread.start();
        try {
//            thread.join();//此句会导致 Thread 优先执行
            System.out.println("before main thread sleep 800 millisecond");
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after thread run");
    }
}
