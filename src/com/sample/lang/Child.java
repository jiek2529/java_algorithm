package com.sample.lang;

/**
 * 静态代码块与非静态代码块的执行顺序
 * <p>
 * 编译后的代码结构
 * <p>
 * package com.sample.lang;
 *
 * public class Child extends Parent implements MyInterface {
 *     int num;
 *     String name;
 *
 *     public Child(int num) {
 *         System.out.println("Child normal 0 ");
 *         this.name = "n1";
 *         System.out.println("Child normal 1 ");
 *         System.out.println("Child normal 2 ");
 *         this.num = num;
 *         System.out.println("Child construction: 有参");
 *     }
 *
 *     public Child() {
 *         System.out.println("Child normal 0 ");
 *         this.name = "n1";
 *         System.out.println("Child normal 1 ");
 *         System.out.println("Child normal 2 ");
 *         this.num = this.num;
 *         System.out.println("Child construction: 无参");
 *     }
 *
 *     public String toString() {
 *         return "Child {num=" + this.num + ", name='" + this.name + '\'' + '}';
 *     }
 *
 *     public static void main(String[] args) {
 *         System.out.println(new Child());
 *         System.out.println("==========\n\n");
 *         System.out.println(new Child(5));
 *         System.out.println("==========\n\n");
 *         System.out.println(new Child());
 *     }
 *
 *     static {
 *         System.out.println("Child static 1 ");
 *         System.out.println("Child static 2 ");
 *     }
 * }
 * <p>
 * 1. 静态代码块依顺序优先执行，由类加载后执行一次
 * 2. 非静态代码块，在构造方法前加入构造方法内前端
 * 3. 子类构造函数默认隐式加了 super(),先父后子
 */
public class Child extends Parent implements MyInterface {
    int num;

    {
        System.out.println("Child normal 0 ");
    }

    static {
        System.out.println("Child static 1 ");
    }

    String name = "n1";

    {
        System.out.println("Child normal 1 ");
    }

    public Child(int num) {
//        super();
        this.num = num;
        System.out.println("Child construction: 有参");
    }

    public Child() {
//        super(8);
        this.num = num;
        System.out.println("Child construction: 无参");
    }

    static {
        System.out.println("Child static 2 ");
    }

    {
        System.out.println("Child normal 2 ");
    }

    @Override
    public String toString() {
        return "Child {" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(new Child());
        System.out.println("==========\n\n");
        System.out.println(new Child(5));
        System.out.println("==========\n\n");
        System.out.println(new Child());
    }
}
