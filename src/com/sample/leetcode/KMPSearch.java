package com.sample.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jiek on 2020/8/24.
 * <p>
 * 单趟查询之字符串匹配查询 KMP 算法
 */
public class KMPSearch {
    //    最大前后缀匹配表
    int[] kmpTable;
    //    最后一次的匹配串
    private String lastWord;

    public static void main(String[] args) {
        solve("ab", "");
        solve("ab", null);
        solve("ab", "abcdabd");
        solve("abcdabcdabde", "abcdabd");
        solve("abc abcdab abcdabcdabde", "abcdabd");
        solve("123123142312356", "123");
        solve("0123123142312356", "123");

        solveAll("12", "123");
        solveAll("1231", "123");
        solveAll("123123142312356", "123");
        solveAll("0123123142312356", "123");
        solveAll("0123456123", "123");
    }

    private static int solve(String str, String word) {
        int result = new KMPSearch().search(str, word);//, 0);
        System.out.println("str: " + str);
        System.out.println("march position: " + result + "\n");
        return result;
    }

    private static int[] solveAll(String str, String word) {
        int[] result = new KMPSearch().searchAll(str, word);
        System.out.println("str: " + str);
        System.out.println("march position: " + Arrays.toString(result) + "\n");
        return result;
    }

    public int[] searchAll(String str, String word) {
        return searchAll(str, word, 0);
    }

    public int[] searchAll(String str, String word, int startIndex) {
        List<Integer> list = new ArrayList<>();
        int result = startIndex;
        while (result < str.length() - word.length()) {
            result = search(str, word, result);
            if (result >= 0) {
                list.add(result);
                result++;
            } else {
                break;//排除最后一趟没匹配到，结束
            }
        }
        int[] matchPos = list.stream().mapToInt(Integer::valueOf).toArray();
        return matchPos;
    }

    /**
     * kmp 搜索
     *
     * @param str  原串
     * @param word 匹配串
     * @return -1：未匹配上； 其它都是匹配的首下标位置
     */
    public int search(String str, String word) {
        return search(str, word, 0);
    }

    /**
     * kmp 搜索
     *
     * @param str        原串
     * @param word       匹配串
     * @param startIndex 下标开始位置
     * @return -1：未匹配上； 其它都是匹配的首下标位置
     */
    public int search(String str, String word, int startIndex) {
        if (str == null || str.length() - startIndex == 0) {
            return -1;
        }
        int[] kmpTable = kmpTable(word);
        if (kmpTable == null) {
            return -1;
        }

        int i = startIndex, index = 0;
        int wordLens = word.length();
        while (i < str.length()) {
            //与 word 的第 index 个匹配
            if (str.charAt(i) == word.charAt(index)) {
                index++;
                i++;
//            匹配到结束
                if (index == wordLens) {
                    return i - index;
                }
                continue;
            }
//            未匹配到，继续后移
            if (kmpTable[index] == -1) {
                i++;
                index = 0;
            } else {
                index = kmpTable[index];
            }
        }

        return -1;
    }

    /**
     * 最大前后缀匹配表
     * 如："abcdabd" -> [-1,0,0,0,0,1,2]
     *
     * @param word 匹配串
     * @return
     */
    private int[] kmpTable(String word) {
        if (word == null) {
            return null;
        }
        int length = word.length();
        if (length == 0) {
            return null;
        }
        //重复利用最大前后缀匹配表
        if (word.equals(lastWord)) {
            return kmpTable;
        }
        lastWord = word;

        kmpTable = new int[length];
//        {
//            int preIndex = 0;
//            for (int i = 1; i < length; i++) {
//                if (word.charAt(i) == word.charAt(preIndex)) {
//                    preIndex++;
//                    kmpTable[i] = preIndex;
//                } else {
//                    preIndex = 0;
//                }
//            }
//        }
        {
            //前缀匹配个数
            int preMatch = 0;
            kmpTable[0] = -1;
//            将当前匹配前缀数设入下一组中
            for (int i = 1; i < length - 1; i++) {
                if (word.charAt(i) == word.charAt(preMatch)) {
                    preMatch++;
                    kmpTable[i + 1] = preMatch;
                } else {
                    preMatch = 0;
                }
            }

            if (length > 1) {
//            边界单设
                kmpTable[length - 1] = preMatch;
            }
        }
        System.out.println("\t\tKMPTable: " + word + "-> " + Arrays.toString(kmpTable));
        return kmpTable;
    }
}
