package com.sample.leetcode.extension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jiek on 2020/8/25.
 * <p>
 * 动态规划示例
 */
public class DP {
    int count = 0;

    public static void main(String[] args) {
//        1. 求数组中不相邻数和的最大值 递归与非递归 DP
//        solve_opt(new int[]{1, 2, 4, 1, 7, 8, 3});
//        solve_opt(new int[]{4, 1, 1, 9, 1});

//        2. 求一堆数中和为目标数的可能性
        /*for (int i = 0; i < 15; i++) {
            solve_subset(new int[]{3, 34, 4, 12, 5, 2}, i);
//            solve_subset(new int[]{2, 3, 1, 5, 7, 11, 13}, i);
        }*/

//        3. 求多任务时，赚钱最多钱
        new WorkMaxValue().solve();
    }

    private static void solve_subset(int[] arr, int n) {
        System.out.println("\t递归 和为：" + n + " -> " + new DP().sec_subset(arr, arr.length - 1, n));
        System.out.println("动态规划 和为：" + n + " -> " + new DP().dp_subset(arr, n));
    }

    private static void solve_opt(int[] arr) {
        boolean recursive = false;//是否用递归方式，否则为缓存查找方式
        if (recursive) {
            System.out.println("\t递归： " + new DP().sec_opt(arr, arr.length - 1));
        } else {
            System.out.println("\t 非递归" + new DP().dp_opt(arr));
        }
    }

    /**
     * 缓存方式（非递归方式），动态规划不相邻数最大值
     *
     * @param arr
     * @return
     */
    private int dp_opt(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[0] = arr[0];
        dp[1] = Math.max(arr[0], arr[1]);

        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 2] + arr[i], dp[i - 1]);
        }
        //返回最后一个数
        return dp[n - 1];
    }

    /**
     * 递归方式
     * 求不相邻数的和最大值
     *
     * @param arr
     * @param n   最后一位的下标（length-1）
     */
    int sec_opt(int[] arr, int n) {
//        System.out.println(++count + " n = " + n);//日志递归的次数
        if (n == 0) return arr[0];
        else if (n == 1) return Math.max(arr[0], arr[1]);
        return Math.max(sec_opt(arr, n - 2) + arr[n], sec_opt(arr, n - 1));
    }


    /**
     * 递归方式，在数组中，找出和为目标数的可能性
     *
     * @param arr
     * @param i   当前下标
     * @param s   剩余数量
     * @return true 已经匹配到 false 未匹配到
     */
    boolean sec_subset(int[] arr, int i, int s) {
        if (s == 0) {
            return true;
        } else if (i == 0) {
            return arr[i] == s;
        } else if (arr[i] > s) {
            return sec_subset(arr, i - 1, s);
        }
        return sec_subset(arr, i - 1, s)//不选当前值，取前一个的值
                || sec_subset(arr, i - 1, s - arr[i]);//选了当前值 + 向前遍历（s-当前值）
    }

    /**
     * DP 动态规划方式 在数组中，找出和为目标数的可能性
     *
     * @param arr 原数组
     * @param s   剩余总数
     * @return 是否能凑出目标数
     */
    boolean dp_subset(int[] arr, int s) {
//        调试打印日志
        boolean isDebug = false;

        int lens = arr.length;
        boolean[][] subset = new boolean[lens][s + 1];
//        防止越界，第一个数超出范围
        if (arr[0] < s + 1) {
            subset[0][arr[0]] = true;
        }
        //此处必须设 true
        subset[0][0] = true;

        if (isDebug) {
            System.out.println("");
            System.out.print("arr[ 0 ][ 0 - " + (lens - 1) + " ] : " + subset[0][0]);
            for (int i = 1; i < s + 1; i++) {
                System.out.print(" , " + subset[0][i]);
            }
        }
        //遍历数组数
        for (int i = 1; i < lens; i++) {
            subset[i][0] = true;
            if (isDebug) {
                System.out.println("");
                System.out.print("arr[ " + i + " ][ 0 - " + (lens - 1) + " ] : " + subset[i][0]);
            }
            //遍历剩余量
            for (int j = 1; j < s + 1; j++) {
                if (arr[i] > j) {//大于余额，不选
                    subset[i][j] = subset[i - 1][j];
                } else {//不大于余额，选用
                    subset[i][j] = subset[i - 1][j - arr[i]] || subset[i - 1][j];
                }
                if (isDebug) {
                    System.out.print(" , " + subset[i][j]);
                }
            }
        }
        return subset[lens - 1][s];
    }

    /**
     * n个任务可选，
     * 每个任务从 a时至 b时结束，能赚 c 收益，
     * 求时间不重叠的最大收益
     */
    private static class WorkMaxValue {

        public void solve() {
            int vals = earn(new int[][]{
                    {0, 0, 0},//增加占位 0，0，0
                    {1, 4, 5},
                    {3, 5, 1},
                    {0, 6, 8},
                    {4, 7, 4},
                    {3, 8, 6},
                    {5, 9, 3},
                    {6, 10, 2},
                    {8, 11, 4}
            });

            System.out.println("最多赚： " + vals);
        }

        private int earn(int[][] arr) {
//            先将所有任务按结束时间顺序排序。后一任务找前一最近可执行任务方便；
            List<Job> tasks = new ArrayList<>();
            fillSet(tasks, arr);//填充为有序列表

            System.out.println("任务按结束时间排序");
            for (Job task : tasks) {
                System.out.println(task);
            }

            //总任务数
            int n = tasks.size();
            if (n < 1) return 0;
            if (n == 1) {
                return tasks.get(1).val;//只有一个任务时，返回第一个任务的收益
            }

            int i, j;

            //对前区可选任务序号
            int[] prev = new int[n]; //表示非要选i这个临时工作的话，还能选的前面最接近时间的临时工作是什么
            for (i = 2; i < n; i++) {//从第二个开始是因为第0个是用0,0,0来占位的，第一个没有前面的工作所以不能比较
                for (j = i - 1; j >= 1; j--) {
//                    任务时间无冲突时，前一任务为可做任务
                    if (tasks.get(i).start == tasks.get(j).end) {
                        prev[i] = j;
                        break;
                    }
                }
            }
//            做到i号个任务能赚到的最多收益
            int opt[] = new int[n];
            opt[0] = 0;
            for (i = 1; i < n; i++) {
                if (prev[i] == 0) {
                    opt[i] = Math.max(tasks.get(i).val, opt[i - 1]);
                } else {
                    opt[i] = Math.max(tasks.get(i).val + opt[prev[i]], opt[i - 1]);
                }
            }
            for (i = 1; i < n; i++) {
                System.out.println("前 " + i + "个任务，最多赚 " + opt[i]);
            }
            return opt[n - 1];
        }

        private void fillSet(List<Job> tasks, int[][] task) {
            for (int i = 0; i < task.length; i++) {
                tasks.add(new Job(task[i][0], task[i][1], task[i][2]));
            }
            tasks.sort(new Comparator<Job>() {
                @Override
                public int compare(Job o1, Job o2) {
                    return o1.end - o2.end;
                }
            });
        }

        private class Job {
            int start;
            int end;
            int val;

            public Job(int start, int end, int val) {
                this.start = start;
                this.end = end;
                this.val = val;
            }

            @Override
            public String toString() {
                return "Job{" +
                        "start=" + start +
                        ", end=" + end +
                        ", val=" + val +
                        '}';
            }
        }
    }
}
