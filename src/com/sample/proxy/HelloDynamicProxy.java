package com.sample.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by jiek on 2020/5/19.
 *
 * 动态代理
 */
public class HelloDynamicProxy<T> {
    T iHello;

    public HelloDynamicProxy(T iHello) {
        this.iHello = iHello;
    }

    public Object getInstance() {
        return Proxy.newProxyInstance(iHello.getClass().getClassLoader(), iHello.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("【HelloDynamicProxy】 Before say hello...");
                Object ret = method.invoke(iHello, args);
                System.out.println("【HelloDynamicProxy】 After say hello...");
                return ret;
            }
        });
    }
}
