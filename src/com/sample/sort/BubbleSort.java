package com.sample.sort;

/**
 * Created by jiek on 2020/8/17.
 * <p>
 * 冒泡排序
 * <p>
 * 大值或小值向后端有序区头部靠齐
 * <p>
 * <无序区，有序区>
 * 每轮将一个最大数交换至有序区前端。比较 len-1轮，每轮比较 N-1次
 * <p>
 * 时间复杂度：O(n^2)  空间复杂度：O(1)
 * 【稳定排序】
 */
public class BubbleSort<T extends Comparable> extends AbsSort<T> {

    @Override
    public T[] sort(T[] list, boolean type) {
        if (list != null && list.length > 1) {
            for (int i = 1; i < list.length; i++) {//比较 N-1次
                for (int j = 0; j < list.length - i; j++) {
                    if (needSwap(list[j], list[j + 1], type)) {
                        swap(list, j, j + 1);
                    }
                }
            }
        }
        return list;
    }
}
