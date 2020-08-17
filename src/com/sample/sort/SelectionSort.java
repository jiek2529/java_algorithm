package com.sample.sort;

/**
 * Created by jiek on 2020/8/17.
 * <p>
 * 选择排序
 * <p>
 * 时间复杂度：O(n^2)  空间复杂度：O(1)
 * 【不稳定排序】
 */
public class SelectionSort<T extends Comparable> extends AbsSort<T> {

    @Override
    public T[] sort(T[] list, boolean type) {
//        每轮向后找到的最小数下标
        int min;
        //比较 N-1轮
        for (int i = 0; i < list.length - 1; i++) {
            min = i;

//            从 i+1 位向后，找出最小的小于 i 位数的下标
            for (int j = i + 1; j < list.length; j++) {
                if (needSwap(list[min], list[j], type)) {
//                    记录当前比 i 位值小的下标；循环完后，即找到最小数的下标
                    min = j;
                }
            }

            if (min != i) {
                swap(list, i, min);
            }
        }
        return list;
    }
}
