package com.sample.jiek.sorting;

import java.util.Arrays;

/**
 * Created by jiek on 13/06/2017.
 * merge sorting algorithm
 *
 *
 */
public class MergeSort extends SortableSwap {
    private void merge(int[] result, int[] left, int[] right) {
        int result_index = 0, left_index = 0, right_index = 0;
        while (left_index < left.length && right_index < right.length) {
            if (left[left_index] < right[right_index]) {
                result[result_index] = left[left_index];
                result_index++;
                left_index++;
            } else {
                result[result_index] = right[right_index];
                result_index++;
                right_index++;
            }
        }

        while (right_index < right.length) {//如果右边剩下合并右边的
            result[result_index] = right[right_index];
            right_index++;
            result_index++;
        }

        while (left_index < left.length) {
            result[result_index] = left[left_index];
            left_index++;
            result_index++;
        }
    }

    @Override
    public void sort(int[] list) {
        int length = list.length;
        int pivot = length / 2;
        if (length > 1) {
            int[] left = Arrays.copyOfRange(list, 0, pivot);//copy the left half from the list
            int[] right = Arrays.copyOfRange(list, pivot, length);//copy the right half from the list
            sort(left);//recursion sort the left half
            sort(right);//recursion sort the right half
            merge(list, left, right);//merge to list frome left and right
        }
    }

}
