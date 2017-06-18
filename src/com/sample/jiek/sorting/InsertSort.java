package com.sample.jiek.sorting;

/**
 * Created by jiek on 18/06/2017.
 * 插入排序算法,同冒泡算法很相似
 * 把新加一个数来进行沉底排序，所以效率比冒泡快
 *
 * @See https://en.wikipedia.org/wiki/Insertion_sort
 */
public class InsertSort extends SortableSwap {
    @Override
    public void sort(int[] list) {
        if (list != null && list.length > 1) {
            for (int i = 1; i < list.length; i++) {
                for (int j = i; j > 0; j--) {
                    if (list[j] < list[j - 1]) {
                        swap(list, j, j - 1);
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
