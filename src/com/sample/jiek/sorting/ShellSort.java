package com.sample.jiek.sorting;

/**
 * Created by jiek on 18/06/2017.
 * @See https://en.wikipedia.org/wiki/Shellsort
 * 希尔排序是按照不同步长对元素进行插入排序
 */
public class ShellSort extends SortableSwap {
    @Override
    public void sort(int[] list) {
        //希尔排序
        int d = list.length;
        while (true) {
            d = d / 2;
            for (int x = 0; x < d; x++) {
                for (int i = x + d; i < list.length; i = i + d) {
                    int temp = list[i];
                    int j;
                    for (j = i - d; j >= 0 && list[j] > temp; j = j - d) {
                        list[j + d] = list[j];
                    }
                    list[j + d] = temp;
                    log("swap(" + i + "," + (j + d) + ")");
                }
            }
            if (d == 1) {
                break;
            }
        }
    }
}
