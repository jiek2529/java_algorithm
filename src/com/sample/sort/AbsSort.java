package com.sample.sort;

import java.util.Arrays;

/**
 * Created by jiek on 2020/8/17.
 * <p>
 * 排序算法抽象父类
 */
public abstract class AbsSort<T extends Comparable> implements ISort<T> {

    /**
     * 根据排序类型，比较两值是否需要交换
     * <p>
     * 当 type=true 时，a > b 需要交换返回true，否则返回 false
     * 当 type=false 时，a < b 需要交换返回true，否则返回 false
     *
     * @param a
     * @param b
     * @param type true 顺序； false 倒序
     * @return
     */
    boolean needSwap(T a, T b, boolean type) {
//        顺序排序
        if (type) {
            return a.compareTo(b) > 0;
        } else {
            return a.compareTo(b) < 0;
        }
    }

    /**
     * 下标数据交换，外部确保L R 下标都不越界
     *
     * @param list
     * @param l
     * @param r
     */
    void swap(T[] list, int l, int r) {
        T tmp = list[l];
        list[l] = list[r];
        list[r] = tmp;
    }

    /**
     * 将 list从 l至 r向右移一位
     *
     * @param list 原数组
     * @param l    左下标
     * @param r    右下标
     */
    void moveRight1Step(T[] list, int l, int r) {
        //防越界
        if (l <= r && l >= 0 && r < list.length - 1) {
            for (int i = r; i >= l; i--) {
                list[i + 1] = list[i];
            }
        }
    }

    /**
     * 测试算法结果
     *
     * @param arrayList
     * @param sortType
     */
    @Deprecated
    void test(T[] arrayList, boolean sortType) {
        if (arrayList.length < 32) {
            System.out.println("\t原数组 the original list: \t" + Arrays.toString(arrayList));
        }
        long start = System.currentTimeMillis();
        if (arrayList != null && arrayList.length > 1) {
            sort(arrayList, sortType);
        }
        if (arrayList.length < 32) {
            System.out.println((sortType ? "顺序" : "倒序") + this.getClass().getSimpleName() + " >\t" + "the sorted " +
                    "list: " + Arrays.toString(arrayList));
        }
        System.out.println(this.getClass().getSimpleName() + " >\t " + (sortType ? "顺序" : "倒序") + "数组长度=" + arrayList
                .length + " 排序用时：" + (System.currentTimeMillis() - start));
    }
}
