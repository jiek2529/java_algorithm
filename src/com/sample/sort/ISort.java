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
     */
    void sort(T[] list, boolean type);
}
