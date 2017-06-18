package com.sample.jiek.sorting;

/**
 * Created by jiek on 11/06/2017.
 * 快速排序算法
 * <p>
 * 但是Array.sort 还是比这个算法快，以1M数进行排序对比，
 * <p>
 * initial list capacity：1048576 ,and mix times ：2
 * 12 <------ initList used time
 * 335 <------ mix 2 times used time
 * <p>
 * Sort with [ com.sample.jiek.sorting.QuickSort ] for 440 milliseconds
 * Sort with [ com.sample.jiek.sorting.MergeSort ] for 160 milliseconds
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
            log("fibonacci : 左 >> " + left + " : " + (index - 1));
            quickSort(list, left, index - 1);
        }
        if (index < right) {
            log("fibonacci : 右 >> " + index + " : " + right);
            quickSort(list, index, right);
        }
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
        int pivot = list[(left + right) / 2];//取折中点的值
        log("partition:  left=" + left + "  right=" + right + "  pivot=" + pivot);

        while (i <= j) {//如果是倒序，就是把以下两while条件判断修改
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
