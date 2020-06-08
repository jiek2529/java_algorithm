package com.sample.lang;

import java.math.BigDecimal;

/**
 * Created by jiek on 2020/6/5.
 * <p>
 * 浮点运算的坑，与解决方案
 */
public class FloatingPointMathMain {
    public static void main(String[] args) {
//        primitiveFloatMathError();
//        boxingFloatMathError();
//
//        floatCompare();
//
//        recommendUsage();

        primitiveType();
    }

    /**
     * 基本类型与其封装类的赋值注意事项。
     */
    private static void primitiveType() {
        Double d = 13.0;
        d = 1.0D;
        Float f = 1.0F;// 1.0 X
        float f1 = 1.0F;// 1.0 X
        Long l = 1L;// 1 X
        long l1 = 1;//会自动编译成1L
        long l2 = 1L;

        /**
         * BigDecimal 当不可被整除时，会抛出异常，『ArithmeticException: Non-terminating decimal expansion; no exact representable
         * decimal result.』
         * 应使用加有效位来控制。
         */
        BigDecimal bigDecimal = new BigDecimal(Double.toString(d)).divide(new BigDecimal("3"), 5, BigDecimal
                .ROUND_HALF_UP);
        System.out.println(bigDecimal.doubleValue());
    }

    /**
     * 推荐浮点数运算注意项，Float 有效位7~8位，Double 有效位17~18位
     */
    private static void recommendUsage() {
        System.out.println("\n------------");
        BigDecimal unrecommend = new BigDecimal(0.1f);// 实际的存储值为:0.10000000149
        System.out.println(unrecommend);

//        正例:优先推荐入参为 String 的构造方法，或使用 BigDecimal 的 valueOf 方法，此方法内部其实执行了
//        Double 的 toString，而 Double 的 toString 按 double 的实际能表达的精度对尾数进行了截断。

        BigDecimal recommend1 = new BigDecimal("0.2");
        BigDecimal recommend2 = BigDecimal.valueOf(0.1);
        System.out.println(recommend1);
        System.out.println(recommend2);
        System.out.println(recommend1.subtract(recommend2));
        System.out.println(recommend1.add(recommend2));

//        1.2345678901234568E-21
        BigDecimal bigDecimal = BigDecimal.valueOf(0.123456789012345678901234567890123456789E-20);
//        0.12345678901234568
//        BigDecimal bigDecimal = BigDecimal.valueOf(0.123456789012345678901234567890123456789);
        // 1.23456789012345677E+188
//        BigDecimal bigDecimal = BigDecimal.valueOf(1234567890123456789.01234567890123456789D);//double 是16位有效位
        BigDecimal subtract = bigDecimal.subtract(recommend2).add(recommend2);
        System.out.println(bigDecimal);
        System.out.println(subtract);
        System.out.println(subtract.doubleValue());


        String str = "a,b,c,,,,g,,";
        String[] ary = str.split(",");// 预期大于 7，结果是 7
        System.out.println(ary.length);
    }

    private static void floatCompare() {
        {
            System.out.println("(1) 指定一个误差范围，两个浮点数的差值在此范围之内，则认为是相等的。");
            float a = 1.0f - 0.9f;
            float b = 0.9f - 0.8f;
            float diff = 1e-6f;
            if (Math.abs(a - b) < diff) {
                System.out.println("两值差小于1e-6表示两值相同");
            }
        }
        {
            System.out.println(" (2) 使用 BigDecimal 来定义值，再进行浮点数的运算操作。");
            BigDecimal a = new BigDecimal("1.0");
            BigDecimal b = new BigDecimal("0.9");
            BigDecimal c = new BigDecimal("0.8");
            BigDecimal x = a.subtract(b);
            BigDecimal y = b.subtract(c);
            if (x.equals(y)) {
                System.out.println("BigDecimal的减法可以进行加减后的 equals");
            }
        }
    }

    /**
     * 两个基本类型的计算比较错误
     */
    private static void primitiveFloatMathError() {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        if (a == b) {
// 预期进入此代码快，执行其它业务逻辑 // 但事实上 a==b 的结果为 false
        } else {
            System.out.println("float == float is false");
        }
    }

    /**
     * 两个基本类型 float 计算后的包装类进行比较错误
     */
    private static void boxingFloatMathError() {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        Float x = Float.valueOf(a);
        Float y = Float.valueOf(b);
        if (x.equals(y)) {
// 预期进入此代码快，执行其它业务逻辑
// 但事实上 equals 的结果为 false
        } else {
            System.out.println("Float .equals( Float) is false");
        }
    }
}
