package com.sample.sort;

import java.util.Arrays;

/**
 * Created by jiek on 2020/8/17.
 * <p>
 * 快速排序
 * <p>
 * 【不稳定排序】
 */
public class QuickSort<T extends Comparable> extends AbsSort<T> {
    @Override
    public T[] sort(T[] list, boolean type) {
        return quickSort(list, 0, list.length - 1, type);
    }

    T[] quickSort(T[] list, int left, int right, boolean type) {
        if (left < right) {
            int pivot = partition(list, left, right, type);
            quickSort(list, left, pivot - 1, type);
            quickSort(list, pivot + 1, right, type);
        }
        return list;
    }

    int partition(T[] list, int left, int right, boolean type) {
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (needSwap(list[pivot], list[i], type)) {
                swap(list, i, index);
                index++;
            }
        }
        index--;
        swap(list, pivot, index);
//        System.out.println(left + "," + right + "    <- " + index + Arrays.toString(list));
        return index;
    }
}
