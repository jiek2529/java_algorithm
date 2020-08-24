package com.sample.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by jiek on 2020/8/24.
 * <p>
 * 5497. 查找大小为 M 的最新分组
 * <p>
 * 给你一个数组 arr ，该数组表示一个从 1 到 n 的数字排列。有一个长度为 n 的二进制字符串，该字符串上的所有位最初都设置为 0 。
 * <p>
 * 在从 1 到 n 的每个步骤 i 中（假设二进制字符串和 arr 都是从 1 开始索引的情况下），二进制字符串上位于位置 arr[i] 的位将会设为 1 。
 * <p>
 * 给你一个整数 m ，请你找出二进制字符串上存在长度为 m 的一组 1 的最后步骤。一组 1 是一个连续的、由 1 组成的子串，且左右两边不再有可以延伸的 1 。
 * <p>
 * 返回存在长度 恰好 为 m 的 一组 1  的最后步骤。如果不存在这样的步骤，请返回 -1 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：arr = [3,5,1,2,4], m = 1
 * 输出：4
 * 解释：
 * 步骤 1："00100"，由 1 构成的组：["1"]
 * 步骤 2："00101"，由 1 构成的组：["1", "1"]
 * 步骤 3："10101"，由 1 构成的组：["1", "1", "1"]
 * 步骤 4："11101"，由 1 构成的组：["111", "1"]
 * 步骤 5："11111"，由 1 构成的组：["11111"]
 * 存在长度为 1 的一组 1 的最后步骤是步骤 4 。
 * 示例 2：
 * <p>
 * 输入：arr = [3,1,5,4,2], m = 2
 * 输出：-1
 * 解释：
 * 步骤 1："00100"，由 1 构成的组：["1"]
 * 步骤 2："10100"，由 1 构成的组：["1", "1"]
 * 步骤 3："10101"，由 1 构成的组：["1", "1", "1"]
 * 步骤 4："10111"，由 1 构成的组：["1", "111"]
 * 步骤 5："11111"，由 1 构成的组：["11111"]
 * 不管是哪一步骤都无法形成长度为 2 的一组 1 。
 * 示例 3：
 * <p>
 * 输入：arr = [1], m = 1
 * 输出：1
 * 示例 4：
 * <p>
 * 输入：arr = [2,1], m = 2
 * 输出：2
 * <p>
 * 链接：https://leetcode-cn.com/problems/find-latest-group-of-size-m
 */
public class FindLatestStep {
    public static void main(String[] args) {
        solve(new int[]{3, 5, 1, 2, 4}, 1, 4);
        solve(new int[]{3, 5, 1, 2, 4}, 0, -1);
        solve(new int[]{3, 5, 1, 2, 4}, 5, 5);
        solve(new int[]{3, 5, 1, 2, 4}, 2, -1);
        solve(new int[]{2, 5, 1, 3, 4}, 2, 3);
    }

    private static void solve(int[] arr, int m, int expect) {
        int result = new Solution().findLatestStep(arr, m);
        System.out.println(result == expect ? "正确" : "错误");
    }

    static class Solution {

        public int findLatestStep(int[] arr, int m) {
            return solve_3(arr, m);//增加新数组维护连续串的起始下标，免排序算法
//            return solve_2(arr, m);//使用 TreeSet，简单加性能好
//            return solve_1(arr, m);//循环加遍历实现效果，但性能最差
        }

        /**
         * 方案三：
         * 使用数组维护连续串的起始点下标，链头存链尾下标
         * <p>
         * 自身维护链表长度，未用到排序队列，所以性能优于【方案二】
         * <p>
         * 时：O(n) 空：O(n)
         *
         * @param arr
         * @param m
         * @return
         */
        private int solve_3(int[] arr, int m) {
            //满足条件的最后一步步数
            int result = -1;
            int arrLens = arr.length;
            int[] link = new int[arrLens + 1];//为匹配自然数 1开始
            //满足条件的个数
            int count = 0;
            int pos;
            //连续串的首下标、连续串的尾下标
            int lPos, rPos;
            for (int i = 0; i < arrLens; i++) {
                pos = arr[i];
                link[pos] = pos;

                lPos = pos;
                rPos = pos;
                //当前位置的前一位链头下标
                if (link[pos - 1] > 0) {
                    //（前一位下标）减去（前链链头下标） 加1 等于 连续串长时，链长自己当前位置而减少一次满足条件
                    //[当前下标]减去[前一位链头下标] 等于 前一位前方连续串的长度
                    if (pos - link[pos - 1] == m) {
                        count--;
                    }
                    //记录延长串后的左起下标
                    lPos = link[pos - 1];
                }
                //右不越界时，link链长比 arr 多一位，所以条件加 <=
                if (pos < arrLens && link[pos + 1] > 0) {
                    //（（后链链尾下标）减去 后一位下标）加1 等于 连续串长时，链长自己当前位置而减少一次满足条件
                    //[后一位链尾下标]减去[当前下标] 等于 后一位后方连续串的长度
                    if (link[pos + 1] - pos == m) {
                        count--;
                    }
                    //记录延长串后的右尾下标
                    rPos = link[pos + 1];
                }

                if (lPos < rPos) {
//                    修改延长后的新链头尾的下标
                    link[lPos] = rPos;
                    link[rPos] = lPos;
                }

//                    如果新链满足链长，记数加一
                if (rPos - lPos + 1 == m) {
                    count++;
                }

//                每一步完成时，记录满足条件的最后一次的步数
                if (count > 0) {
                    result = i + 1;//循环从0开始，记步从 1开始，所以加 1
//                    System.out.println("result: " + result);
                }
            }
            return result;
        }

        /**
         * 方案二：
         * 使用 TreeSet 的自然有序性，对数据从后向前找，
         * 当两数相差为 lower-a-1 == m 或high-a-1 == m时，表示当前值被添加进来时，就是最后一个满足条件的位置
         * <p>
         * 时：O(n) 空：O(n)
         *
         * @param arr
         * @param m
         * @return
         */
        private int solve_2(int[] arr, int m) {
            if (arr.length == m) return arr.length;
            if (m <= 0) return -1;

            //有需队列，从后向前找，当前一值与自己相差正好为 m-1
            TreeSet<Integer> set = new TreeSet<>();
            set.add(0);
            set.add(arr.length + 1);
            int length = arr.length;
            for (int i = length - 1; i >= 0; i--) {
                int iNum = arr[i];
                int lower = set.lower(iNum);
                int high = set.higher(iNum);
//                向前搜索最后一个数
                if (iNum - lower - 1 == m || high - iNum - 1 == m) {
                    return i;
                }
                set.add(iNum);
            }
            return -1;
        }

        /**
         * 方案一：
         * <p>
         * 执行每步结果，并循环找是否满足条件，
         * <p>
         * 时：O(n^2) 空：O(n)
         *
         * @param arr
         * @param m
         * @return
         */
        private int solve_1(int[] arr, int m) {
            int length = arr.length;
            int[] tmp = new int[length];
            int res = -1;
            int index = 0;
            while (index < length) {
                tmp[arr[index] - 1] = 1;
                if (judge(tmp, m)) {
                    res = index + 1;
                }
                index++;
            }
            return res;
        }

        /**
         * 遍历查是否满足条件
         *
         * @param strArr
         * @param m
         * @return
         */
        private boolean judge(int[] strArr, int m) {
            int index = 0;
            List list = new ArrayList<>();
            int con = 0;
            int length = strArr.length;
            while (index < length) {
                if (strArr[index] == 1) {
                    con++;
                    index++;
//                    遍历到最后了，需要判断是否满足条件
                    if (index == length) {
                        list.add(con);
                    }
                    continue;
                }
                if (strArr[index] == 0 && con > 0) {
                    list.add(con);
                    con = 0;
                }
                index++;
            }
            return list.contains(m);
        }
    }
}
