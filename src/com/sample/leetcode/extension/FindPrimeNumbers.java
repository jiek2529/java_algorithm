package com.sample.leetcode.extension;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiek on 2020/8/22.
 *
 * 筛法求素数
 */
public class FindPrimeNumbers {
    public static int[] findPrimeNumbers(int minVal, int maxVal) {
        int lens = 1, tmp = maxVal + 1;
        while ((tmp >> 1) > 0) {
            tmp = tmp >> 1;
            lens++;
        }
        int[] arr = new int[1 << lens];

        int i = 3;
        if (minVal <= 2) {
            i = 3;
            arr[2] = 2;
        } else {
            if (minVal % 2 == 1) {
                i = minVal;
            } else {
                i = minVal + 1;
            }
        }
        while (i <= maxVal) {
            arr[i] = i;
            i += 2;
        }

        i = 3;
        int index = 3;
        while (i <= maxVal) {
            index = 3;
            while (i * index < maxVal) {
                arr[i * index] = 0;
                index += 2;
            }
            i += 2;
            while (arr[i] == 0 && i < maxVal) {
                i += 2;//两步一前进
            }
        }

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
        while (i < maxVal) {
            if (arr[i] == 0) {
                i += 2;
                continue;
            }
            list.add(arr[i]);
            i += 2;
        }
        System.out.println(list.size());
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }
}
