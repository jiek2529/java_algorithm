package com.sample.lang;

/**
 * Created by jiek on 2020/6/11.
 * <p>
 * Java 8新特性之 Lambda 表达式
 * <p>
 * <p>
 * // 1. 无参数, 返回值 1
 * () -> 1;
 * <p>
 * // 2. 一个入参(数字类型),返回2倍值
 * x -> 2 * x;
 * <p>
 * // 3. 二个入参(数字),返回它们的差值
 * (x, y) -> x – y;
 * <p>
 * // 4. 二个int型整数,返回他们的和
 * (int x, int y) -> x + y;
 * <p>
 * // 5. 一个string入参，打印，并不返回任何值
 * (String s) -> System.out.print(s);
 * <p>
 * 详情参考： https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
 */
public class LambdaMain {
    public static void main(String[] args) {

        LambdaMain main = new LambdaMain();

        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;

        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + main.operate(10, 5, addition));
        System.out.println("10 - 5 = " + main.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + main.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + main.operate(10, 5, division));

        // 不用括号
        GreetingService greetService1 = message ->
                System.out.println("Hello, " + message);

        // 用括号
        GreetingService greetService2 = (message) ->
                System.out.println("Hello, " + message);

        greetService1.sayMessage("Jiek!");
        greetService2.sayMessage("Guys!");
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String msg);
    }
}
