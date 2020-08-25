package com.sample.leetcode.extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jiek on 2020/8/22.
 * <p>
 * 筛法求质数
 * <p>
 * 可按范围，3-10查质数级：结果 [3,5,7]
 * <p>
 */
public class FindPrimeNumbers {

    public static void main(String[] args) {
        int maxVal = 10_0;

        FindPrimeNumbers fp = new FindPrimeNumbers();
        List<Integer> primeNumbers = fp.solvePrime(maxVal);
        System.out.println(maxVal + " 范围内的质数：" + primeNumbers);
    }

    /**
     * 求范围内所有质数
     * <p>
     * 质数特点：每一个质数的倍数都不是质数
     * bool状态数组标记是否为质数
     *
     * @param maxVal 求质数最大范围 [0, maxVal]
     */
    public List<Integer> solvePrime(int maxVal) {
//        包含输入的值为检测范围
        maxVal++;
//        标记过的数不再做质数判断
        boolean[] isNotPrimes = new boolean[maxVal];

//        初始化所有数都是质数，由倍数处理掉所有非质数位
        Arrays.fill(isNotPrimes, false);
        // 0 ,1 都不是质数
        isNotPrimes[0] = true;
        isNotPrimes[1] = true;

//        存储所有计算出的质数
        List<Integer> list = new ArrayList<>();
        for (int i = 2; i < maxVal; i++) {
//            前质数的倍数处理过的都为非质数，直接跳转至下一个数再判断
            if (isNotPrimes[i]) {
                continue;
            }
//            不能被前质数整除的数，一定是质数
            list.add(i);
//            当前质数的倍数都表示为非质数
            for (int j = i; j < maxVal; j += i) {
//                因质数检测是从小至大，所以当前质数和它的倍数都被标记为 true
                isNotPrimes[j] = true;
            }
        }
        return list;
    }
}
