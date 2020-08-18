package com.sample.sort;

import com.sample.util.MockArrayUtil;
import com.sample.util.Util;

/**
 * Created by jiek on 2020/8/17.
 * <p>
 * 快速排序
 * <p>
 * 【不稳定排序】
 */
public class QuickSort<T extends Comparable> extends AbsSort<T> {

    boolean dualQS = true;

    /**
     * 单向或双向快排
     *
     * @param dualQS
     */
    public QuickSort(boolean dualQS) {
        this.dualQS = dualQS;
    }

    public QuickSort() {
    }

    public static void main(String[] args) {
        Integer[] array =
                {9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 1};

        for (int i = 0; i < 1_0; i++) {//循环 n次，看算法性能
            array = Util.getArrayBoxed(MockArrayUtil.mock(1 << 10, 0, 1));//模拟产生 Integer[] 数据
            {
                QuickSort<Comparable> sort = new QuickSort<>(true);//true 双向，false 单向

                boolean sortType = false;//true 顺序 false 倒序

                long time = System.currentTimeMillis();
                sort.quickSort(array, 0, array.length - 1, sortType);
                System.out.println(" \t\t\t\t倒 time : " + (System.currentTimeMillis() - time));
                sort.check(array, sortType);
            }
        }
    }


    @Override
    public T[] sort(T[] array, boolean type) {
        return quickSort(array, 0, array.length - 1, type);
    }

    T[] quickSort(T[] array, int left, int right, boolean type) {
        if (left < right) {
            int pivot = partition(array, left, right, type);
            quickSort(array, left, pivot - 1, type);
            quickSort(array, pivot + 1, right, type);
        }
        return array;
    }

    int partition(T[] array, int left, int right, boolean type) {
        if (dualQS) {
            return partition_lr(array, left, right, type);//双向快排
        } else {
            return partition_left(array, left, right, type);//单向快排
        }
    }

    int partition_lr(T[] array, int left, int right, boolean type) {
        assert left <= right;
        int pivot = left;
        left++;

        while (left < right) {
            if (type ?
                    array[left].compareTo(array[pivot]) <= 0 :
                    array[left].compareTo(array[pivot]) >= 0
                    ) {
                left++;
                continue;
            }
            while (needSwap(array[right], array[pivot], type)) {//此处是跑出 l > r的场景，因为上一句跑出 list[l] > list[pivot]
                right--;
            }
            if (left < right) {
                swap(array, left, right);
                right--;
            }
            if (left >= right) {
                left--;
                continue;
            }
            left++;
        }

        assert left == right;
        if (needSwap(array[left], array[pivot], type)) {
            left--;
        }
        swap(array, left, pivot);
        return left;
    }

    /**
     * 单向快排，从左至右
     *
     * @param array
     * @param left
     * @param right
     * @param type
     * @return
     */
    int partition_left(T[] array, int left, int right, boolean type) {
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (needSwap(array[pivot], array[i], type)) {
                swap(array, i, index);
                index++;
            }
        }
        index--;
        swap(array, pivot, index);
        return index;
    }
}
