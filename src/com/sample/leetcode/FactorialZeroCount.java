package com.sample.leetcode;

/**
 * Created by jiek on 2020/9/8.
 *
 * 计算 N！阶乘的尾数零的个数
 *
 * 原理：当两数相乘=10，就产生一个零，有2*5=10。
 *      阶乘：从 N * (N-1) *...*1
 *      将所有数都因式分解后，产生的2的个数与5的个数，取个数最少的数，即5在乘式的个数，就是结果的个数
 *      如：9！ 中2的倍数有2 4 6 8，而5的倍数只有一个5.所以 9！结果零的个数为1。
 *         10！ = 9！ * 10 = 9！ * 2 * 5 结果有两个零
 *         以此类推
 *
 * 示例：
 *      3! 结果尾数中共有 0 个0。
 *      5! 结果尾数中共有 1 个0。
 *      10! 结果尾数中共有 2 个0。
 *      11! 结果尾数中共有 2 个0。
 *      20! 结果尾数中共有 4 个0。
 *      100! 结果尾数中共有 24 个0。
 *      10000! 结果尾数中共有 2499 个0。
 *      100000! 结果尾数中共有 24999 个0。
 */
public class FactorialZeroCount {
    public static void main(String[] args) {
        solve(3);
        solve(5);
        solve(10);
        solve(11);
        solve(20);
        solve(100);
        solve(10_000);
        solve(100_000);
    }

    private static void solve(int maxNum) {
        int count = 0;
        int base = 5;
        for (int i = base; i <= maxNum; i *= 5) {
            //步长为base 的
            for (int j = i; j <= maxNum; j += i) {
                count++;
            }
//            System.out.println("\ti = "+i+"  > count= "+count);
        }

        System.out.println(maxNum+"! 结果尾数中共有 "+count+" 个0。");
    }
}
