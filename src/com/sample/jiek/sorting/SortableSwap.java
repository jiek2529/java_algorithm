package com.sample.jiek.sorting;

import java.util.Arrays;

/**
 * Created by jiek on 11/06/2017.
 */
public abstract class SortableSwap implements Sortable {

    /**
     * Swaps x[a] with x[b].
     */
    protected void swap(int[] x, int a, int b) {
        if (a != b) {
            int t = x[a];
            x[a] = x[b];
            x[b] = t;
        }
        log(x, a, b);
    }


    /**
     * 调试println，把交换时的位置打印出来
     *
     * @param list
     * @param left
     * @param right
     */
    @Deprecated
    private void log(int[] list, int left, int right) {
        if (list.length < 33) {
            System.out.println(Arrays.toString(list) + " swap " + left + " <--> " + right);
        }
    }
}
