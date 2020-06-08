package com.sample.thread;

/**
 * Created by jiek on 2020/6/3.
 */
public class ThreadJoin extends Thread {
    Thread preThread;

    public ThreadJoin(Thread preThread) {
        this.preThread = preThread;
    }

    public static void main(String[] args) {
        Thread previousThread = Thread.currentThread();
        for (int i = 1; i <= 10; i++) {
            Thread curThread = new ThreadJoin(previousThread);
            curThread.start();
            System.out.println("for : "+i);
            previousThread = curThread;
        }
    }

    @Override
    public void run() {
        try {
            System.out.println(" this run: "+this.getName());
            preThread.join();
            System.out.println("thread:" + preThread.getName() + " terminated.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
