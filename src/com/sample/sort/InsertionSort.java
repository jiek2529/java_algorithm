package com.sample.sort;

/**
 * Created by jiek on 2020/8/17.
 * 插入排序
 *
 * 从当前下标向后找最大或最小值的下标，与当前下标值进行交换
 * <p>
 * 遍历式与折半式两种,默认折半式
 * <p>
 * 遍历式> 时间复杂度：O(n^2) 空间复杂度：O(1)
 * 折半式> 时间复杂度：O(n*log(n)) 空间复杂度：O(1)
 * 【稳定排序】
 */
public class InsertionSort<T extends Comparable> extends AbsSort<T> {

    /**
     * 1: 折半式，0：遍历式
     */
    byte method = 1;

    @Override
    void sort(T[] list, boolean type) {
        for (int i = 1; i < list.length; i++) {
            int position = -1;
            position = binarySearchPosition(list, i, type);
            if (position >= 0) {
                T tmp = list[i];
                moveRight1Step(list, position, i - 1);
                list[position] = tmp;
            }
        }
    }

    /**
     * 折半查找list的 [0,position-1]范围内【有序的】，第一个大于 position下标数的下标
     *
     * @param list
     * @param position 待比较交换的下标
     * @param type     true:顺序  false: 倒序
     * @return 第一个大于 position 的下标
     */
    private int binarySearchPosition(T[] list, int position, boolean type) {
        if (method == 1) {//折半查找
            int l = 0, r = position - 1;
            while (l <= r) {
                if (l == r) {
                    if (needSwap(list[r], list[position], type)) {
                        return r;
                    } else {
                        return -1;
                    }
                }
                if (needSwap(list[(l + r) / 2], list[position], type)) {
                    r = (l + r) / 2;
                } else {
//                    l = (l + r) / 2;//存在死循环可能，l + r相临数相加为奇数时，除以2任为 l
                    l = ((l + r) / 2) + 1;//加 1防死循环
                }
            }
            return r;
        }

//        if (method != 1) {//遍历查找法
//            for (int j = position - 1; j >= 0; j--) {
//                if (needSwap(list[j], list[position], type)) {
//                    position = j;
//                }
//            }
//        }

        return position;
    }
}
