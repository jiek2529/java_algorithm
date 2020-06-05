package com.sample.lang;

import java.math.BigDecimal;

/**
 * Created by jiek on 2020/6/5.
 * <p>
 * 浮点运算的坑，与解决方案
 */
public class FloatingPointMathMain {
    public static void main(String[] args) {
        primitiveFloatMathError();
        boxingFloatMathError();

        floatCompare();
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
