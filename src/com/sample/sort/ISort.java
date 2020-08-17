package com.sample.sort;

/**
 * Created by jiek on 2020/8/17.
 */
public interface ISort<T extends Comparable> {

    /**
     * 前提：list 不为空，且数组长大于1
     *
     * @param list
     * @param type true 顺序； false 倒序
     * @return 因有排序算法需要新空间去交换，所以增加返回值
     */
    T[] sort(T[] list, boolean type);
}
