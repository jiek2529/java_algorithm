package com.sample.leetcode;

/**
 * Created by jiek on 2020/8/21.
 * <p>
 * 求无重复字符的最长子串长度
 * <p>
 * 用char值做位的状态 assic字符出现与没出现过状态
 * 使用byte[]+位，char是16bit，所以byte 需要使用 1<<(16-3)个空间，但如果是 assic编码就使用 1<< (8-3)就够了，非常节约空间。
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public static void main(String[] args) {
        solve("");
        solve("a");
        solve("ab");
        solve("aba");
        solve("aba");
        solve("abcdefa");
        solve("ababa");
        solve("abcbe");
        solve("中文");
        solve("中\uD83C\uDF38文");
    }

    static void solve(String literal) {
        System.out.println("\noriginal literal: " + literal);

        int result = new Solution().lengthOfLongestSubstring(literal);

        System.out.println("longestPalindrome result: " + result);
    }

    static class Solution {
        public int lengthOfLongestSubstring(String literal) {
            int maxLens = 0;
            if (literal.length() == 0) {
                return 0;
            }
            int index = 0;
            while (index < literal.length() - maxLens) {
                int temp = findLengthOfLongestSubstring(literal, index);
                if (maxLens < temp) {
                    maxLens = temp;
                }
                index++;
            }
            return maxLens;
        }

        private int findLengthOfLongestSubstring(String literal, int index) {
            //申请64K位空间,来做状态位对比
            byte[] bs = new byte[1 << 13];//如果值为 char，则此空间要申请 1<< 13
            int curr = index;
            while (curr < literal.length()) {
                char c = literal.charAt(curr);
                char pos = (char) (c / 8);
                byte remainder = (byte) (c % 8);
                if ((bs[pos] & (1 << remainder)) > 0) {
                    break;
                }
                bs[pos] = (byte) (bs[pos] | (1 << remainder));
                curr++;
            }
            return curr - index;
        }
    }
}
