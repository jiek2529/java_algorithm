package com.sample.lang;

/**
 * 静态代码块与非静态代码块的执行顺序
 * <p>
 * 编译后的代码结构
 * <p>
 * package com.sample.lang;
 *
 * public abstract class Parent {
 *     int num;
 *     String name;
 *
 *     Parent(int num) {
 *         System.out.println("Parent normal 0 ");
 *         this.name = "n1";
 *         System.out.println("Parent normal 1 ");
 *         System.out.println("Parent normal 2 ");
 *         this.num = num;
 *         System.out.println("Parent construction: 有参");
 *     }
 *
 *     Parent() {
 *         System.out.println("Parent normal 0 ");
 *         this.name = "n1";
 *         System.out.println("Parent normal 1 ");
 *         System.out.println("Parent normal 2 ");
 *         this.num = this.num;
 *         System.out.println("Parent construction: 无参");
 *     }
 *
 *     public String toString() {
 *         return "Parent{num=" + this.num + ", name='" + this.name + '\'' + '}';
 *     }
 *
 *     static {
 *         System.out.println("Parent static 1 ");
 *         System.out.println("Parent static 2 ");
 *     }
 * }
 * <p>
 * 1. 静态代码块依顺序优先执行，由类加载后执行一次
 * 2. 非静态代码块，在构造方法前加入构造方法内前端
 * 3. 子类构造函数默认隐式加了 super(),先父后子
 */
public /*缺省为 protected，但不可用此修饰符*/ abstract class Parent {
    int num;

    {
        System.out.println("Parent normal 0 ");
    }

    static {
        System.out.println("Parent static 1 ");
    }

    String name = "n1";

    {
        System.out.println("Parent normal 1 ");
    }

    Parent(int num) {
        this.num = num;
        System.out.println("Parent construction: 有参");
    }

    Parent() {
        this.num = num;
        System.out.println("Parent construction: 无参");
    }

    static {
        System.out.println("Parent static 2 ");
    }

    {
        System.out.println("Parent normal 2 ");
    }

    @Override
    public String toString() {
        return "Parent{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }

//    public static void main(String[] args) {
//        System.out.println(new Parent());
//        System.out.println("==========\n\n");
//        System.out.println(new Parent(5));
//        System.out.println("==========\n\n");
//        System.out.println(new Parent());
//    }
}
