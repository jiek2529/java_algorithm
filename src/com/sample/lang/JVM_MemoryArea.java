package com.sample.lang;

/**
 * 罗列变的成员与方法在 JVM 中的位置
 * <p>
 * Created by jiek on 2020/4/14.
 */
public class JVM_MemoryArea {

    public static void main(String[] args) {//stack中
        Person person = new Person();//stack中
        //时间局部变量和形参 args 都在 main 的栈桢中
    }

}

class Person {


//    name1 & name2  & "cn" 都在方法区       , 但 new String("cn")对象是在 heap 中分配空间
    static String name1 = "cn";
    static String name2 = new String("cn");

//    name & age 实例变量在 heap 中分配空间
    private String name;
    private int age;

//    数据区 堆中分配空间
    private int[] nums = new int[10];

    private String tmp = "a" + "b" + "c";

    public Person(String name, int age) {
        this.name = name;
        this.age = age;

//        this、形参 name、age 构造方法被调用时，会在构造方法的栈桢中开避空间
    }

    public Person() {
    }

    //该方法在方法区中
    public static void showCountry() {
        System.out.println("country : " + name1);
    }

    //该方法在方法区中
    public void setName(String name) {
        this.name = name;
    }

    //该方法在方法区中
    public void speak() {
        System.out.println("say something.");
    }
}
