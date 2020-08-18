package com.sample.util;

import org.openjdk.jol.info.ClassLayout;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 * Created by jiek on 2020/4/15.
 * <p>
 * 计算对象内存大小
 * 1. JOL java object layout库
 * ClassLayout.parseInstance(obj).toPrintable()
 * <p>
 * 2. jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize(obj)
 * <p>
 * JVM（十）Mac下hsdis和jitwatch   看锁与查看与分析HotSpot JIT compiler生成的汇编代码
 * <p>
 * IntelliJ -> View -> showBytecode 看字节码
 * <p>
 * 内存对齐是以【8bytes】的整数部来对齐
 */
public class Util {

    public static void main(String[] args) {
//objectheader 包括 MarkWord KlassWord
        Object obj = new Object();//压缩：（8+4）+ 4 +4p = 24bytes ; 不压缩：(8+8)+ 4 + 4p = 32bytes
        Util.printObjSize(obj);
//        obj.hashCode();
//        Util.printObjSize(obj);
        /*synchronized (obj) {
            obj.hashCode();
            Util.printObjSize(obj);
        }*/
//        Util.printObjSize(obj);
//        Util.printObjSize(1);//压缩：（8+4）+ 4 + 0p = 16bytes ;
//        Util.printObjSize(true);//压缩：（8+4）+ 1 + 3p = 16bytes ;
//        Util.printObjSize((short) 123);//压缩：（8+4）+ 2 + 2p = 16bytes ;
//        Util.printObjSize('a');//压缩：（8+4）+ 2 + 2p = 16bytes ;
//        Util.printObjSize('中');//压缩：（8+4）+ 2 + 2p = 16bytes ;
//        Util.printObjSize("adfasdfa 中国");//压缩：（8+4）+ （4 char[] value + 4 hash）+4p = 24bytes ; 不压缩：（8+8）+ （8+4）+4p = 32bytes
//        Util.printObjSize(new String("adfasdfa 中国"));//压缩：（8+4）+ （4char[] value + 4 hash）+4p = 24bytes ; 不压缩：（8+8）+ （8+4）+4p = 32bytes
//        Util.printObjSize(12345678909876l);//压缩：（8+4）+ 4p + 8 = 24bytes ;//会在编译时自动装箱成 Long.valueof //不解此对齐在对象头后值前？？？
//        Util.printObjSize(3.2f);//压缩：（8+4）+ 4 + 0p = 16bytes ;//会在编译时自动装箱成 Float.valueof
//        Util.printObjSize(Math.PI);//压缩：（8+4）+ 4p + 8 = 24bytes ;//会在编译时自动装箱成 Double.valueof //不解此对齐在对象头后值前？？？
//
//        char[] bs = new char[1 << 6 + 1];//数组分配了128的长度； 对象头占8+4 byte + 4 + 65分配128*2个字节 = 272
//        printObjSize(bs);
//        int[] i = new int[1 << 7 + 1];//数组分配了128的长度； 对象头占8+4 byte + 4 + 129分配256*4个字节 = 1040
//        printObjSize(i);
//        byte[] b = new byte[1 << 7 + 1];//数组分配了128的长度； 对象头占8+4 byte + 4 + 129分配256*1个字节 = 272
//        printObjSize(b);

//        引用类型都是指针，不压缩打针是8Bytes，压缩指针4bytes,默认压缩
//        String[] s = new String[1 << 7 + 1];//数组分配了128的长度； 对象头占8+4 byte + 4 + 129分配256*4个字节  = 1040
//        printObjSize(s);
//        Boolean[] bb = new Boolean[1 << 7 + 1];//数组分配了128的长度； 对象头占8+4 byte + 4 + 129分配256*4个字节 = 1040
//        printObjSize(bb);
//        boolean[] bbb = new boolean[1 << 7 + 1];//数组分配了128的长度； 对象头占8+4 byte + 4 + 129分配256*1个字节 = 272
//        printObjSize(bbb);


        /**
         * java.lang.Class : class java.lang.Integer
         java.lang.String : JOL sizeof = java.lang.Class object internals:
         OFFSET  SIZE                                              TYPE DESCRIPTION                               VALUE
         0     4                                                   (object header)                           01 fd 9b c2 (00000001 11111101 10011011 11000010) (-1029964543)
         4     4                                                   (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
         8     4                                                   (object header)                           df 03 00 f8 (11011111 00000011 00000000 11111000) (-134216737)
         12     4                     java.lang.reflect.Constructor Class.cachedConstructor                   null
         16     4                                   java.lang.Class Class.newInstanceCallerCache              null
         20     4                                  java.lang.String Class.name                                (object)
         24     4                                                   (alignment/padding gap)
         28     4                       java.lang.ref.SoftReference Class.reflectionData                      (object)
         32     4   sun.reflect.generics.repository.ClassRepository Class.genericInfo                         null
         36     4                                java.lang.Object[] Class.enumConstants                       null
         40     4                                     java.util.Map Class.enumConstantDirectory               null
         44     4                    java.lang.Class.AnnotationData Class.annotationData                      null
         48     4             sun.reflect.annotation.AnnotationType Class.annotationType                      null
         52     4                java.lang.ClassValue.ClassValueMap Class.classValueMap                       null
         56    32                                                   (alignment/padding gap)
         88     4                                               int Class.classRedefinedCount                 0
         92   476                                                   (loss due to the next object alignment)
         Instance size: 568 bytes
         Space losses: 36 bytes internal + 476 bytes external = 512 bytes total
         */
        printObjSize(Integer.class);

//        printObjSize(null);//0; 会崩溃，java.lang.NullPointerException at org.openjdk.jol.info.ClassData.parseInstance(ClassData.java:53)
//
//        p("  sizeof = " + jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize(false));
    }

    //计算对象内存占用
    // ref:  https://www.cnblogs.com/ulysses-you/p/10060463.html

    /**
     * java -XX:+/-UseCompressedOops  //OrdinaryObjectPointer 普通对象打针压缩
     *
     * @param obj
     */
    public static void printObjSize(Object obj) {
        p(obj);
//        p("  sizeof = " + jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.getObjectSize(obj));
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



    public static void traverseList(List<String> list) {
        Util.splitLine("traverse the List using for each");
        for (String s : list) {
            System.out.println(s);
        }
        traverseListIterator(list.listIterator());
        traverseIterator(list.iterator());
    }

    public static void traverseListIterator(ListIterator listIterator) {
        Util.splitLine("traverse the List using ListIterator");
        while (listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }
    }

    public static void traverseIterator(Iterator iterator) {
        Util.splitLine("traverse the List using Iterator");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }


//    以下为 int[] Integer[] List<Integer>三个互转方式，都需要外部进行判空处理
    public static int[] getArray(Integer[] arr) {
        return Arrays.stream(arr).mapToInt(Integer::valueOf).toArray();
    }

    public static int[] getArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }

    public static Integer[] getArrayBoxed(int[] arr) {
        return Arrays.stream(arr).boxed().toArray(Integer[]::new);
    }

    public static Integer[] getArrayBoxed(List<Integer> list) {
        return list.toArray(new Integer[0]);
    }

    public static List<Integer> getList(Integer[] arr) {
        return Arrays.asList(arr);
    }

    public static List<Integer> getList(int[] arr) {
        return Arrays.stream(arr).boxed().collect(Collectors.toList());
    }
}
