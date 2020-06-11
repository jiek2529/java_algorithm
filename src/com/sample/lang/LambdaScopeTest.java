package com.sample.lang;


import java.util.function.Consumer;

/**
 * Created by jiek on 2020/6/11.
 * <p>
 * Lambda 变量作用域验证
 */
public class LambdaScopeTest {
    int x = 1;

    public static void main(String[] args) {
        new LambdaScopeTest()
                .new InnerLambda()
                .testLambda(23);
    }

    class InnerLambda {
        int x = 2;

        public void testLambda(int x) {
            Consumer<Integer> consumer = (y) -> {
                System.out.println("x = " + x);// 23
                System.out.println("y = " + y);// 23
                System.out.println("this.x = " + this.x);// 2
                System.out.println("LambdaScopeTest.this.x = " + LambdaScopeTest.this.x);// 1
            };
            consumer.accept(x);
        }
    }
}
