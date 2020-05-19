package com.sample.proxy;

/**
 * Created by jiek on 2020/5/19.
 * <p>
 * 测试动态代理与静态代理模式
 * <p>
 * 特点：
 *  1. 静态代理实现较简单，代理类在编译期生成，效率高。缺点是需要编写大量的代理类。
 *  2. 动态代理：不要求代理类和委托类实现同一个接口，但是委托类需要实现接口，动态代理类需要实现InvocationHandler接口。
 *  3. 动态代理：要求代理类实现InvocationHandler接口，通过反射代理方法调用。比较消耗系统性能，但可以减少代理类的数量，使用较灵活。
 * <p>
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
