package com.sample.jiek.sorting;

/**
 * Created by jiek on 18/06/2017.
 * 选择排序
 * @See https://en.wikipedia.org/wiki/Selection_sort
 */
public class SelectionSort extends SortableSwap {
    @Override
    public void sort(int[] list) {
        for(int i = 0; i<list.length-1; i++){
            int min = i;
            //find min
            for(int j = i+1; j<list.length ;j++){
                if(list[j] <list[min]){
                    min = j;
                }
            }
            //swap the min with the ith element
            swap(list, min, i);
        }
    }
}
