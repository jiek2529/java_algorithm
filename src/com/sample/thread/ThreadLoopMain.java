package com.sample.thread;

import java.util.concurrent.*;

/**
 * Created by jiek on 2020/6/8.
 * <p>
 * 线程池，LinkedBlockingQueue链式阻塞队列：队列满时，新来数据直接丢弃
 */
public class ThreadLoopMain {
    public static void main(String[] args) {
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        int KEEP_ALIVE_TIME = 1;
        TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingDeque<>(4);//设置空间大小，可防止 OOM 的产生
        /*比 ArrayBlockingQueue 好，不用申请连续有容量限制的空间，还是线程安全的 AQS CAS 模式
        ArrayBlockingQueue{先进先出}；LinkedBlockingQueue{后进先出};*/
        ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2,
                //当等待队列满时，线程池中不满最大数量时，就进行新开线程直至最大线程数为止
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, taskQueue, new BackgroundThreadFactory()
                , new DefaultRejectedExecutionHandler()
                // default: AbortPolicy();//阻塞队列满了就不接受任务，抛异常RejectedExecutionException， 崩溃
        );

        System.out.println("内核数： " + NUMBER_OF_CORES);
        for (int i = 0; i < 100; i++) {
//            执行任务
            int finalI = i;
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + ": before: " + finalI);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": after: " + finalI);
            });
            System.out.println("线程池队列完成： " + i + " : " + executorService);
        }
        //阻塞队列为空或无活动状态线程时，关闭线程池
        while (!taskQueue.isEmpty()
                || ((ThreadPoolExecutor) executorService).getActiveCount() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("shutdown 线程池");
        executorService.shutdownNow();
    }

    private static class BackgroundThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }

    private static class DefaultRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("丢弃老线程，使用新线程替代：");//也可抛异常而终止应用
            executor.getQueue().poll();//丢弃队头 head；
            executor.getQueue().add(r);

        }
    }
}
