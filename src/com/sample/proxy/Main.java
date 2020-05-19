package com.sample.proxy;

/**
 * Created by jiek on 2020/5/19.
 *
 * 测试动态代理与静态代理模式
 */
public class Main {
    public static void main(String[] args) {
        testStaticProxy();

        testDynamicProxy();
    }

    private static void testDynamicProxy() {
        IHello iHello = new HelloDelegate();
        IHello proxy = (IHello) new HelloDynamicProxy(iHello).getInstance();
        proxy.saySomething();
        pl();
    }

    /**
     * 实验静态代理
     */
    private static void testStaticProxy() {
        IHello iHello = new HelloDelegate();
        IHello proxy = new HelloStaticProxy(iHello);
        proxy.saySomething();
        pl();
    }

    private static void pl() {
        System.out.println("\n============\n");
    }

}
