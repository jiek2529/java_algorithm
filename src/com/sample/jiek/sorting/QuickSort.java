package com.sample.jiek.sorting;

/**
 * Created by jiek on 11/06/2017.
 * 快速排序算法，
 */
public class QuickSort extends SortableSwap {
    @Override
    public void sort(int[] list) {
        if (list != null && list.length > 1) {
            quickSort(list, 0, list.length - 1);
        }
    }

    private void quickSort(int list[], int left, int right) {
        int index = partition(list, left, right);
        if (left < index - 1) {
            log("fibonacci : 左");
            quickSort(list, left, index - 1);
        }
        if (index < right) {
            log("fibonacci : 右");
            quickSort(list, index, right);
        }
    }

    private void log(String s) {
//        System.out.println(s);
    }

    /**
     * 分区折中交换
     *
     * @param list  int数组
     * @param left
     * @param right
     * @return
     */
    private int partition(int list[], int left, int right) {
        int i = left, j = right;
        int temp;
        int pivot = list[(left + right) / 2];//取折中点的值
        log("partition:  left=" + left + "  right=" + right + "  pivot=" + pivot);

        while (i <= j) {
            while (list[i] < pivot)
                i++;
            while (list[j] > pivot)
                j--;
            if (i <= j) {
                swap(list, i, j);
                i++;
                j--;
            }
        }
        return i;
    }

}
