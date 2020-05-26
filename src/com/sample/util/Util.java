package com.sample.util;

import org.openjdk.jol.info.ClassLayout;

/**
 * Created by jiek on 2020/4/15.
 * <p>
 * 计算对象内存大小
 * 1. JOL java object layout库
 * ClassLayout.parseInstance(obj).toPrintable()
 * <p>
 * 2. jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize(obj)
 */
public class Util {

    public static void main(String[] args) {

        Util.printObjSize(1);
        Util.printObjSize(true);
        Util.printObjSize((short) 123);
        Util.printObjSize('a');
        Util.printObjSize('中');
        Util.printObjSize("adfasdfa 中国");
        Util.printObjSize(12345678909876l);
        Util.printObjSize(3.2f);
        Util.printObjSize(Math.PI);

        char[] bs = new char[1 << 6 + 1];// 对象头占16Byte   1个boolean 数组型1Byte,不满2^用 padding补齐
        printObjSize(bs);
        printObjSize(Integer.class);
        printObjSize(null);

        p("  sizeof = " + jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize(false));
    }

    //计算对象内存占用
    // ref:  https://www.cnblogs.com/ulysses-you/p/10060463.html
    public static void printObjSize(Object obj) {
        p(obj);
        p("  sizeof = " + jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize(obj));
        p("JOL sizeof = " + ClassLayout.parseInstance(obj).toPrintable());
    }

    public static void p(Object obj) {
        if (obj != null) {
            System.out.println(obj.getClass().getName() + (obj.getClass().isArray() ? "[] : " : " : ") + obj);
        } else {
            System.out.println("null");
        }
    }

    public static void splitLine() {
        System.out.println("\n-----------------");
    }

    public static void splitLine(String msg) {
        System.out.println("\n\t========== " + msg + " ========");
    }
}
