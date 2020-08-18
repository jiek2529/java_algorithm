package com.sample.sort;

import java.util.Arrays;

/**
 * Created by jiek on 2020/8/17.
 * <p>
 * 排序算法抽象父类
 * <p>
 * sort方法需要外部判空处理
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
     * @param array
     * @param l
     * @param r
     */
    void swap(T[] array, int l, int r) {
        if (l == r) {
            return;
        }
        T tmp = array[l];
        array[l] = array[r];
        array[r] = tmp;
//        System.out.println("swap: " + l + ", " + r);
    }

    /**
     * 将 list从 l至 r向右移一位
     *
     * @param array 原数组
     * @param l     左下标
     * @param r     右下标
     */
    void moveRight1Step(T[] array, int l, int r) {
        //防越界
        if (l <= r && l >= 0 && r < array.length - 1) {
            for (int i = r; i >= l; i--) {
                array[i + 1] = array[i];
            }
        }
    }

    /**
     * 测试算法结果
     *
     * @param array
     * @param sortType
     */
    @Deprecated
    void test(T[] array, boolean sortType) {
        if (array.length < 32) {
            System.out.println("\t原数组 the original list: \t" + Arrays.toString(array));
        }
        long usedTime = System.currentTimeMillis();
        if (array != null && array.length > 1) {
            array = sort(array, sortType);
            usedTime = (System.currentTimeMillis() - usedTime);

            check(array, sortType);
        }
        if (array.length < 32) {
            System.out.println((sortType ? "顺序" : "倒序") + this.getClass().getSimpleName() + " >\t" + "the sorted " +
                    "list: " + Arrays.toString(array));
        }
        System.out.println(this.getClass().getSimpleName() + " >\t " + (sortType ? "顺序" : "倒序") + "数组长度=" + array
                .length + " 排序用时：" + usedTime);
    }

    /**
     * 顺序与倒序检验
     *
     * @param array
     * @param type
     */
    @Deprecated
    public void check(Comparable[] array, boolean type) {
        if (array == null || array.length == 0) {
            System.out.println("list is " + array);
            return;
        }
        for (int i = 1; i < array.length; i++) {
            if (type ?
                    array[i - 1].compareTo(array[i]) > 0 :
                    array[i - 1].compareTo(array[i]) < 0
                    ) {
                System.out.println("验证不通过: [" + i + "] 小于前一位 " + array[i - 1] + " - " + array[i]);
                return;
            }
        }
        System.out.println("\t\t\t========== 排序验证通过！！！ =========== ");
    }
}
