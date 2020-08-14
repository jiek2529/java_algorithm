package com.sample.interview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jiek on 2020/8/14.
 * <p>
 * 常见基础写法与用法
 * <p>
 * 利用
 * > ps -ef | grep EasyWrong #找进程 Pid；
 * > jps -lvm #查当前运行的 java 进程
 * > jstack -l {pid}  #排查死循环的位置
 */
public class EasyWrong {
    public static void main(String[] args) {
        method_1();//unicode 的回车换行可能导致的运行异常，没复现，但存在可能性

        method_2();//+= 与 =+的写法错误

//        method_3();//；分号误输入问题  // 利用 > ps -ef | grep EasyWrong #找进程 Pid；  > jstack -l {pid}  #排查死循环的位置

        method_4();//Integer自动装箱与缓存问题

        method_5();//代码块要加括号的场景

        method_6();//char ascii 问题

        method_7();// 遍历后删除导致的下标越界

        method_8();// 短路与，false && statement 导致后一语句不会执行

        method_9();//switch条件要以break隔断，否则会条件穿透

        method_10();// 数组下标由0开始计算

    }

    /**
     * 数组下标都由0开始计数
     */
    private static void method_10() {
        int[] arr = new int[]{1, 3, 5, 7, 9};
        for (int i = 0; i < arr.length; i++) {
            System.out.println("the element is:" + arr[i]);
        }

        String str = "hello world";
        System.out.println(str.charAt(1));//结果 ：e
    }

    /**
     * switch case 需要用break 隔断条件，否则会条件穿透
     */
    private static void method_9() {
        int count = 1;
        switch (count) {
            case 1:
                System.out.println("one");
            case 2:
                System.out.println("two");
            case 3:
                System.out.println("three");
        }
//        结果： one
//              two
//              three
    }

    //    短路与
    private static void method_8() {
        int num = 2;
        if (false && num++ == 3) {//短路与会导致后边条件不被执行，当种语句可考虑使用在日志等模块上。
            System.out.println("hello world");
        }
    }

    /**
     * for删除 List 成员 时，导致的 IndexOutOfBoundsException 越界
     * 建议用迭代器，或倒序遍历
     */
    private static void method_7() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("b");
        list.add("c");
        list.add("d");


        {//倒序遍历，避免下标越界
            int length = list.size();
            for (int i = length - 1; i >= 0; i--) {
                if (list.get(i).equals("d")) {
                    list.remove(i);//会导致越界可能
                }
            }
            System.out.println(list);
        }

        {//迭代器遍历与删除数据
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key.equals("b")) {
//                list.remove(key);//迭代器中，不可使用此方式去删除原数据； 抛java.util.ConcurrentModificationException
                    iterator.remove();
                }
            }
            System.out.println(list);
        }

        /*{//顺序遍历删除存在下标越界风险
            int length = list.size();
            for (int i = 0; i < length; i++) {
                if (list.get(i).equals("b")) {
                    list.remove(i);//会导致越界可能
                }
            }
            System.out.println(list);
        }*/
    }

    /**
     * char '8' 是 ascii 值，会编译时转成 char 型 56，即8的ascii 码
     */
    private static void method_6() {
        char symbol = '8';//ascii 8,而不是数值8，编译时直接会转成数值56
        System.out.println(symbol);
        System.out.println((int) symbol);//ascii 56，而不是8
    }

    /**
     * 代码块要加括号的场景
     * <p>
     * 期盼输出 ABABAB，真实结果为 AAAB
     */
    private static void method_5() {
        int i = 0;
        while (i++ < 3)
            System.out.print("A");
        System.out.print("B");//多语句同行，不会隐式加括号
    }

    /**
     * 自动装箱与缓存问题
     */
    private static void method_4() {
        Integer a = 100;//自动装箱 编译时技术Integer.valueOf(100)
        Integer b = 100;
        Integer c = 200;
        Integer d = 200;
        System.out.println(a == b);//true  比较内存地址，IntegerCache [-128, 127]
        System.out.println(c == d);//false  比较内存地址
    }

    /**
     * 在while 条件后误输入入了分号，导致死循环
     * <p>
     * 利用 IDE 的 code format，使左大括号在 while 后，
     * <p>
     * 可利用 > ps -ef | grep EasyWrong #找进程 Pid
     * > jstack -l {pid}  #排查死循环的位置
     */
    private static void method_3() {
        int i = 1;
        while (i < 4) ;        //此处误输入了分号
        {                      // 误输入了个分号，且不易发现，导致的问题，需要使用code format使有问题的代码能在review 或 push 时能更易看出误输入的分号
            System.out.println(i++);
        }
    }

    /**
     * += 与 =+的写法错误问题
     */
    private static void method_2() {
        int num = 100;
        num += 2; //num = num + 2;
        System.out.println(num); //102

        num = +2; // num = +2;//带符号数2
        System.out.println(num); // 2
    }

    /**
     * unicode 的回车可能导致编译忽略场景，执行不该打印的语句
     */
    private static void method_1() {
//        u000d  u000a  回车 换行 System.out.println("Hello World!");
    }
}
