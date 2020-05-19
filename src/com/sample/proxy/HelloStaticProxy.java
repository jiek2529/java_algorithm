package com.sample.proxy;

/**
 * Created by jiek on 2020/5/19.
 */
public class HelloStaticProxy implements IHello {

    IHello delegate;

    public HelloStaticProxy(IHello delegate) {
        this.delegate = delegate;
    }

    @Override
    public void saySomething() {
        System.out.println("【HelloStaticProxy】 say something before delegate!");
        delegate.saySomething();
        System.out.println("【HelloStaticProxy】 say something after delegate!");
    }
}
