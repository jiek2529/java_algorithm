package com.sample.sort;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by jiek on 2020/8/16.
 * <p>
 * 基数排序，按位排序
 * <p>
 * 【稳定算法】
 * 时间复杂度：O(n * k) 空间复杂度：O(n)
 */
public class RadixSort<T extends Comparable> extends AbsSort<T> {
    public static void main(String[] args) {
        Integer[] list = {1, 4, 6, 2, 101, 355, 1024, 74, 3};

        new RadixSort().sort(list, true);
        System.out.println(Arrays.toString(list));
    }

    public T[] sort(T[] numbers, boolean type) {//radix表示最大的数有多少位
        if (SortBean.class.isAssignableFrom(numbers.getClass()) ||
                Integer.class.isAssignableFrom(numbers.getClass())) {
            throw new Error("仅支持数值对象的遍历");
        }
        return sort(numbers, true, 4);
    }

    public T[] sort(T[] list, boolean type, int radix) //radix表示最大的数有多少位
    {
        int k = type ? 0 : list.length;
        int n = 1;
        int m = 1; //控制键值排序依据在哪一位
        T[][] temp = (T[][]) Array.newInstance(list[0].getClass(), 10, list.length);
        //数组的第一维表示可能的余数0-9
        int[] order = new int[10]; //数组order[i]用来表示该位是i的数的个数

        while (m <= radix) {
            for (int i = 0; i < list.length; i++) {
                int lsd = (((Integer) list[i] / n) % 10);
                temp[lsd][order[lsd]] = list[i];
                order[lsd]++;
            }
            for (int i = 0; i < 10; i++) {
                if (order[i] != 0) {
                    for (int j = 0; j < order[i]; j++) {
                        if (type) {
                            list[k] = temp[i][j];
                            k++;
                        } else {
                            list[k] = temp[i][j];
                            k--;
                        }
                    }
                }
                order[i] = 0;
            }
            n *= 10;
            k = 0;
            m++;
//            System.out.println(Arrays.toString(list));
        }
        return list;
    }
}
