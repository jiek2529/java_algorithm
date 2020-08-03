package com.sample.lang;

interface MyInterface {
    //可定常量，不可定义成员
    int a = 1;//隐式 public static final

    //    所有方法必须为抽象方法，且不用加修饰符
//    public /*protected 缺省，但不可加此修饰符*/ /*abstract 缺省*/ void fun1();
}
