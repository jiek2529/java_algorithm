package com.sample.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jiek on 2020/8/21.
 * <p>
 * 给定一个整数数组 nums ，小李想将 nums 切割成若干个非空子数组，使得每个子数组最左边的数和最右边的数的最大公约数大于 1 。为了减少他的工作量，请求出最少可以切成多少个子数组。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [2,3,3,2,3,3]
 * <p>
 * 输出：2
 * <p>
 * 解释：最优切割为 [2,3,3,2] 和 [3,3] 。第一个子数组头尾数字的最大公约数为 2 ，第二个子数组头尾数字的最大公约数为 3 。
 * <p>
 * 示例 2：
 * <p>
 * 输入：nums = [2,3,5,7]
 * <p>
 * 输出：4
 * <p>
 * 解释：只有一种可行的切割：[2], [3], [5], [7]
 * <p>
 * 限制：
 * 1 <= nums.length <= 10^5
 * 2 <= nums[i] <= 10^6
 * <p>
 * 解题思路：找质数 筛法；DP 动态规划；找范围质数与索引关系链；切段找结果。
 * 1. 找出2-√n 的所有质数列表（筛选法） ：如[2, 1_000_000] (10^6)的范围，只计算出10^3 千内的质数，因为 997 * 1009 > 百万
 * 2. 遍历 nums，用质数列表，求出每个数的所有质数（包含大于千的质数）
 * 3. 用 primeDP存质数的前一位最佳分组数，用 dp[i - 1] 与primeDP[pn]每个质数分组数比较，存入比较后的最小值
 * 4. 用 dp[i] i位置的值与此位置的每一个公约数的最佳分组数 + 1（前一位值 + 当前一步）比，存入比较后的最小值
 * 5. 计算到最后的dp[n]即为期望的最佳结果
 * <p>
 * 链接：https://leetcode-cn.com/problems/qie-fen-shu-zu
 */
public class SplitArray {
    static Solution sol;

    public static void main(String[] args) {
        solve(new int[]{4, 6, 8}, 1);                  //1  2-3-2
        solve(new int[]{2, 3, 2}, 1);                  //1  2-3-2
        solve(new int[]{2, 3, 3, 2, 3, 3}, 2);         //2  2-3-3-2 ；3-3
        solve(new int[]{2, 3, 5, 7}, 4);               //4  2 ；3 ；5 ；7
        solve(new int[]{2, 3, 2, 7, 3, 7, 7}, 2);      //2  2-3-2 ；7-3-7-7
        solve(new int[]{2, 3, 2, 3, 7, 3, 7, 7}, 3);   //3  2；3-2-3 ；7-3-7-7
        solve(new int[]{2, 3, 2, 3, 7, 3, 7, 14}, 1);  //1  2-3-2-3-7-3-7-14   2与14，公约数为2.所有只分一组
        solve(new int[]{2, 7, 5, 3, 3, 2, 3}, 2);      //2  2-7-5-3-3-2 ；3

        solve(new int[]{197597, 26083, 231529, 216133, 22483, 74411, 89087, 218681, 863, 228421, 214463, 224863,
                5737, 32941, 216103, 132689, 159737, 151241, 164309, 73643, 45121, 59981, 68821, 11197, 54679, 85213,
                138727, 89657, 102769, 112121, 136573, 27059, 77351, 109891, 94229, 173617, 224443, 149531, 84979,
                31013, 219409, 156749, 108233, 80107, 90173, 138899, 151057, 66683, 66683, 153911, 69959, 79451,
                75407, 159319, 7411, 78571, 128717, 52057, 55799, 128201, 125353, 214763, 12071, 152657, 81199,
                190391, 96779, 62659, 27997, 318559, 299113, 258691, 258031, 296713, 297533, 341477, 273271, 270659,
                296479, 262693, 270287, 247769, 246781, 308509, 289031, 298559, 246439, 318713, 317773, 260879,
                322237, 245851, 276623, 319237, 352589, 283463, 235111, 393203, 917327, 495371}, 99);      //99
        solve(new int[]{782581, 227, 113147, 13451, 288053, 684113, 413579, 917629, 454021, 827633, 673787, 514127,
                432001,
                507961, 790051, 164617, 139759, 315881, 160681, 235231, 106627, 135977, 483811, 570839, 758699,
                549011, 389227,
                21961, 524347, 24763, 882247, 932101, 717559, 124853, 967919, 968111, 966439, 967229, 967739, 968213,
                967171,
                966509, 967397, 967481, 968111, 967297, 968311, 967753, 966677, 968573, 966527, 966653, 967319,
                967663, 967931,
                968021, 967961, 968423, 966727, 967937, 967699, 966883, 968017, 968311, 967781, 966617, 967937,
                967763, 967459, 966971, 968567, 968501, 966991, 966613, 968557, 966863, 966619, 966863, 966727,
                967567, 967061, 966913, 966631, 968021, 968003, 968431, 968291, 969667, 970667, 971723, 969011,
                972113, 972373, 969929, 971491, 970027, 973031, 982973, 980491, 985657}, 63);  //63
//        {
//            int[] arr = new int[100_000];
//            Random r = new Random();
//            for (int i = 0; i < arr.length; i++) {
//                arr[i] = r.nextInt(1_000_000);
//            }
//            solve(arr);
//        }
    }

    static int solve(int[] nums, int expect) {
        if (sol == null) {
            sol = new Solution();
        }
        if (nums.length < 32) {
            System.out.println("\nsplitArray original arr: " + Arrays.toString(nums));
        } else {
            System.out.println("\nsplitArray original arr.length: " + nums.length);
        }
        long start = System.currentTimeMillis();
        int result = sol.splitArray(nums);
        StringBuilder sb = new StringBuilder();
        if (result == expect) {
            sb.append("正确：" + result);
        } else {
            sb.append("错误： " + result + "  期望值： " + expect);
        }
        System.out.println(sb.append("  >> usedTime: " + (System.currentTimeMillis() - start)).toString());
        return result;
    }

    /**
     * author: jiek
     * email: 252951151@qq.com
     * https://github.com/jiek2529
     */
    static class Solution {

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

        /**
         * author: jiek
         * email: 252951151@qq.com
         * https://github.com/jiek2529
         * <p>
         * DP 动态规划解决最佳分组数
         * <p>
         * dp[i]存当前位的最佳分组数
         * primeDp[pn]存质数 pn的最佳分组数
         *
         * @param nums
         * @return
         */
        public int splitArray(int[] nums) {
//        入参约束
            if (nums == null || nums.length == 0 || nums.length > 100_000) {
                return 0;
            }
//        数组的数的约束
            int scope = 1_000_000 + 1;
//        输入数组的长度
            int n = nums.length;
//        记录每一步的最佳分组数
            int[] dp = new int[n + 1];
//        默认所有值都为数组长度，即每个数都成一个组
            Arrays.fill(dp, n);
//        添加第一位占位符
            dp[0] = 0;

//        定义与值域范围长的数组，可按下标快速找到质数对应的值
//        质数的分组数 （即第一个出现 k 的公约数关联的分组数）
            int[] primesDP = new int[scope];
//        默认所有值都为数组长度，即每个数都成一个组
            Arrays.fill(primesDP, n);

//        最大值开方范围内的公约数集
            List<Integer> primeNumbers = solvePrime((int) Math.sqrt(scope) + 1);

//        nums 中当前遍历到的数 k的公约数集
            List<Integer> kHasPns = new ArrayList<>();
//        逐个数组中的数，计算每一位的最佳分组数
            for (int i = 1; i <= n; i++) {
                //当前加入的数
                int k = nums[i - 1];
//            清理上个数生成的公约数列表
                kHasPns.clear();

                for (int j = 0; j < primeNumbers.size(); j++) {
                    int curPN = primeNumbers.get(j);
//                当 k 被已经过遍历到的公约数都除尽后，余数小余当前质数时，结果只会是1，即提前结束
//                如 8 被 2 多次除后，结果就为1，就不用再去与质数3及之后的质数做处理了
                    if (k < curPN) break;
//                当前的质数是 k 的公约数
                    if (k % curPN == 0) {
                        kHasPns.add(curPN);
                        //当前数 k除尽当前质数
                        while (k % curPN == 0) {
                            k /= curPN;
                        }
                    }
                }
//            k==1 时，表示被前质被正好提前整除完
//            但 k 被所有质数除完后，可能还是质数。剩下未被整除的数一定是质数.
//            如 997是千内的最大质数据，但782581是大余千的质数
                if (k > 1) {
//                加入 k 的质数列表
                    kHasPns.add(k);
                }

//            遍历质数时的 k 的当前质数
                int pn;
                for (int j = 0; j < kHasPns.size(); j++) {
//                k的第 j 个公约质数的 dp 值
                    pn = kHasPns.get(j);
                    primesDP[pn] = Math.min(dp[i - 1], primesDP[pn]);
                }

//            所有质数的primesDP[pn] 的值与 dp[ i]中，取最小值
                for (int j = 0; j < kHasPns.size(); j++) {
//                k的第 j 个公约质数的 dp 值
                    pn = kHasPns.get(j);
//                质数的前一项的分组数，加一与当前的分组数比较，取小值
                    dp[i] = Math.min(dp[i], primesDP[pn] + 1);
                }
            }

            return dp[n];
        }
    }
}
