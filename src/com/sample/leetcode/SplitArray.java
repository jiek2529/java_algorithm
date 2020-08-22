package com.sample.leetcode;

import com.sample.leetcode.bean.ListNode;

import java.util.*;

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
 * 1. 找出2-n 的所有质数列表（筛选法） ：如[2, 1_000_000] (10^6)的范围
 * 2. 在(1)中顺带把[2, 1_000_000]间每个数的最大质数，存于 map<num, Node<num, np>>
 * 3. 将 nums 转质数为键，索引为值列表（倒序）,最大索引在前
 * 4. 从 nums 的第一个数开始，（3）中找到下标索引最大的值（列表第一个数就是），统计切出子数组数+1；
 * 5. 将（4）的值+1，就是下一个数组的第一个数，重复（4）直至 数据处理完。
 * 6. 返回统计结果（也可根据每一段的断点位置，返回相关结果）。
 * <p>
 * 链接：https://leetcode-cn.com/problems/qie-fen-shu-zu
 */
public class SplitArray {
    static Solution sol;

    public static void main(String[] args) {
        solve(new int[]{4, 6, 8});                  //1  2-3-2
        solve(new int[]{2, 3, 2});                  //1  2-3-2
        solve(new int[]{2, 3, 3, 2, 3, 3});         //2  2-3-3-2 ；3-3
        solve(new int[]{2, 3, 5, 7});               //4  2 ；3 ；5 ；7
        solve(new int[]{2, 3, 2, 7, 3, 7, 7});      //2  2-3-2 ；7-3-7-7
        solve(new int[]{2, 3, 2, 3, 7, 3, 7, 7});   //3  2；3-2-3 ；7-3-7-7
        solve(new int[]{2, 3, 2, 3, 7, 3, 7, 14});  //3  2；3-2-3 ；7-3-7-14
        solve(new int[]{2, 7, 5, 3, 3, 2, 3});      //2  2-7-5-3-3-2 ；3
    }

    static int solve(int[] nums) {
        if (sol == null) {
            sol = new Solution();
        }
        System.out.println("\nsplitArray original arr: " + Arrays.toString(nums));
        long start = System.currentTimeMillis();
        int result = sol.splitArray(nums);
        System.out.println("result: " + result + "  >> usedTime: " + (System.currentTimeMillis() - start));
        return result;
    }

    static class Solution {

        /**
         * 输入的每个数对应的质数缓存
         * key 质数
         * val 从前向后的索引
         */
        Map<Integer, ListNode> cacheMap;
        /**
         * primeNumbersHM 范围内数对应最大公约质数
         * <p>
         * key 质数
         * val 从小到大的
         */
        Map<Integer, FindPrimeNumbers.PNNode> primeNumbersHM;
        boolean inited = false;

        /**
         * @param divisors       范围内质数，用于构建无冲突 Map 容量用
         * @param primeNumbersHM
         */
        private void initCacheMap(int[] divisors, Map<Integer, FindPrimeNumbers.PNNode> primeNumbersHM) {
            int mapLens = divisors.length;

            if (cacheMap == null) {
                int cacheLens = 1;
                while ((mapLens >> 1) > 0) {
                    mapLens >>= 1;
                    cacheLens++;
                }
//            使用最大公约质数的全1二进制 size 来存，根据数据找下标更快
                this.cacheMap = new HashMap<>(1 << cacheLens);
            }
            this.primeNumbersHM = primeNumbersHM;
        }

        /**
         * @param num   当前数
         * @param index 当前数在 输入数nums[]中的下标
         * @return Map key:最大公约质数，val 从前向后的下标链
         */
        void cacheMap(int num, int index) {
            FindPrimeNumbers.PNNode pnNode = primeNumbersHM.get(num);
            headAdd(pnNode.max_common_divisors, index);
        }

        /**
         * headNum 是 nums 中的下标，p
         *
         * @param pm      公约数
         * @param headNum nums 的下标
         */
        private void headAdd(int pm, Integer headNum) {
            ListNode head = cacheMap.get(pm);
            boolean headFirst = true;//true 头插入
            if (headFirst) {
                ListNode val = new ListNode(headNum);
                cacheMap.put(pm, val);
                val.next = head;//新数在链头
            } else {
                ListNode val = new ListNode(headNum);
                if (head == null) {
                    head = val;
                    cacheMap.put(pm, head);
                } else {
                    ListNode whileHead = head;
                    while (whileHead != null) {
                        if (whileHead.next == null) {
                            whileHead.next = val;//新数在链头,无所谓要不要 continue
                        }
                        whileHead = whileHead.next;
                    }
                }
            }
        }

        private void init() {
            if (!inited) {
                int minVal = 2;
                int maxVal = 1_000_000;
//                System.out.println(minVal + " >> " + maxVal);
//            范围内最大公约数列表
                FindPrimeNumbers findPrimeNumbers = new FindPrimeNumbers();
                int[] divisors = findPrimeNumbers.findPrimeNumbers_bit(maxVal);

                Map<Integer, FindPrimeNumbers.PNNode> primeNumbersHM = findPrimeNumbers.getPrimeNumbersHM();

                initCacheMap(divisors, primeNumbersHM);
            }
        }

        public int splitArray(int[] nums) {
            init();
            cacheMap.clear();
            for (int i = 0; i < nums.length; i++) {
                cacheMap(nums[i], i);
            }
            int result = 0;

            for (int i = 0; i < nums.length; i++) {
                int mcd = primeNumbersHM.get(nums[i]).max_common_divisors;

                //链表下标一定是递增的
                ListNode node = cacheMap.get(mcd);

                i = node.val;

                result++;
            }

            return result;
        }

        static class FindPrimeNumbers {
            //是否获取范围内每个数的最小公约数集，即产生Map<Integer, PNNode>
            private boolean fetchNumberPMHM = true;
            /**
             * key: 当前值
             * val：PNNode key 的所有最大公约数集
             */
            private Map<Integer, PNNode> map = null;

            /**
             * 查询范围内的所有质数集
             * findPrimeNumbers_bit(minVal, maxVal)： 获取范围内质数数组
             */
            public FindPrimeNumbers() {
            }

            //    使用 bit 位存数据值空间节省32倍，同一级别
            public int[] findPrimeNumbers_bit(int maxVal) {
                int minVal = 2;
                if (maxVal > (1 << 20)) {
                    throw new IllegalArgumentException("maxVal is too large(max=1048576): " + maxVal);
                }
                initMap(minVal, maxVal);
                int tmp = maxVal + 1;

//        按位存质数状态
                int lens = tmp / 8 + 1;
                byte[] arr = new byte[lens];

                int i;
                if (minVal <= 2) {
                    i = 3;
                    setPrimeNumber(arr, 2, 2, true);
                } else {
                    if (minVal % 2 == 1) {
                        i = minVal;
                    } else {
                        setPrimeNumber(arr, minVal, 2, true);
                        i = minVal + 1;
                    }
                }
                {//第一遍循环，对所有偶数设置2为最小公约数，对所有奇数不设置最小公约数
                    //将所有奇数存入数组
                    while (i < maxVal) {
                        setPrimeNumber(arr, i, 0, true);//奇数，待定设置最小公约数
                        if (fetchNumberPMHM) {
                            setPrimeNumber(arr, i + 1, 2, false);//偶数
                        }
                        i += 2;
                    }
                    if (i == maxVal) {
                        setPrimeNumber(arr, i, 0, true);//边界奇数，待定设置最小公约数
                    }
                }

                pm_multipleCheck(arr, maxVal);

                return getListPMFromArr_bits(minVal, maxVal, arr);
            }

            /**
             * 排除质数的 N 倍为质数
             * <p>
             * 将3开始的所有质数的倍数的最小公约质数添加此质数
             * <p>
             * 如：3、6、9 添加3
             * 如：5、10、15 添加5
             * 如：7，14，21 添加7
             *
             * @param arr
             * @param maxVal
             */
            private void pm_multipleCheck(byte[] arr, int maxVal) {
//        第一个质数开始
                int firstPM = 3;
//        倍数
                int multiple;
                while (firstPM <= maxVal) {
                    //下一个质数 如3 5 7 11...
                    multiple = fetchNumberPMHM ? 1 : 3;
                    while (firstPM * multiple <= maxVal) {
//                multiple=1表示当前数为质数
                        if (multiple == 1) {//质数本身
                            setPrimeNumber(arr, firstPM, firstPM, true);//质数
                        } else {
                            setPrimeNumber(arr, firstPM * multiple, firstPM, false);//质数的倍数
                        }
                        multiple += fetchNumberPMHM ? 1 : 2;
                    }

                    //奇数向后二步快步，找下一个范围内的质数
                    firstPM += 2;
//            2步一找的下一位是质数时，再执行外围的 while
                    while (!getBitState(arr, firstPM) && firstPM < maxVal) {
                        firstPM += 2;//两步一前进
                    }
                }
            }

            //        所有质数已经筛出，以下为瓶装成所需要的格式
            private int[] getListPMFromArr_bits(int minVal, int maxVal, byte[] arr) {
                int i;
                List<Integer> list = new ArrayList<>(16);
                if (minVal <= 2) {
                    i = 3;
                    list.add(2);
                } else {
                    if (minVal % 2 == 1) {
                        i = minVal;
                    } else {
                        i = minVal + 1;
                    }
                }
                while (i <= maxVal) {
                    if (!getBitState(arr, i)) {
                        i += 2;
                        continue;
                    }
                    list.add(i);
                    i += 2;
                }
//                System.out.println("bit primeNumber list lens: " + list.size());
                return list.stream().mapToInt(Integer::valueOf).toArray();
            }

            //取 index 位是否有1
            private boolean getBitState(byte[] arr, int index) {
                return (arr[index / 8] & (1 << (index % 8))) > 0;
            }

            /**
             * 设置 index 是可能质数或不是质数
             * 低位为小位，如：0000 00001 表示第0位为质数 0011 0100 表示第2，3，5位和为质数
             * <p>
             * 设置 index 对应二进制位设1或0，表示不是该数是不是质数
             *
             * @param arr
             * @param index bit位
             * @param maxPN 当前数的最大公约质数 ，0表示不设置公约数，未知最小公约数
             * @param flag  true: 设值(可能为质数） false: 删值(一定不为质数)
             */
            private void setPrimeNumber(byte[] arr, int index, int maxPN, boolean flag) {
                if (flag) {
                    //index 可能为质数，但一定是奇数
                    arr[index / 8] = (byte) (arr[index / 8] | (1 << (index % 8)));

                    if (fetchNumberPMHM) {
                        getPNNode(index).add(maxPN);
                    }
                } else {
                    //index 一定不为质数，但不一定是偶数
                    arr[index / 8] = (byte) (arr[index / 8] & (0xff ^ (1 << (index % 8))));

                    if (fetchNumberPMHM && maxPN > 0) {
                        getPNNode(index).add(maxPN);
//
                    }
                }
            }

            private FindPrimeNumbers.PNNode getPNNode(int index) {
                if (fetchNumberPMHM && map.get(index) == null) {
                    map.put(index, new FindPrimeNumbers.PNNode(index));
                }
                return map.get(index);
            }

            /**
             * 查询范围内的最小至最大值范围内的最小公约数集
             *
             * @return
             */
            public Map<Integer, FindPrimeNumbers.PNNode> getPrimeNumbersHM() {
                return map;
            }

            //必须确保新 map 必须为2^n 的容量，让每个数据不要重叠
            private void initMap(int minVal, int maxVal) {
                if (fetchNumberPMHM) {
                    int lens = 1;
                    if (minVal < maxVal) {
                        maxVal /= 2;
                        while (maxVal > 0) {
                            lens++;
                            maxVal /= 2;
                        }
                    }
                    map = new HashMap<>(1 << lens);
//                    System.out.println("map capacity: " + (1 << lens));
                }
            }

            /**
             * 数的最小公约质数器
             */
            public static class PNNode {
                public int val;
                //最大公约质数
                public int max_common_divisors;

                public PNNode(int val) {
                    this.val = val;
                }

                //每次添加的有效质数一定是更大的值，由算法决定
                public void add(int primeNumber) {
                    if (primeNumber == 0) {
                        return;
                    }
                    max_common_divisors = primeNumber;
                }
            }
        }
    }
}
