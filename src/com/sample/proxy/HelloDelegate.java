package com.sample.proxy;

/**
 * Created by jiek on 2020/5/19.
 *
 * 委托类 【Impl或 Delegate】
 */
public class HelloDelegate implements IHello {

    @Override
    public void saySomething() {
        System.out.println("\t【HelloDelegate】 say Something!");
    }
}
