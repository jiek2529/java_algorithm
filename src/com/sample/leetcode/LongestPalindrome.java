package com.sample.leetcode;

/**
 * Created by jiek on 2020/8/21.
 * <p>
 * 查询最长回文字符串
 * 回文要求：最少三个字符构成；回文首尾字符去掉后，任然是回文（1、2个字符除外）。
 * <p>
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 * <p>
 * 示例 1：
 * <p>
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * <p>
 * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 */
public class LongestPalindrome {

    public static void main(String[] args) {
        solve("babad");
        solve("1234567890987654321");
        solve("01221");
        solve("01221");
        solve("0121");
        solve("12101");
        solve("121");
        solve("12");
        solve("1234");
        solve("122");
    }

    static String solve(String literal) {
        System.out.println("\noriginal literal: " + literal);

        String result = new Solution().longestPalindrome(literal);

        System.out.println("longestPalindrome result: " + result);
        return result;
    }

    static class Solution {
        public String longestPalindrome(String literal) {
//            使用动态规划去实现

//          未找到时的结果，缺省值
            String defaultVal = "";
//            回文最小长度约束
            int shortestPalindromeLens = 2;
//            限定检索方向
            boolean AESC = true;//true:从前向后找，false:从后向前找

//            字符总长
            int literalLens = literal.length();
//            去检测最长回文长度
            int longestLens = literalLens;


            if (longestLens < shortestPalindromeLens) return defaultVal;
            while (longestLens >= shortestPalindromeLens) {
                //改变此条件或按从前到后或从后到前的查找方式
                for (int i = 0; i < literalLens - longestLens + 1; i++) {
                    if (AESC) {//顺序找
//                        i 至 （i + longestLens - 1）位为共 longestLens 个字符
                        if (isPalindrome(literal, i, i + longestLens - 1)) {
//                            截取子串时 i ， i+longestLens 为 longestLens 个字符
                            return literal.substring(i, i + longestLens);
                        }
                    } else {//倒序找
                        if (isPalindrome(literal, literalLens - longestLens - i, literalLens - i - 1)) {
                            return literal.substring(literalLens - longestLens - i, literalLens - i);
                        }
                    }
                }
                longestLens--;
            }
            return defaultVal;
        }

        /**
         * @param literal 原串
         * @param from    起始 charAt 的下标
         * @param to      结束 charAt 的下标
         *
         * @return 此条件下是否是回文
         */
        boolean isPalindrome(String literal, int from, int to) {
            if (literal.charAt(from) == literal.charAt(to)) {
//                最后一次有效比较为相邻数或只剩一个字符时，比较结束，回文判断成功。
                if (to - from < 1) {
                    return true;
                }
                return isPalindrome(literal, from + 1, to - 1);
            }
            return false;
        }
    }
}
