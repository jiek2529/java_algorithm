package com.sample.sort;

import java.util.Arrays;

/**
 * Created by jiek on 2020/8/17.
 * <p>
 * 归并排序
 * <p>
 * 多路合并，使用同大小新数组交换区
 * <p>
 * 【稳定排序】
 */
public class MergeSort<T extends Comparable> extends AbsSort<T> {
    @Override
    public T[] sort(T[] list, boolean type) {
        return mSort(list, type);
    }

    private T[] mSort(T[] list, boolean type) {
        if (list.length == 1) {
            return list;
        }

        int middle = list.length / 2;
        T[] left = Arrays.copyOfRange(list, 0, middle);
        T[] right = Arrays.copyOfRange(list, middle, list.length);
        list = merge(mSort(left, type), mSort(right, type), type);
        return list;
    }

    private T[] merge(T[] lefts, T[] rights, boolean type) {
        T[] result = Arrays.copyOf(lefts, lefts.length + rights.length);
        int i = 0, j = 0;
        boolean leftOver = false, rightOver = false;
        while (i < lefts.length) {
            while (j < rights.length) {
                //当 left 优先使用完，就要将 right 列表逐一放入结果列表
                if (leftOver) {
                    if (false) {//逐一放入
                        result[i + j] = rights[j];
                        j++;
                        continue;//此行是特别注意
                    } else {
                        System.arraycopy(rights, j, result, i + j, rights.length - j);
                        break;
                    }
                }
                try {
                    if (needSwap(lefts[i], rights[j], !type)) {
                        result[i + j] = lefts[i];
                        i++;
                        if (i == lefts.length) {
                            leftOver = true;
                        }
                    } else {
                        result[i + j] = rights[j];
                        j++;
                        if (j == rights.length) {
                            rightOver = true;
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (rightOver) {
                if(false) {
                    result[i + j] = lefts[i];
                    i++;
                } else {
                    System.arraycopy(lefts, i, result, i + j, lefts.length - i);
                    break;
                }
            }
        }

        return result;
    }
}
