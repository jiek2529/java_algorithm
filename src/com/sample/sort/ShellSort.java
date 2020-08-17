package com.sample.sort;

/**
 * Created by jiek on 2020/8/17.
 * <p>
 * 希尔排序
 * <p>
 * 插入排序的改进版
 * <p>
 * <p>
 * 【不稳定排序】
 */
public class ShellSort<T extends Comparable> extends AbsSort<T> {

    /**
     * 临时用于统计交换次数
     */
    @Deprecated
    int swapCount = 0;

    /**
     * @param list
     * @param type true升序 或 false降序
     *             <p>
     *             步长衰减率缺省值为 2
     */
    @Override
    public T[] sort(T[] list, boolean type) {
        return sort(list, type, 2);
    }

    /**
     * @param list
     * @param type  true升序 或 false降序
     * @param decay 步长衰减率 最小值为2
     */
    public T[] sort(T[] list, boolean type, int decay) {
//        步长
        int gap = 1;
        if (decay < 2) {
            decay = 2;
        }
        while (gap < list.length) {
            gap = gap * decay + 1;//1 4 13 40 121...
        }//此处不能写成 gap = list.lenght / groupLen + 1; 会导致 gap > 1时，gap / groupLen == 0 导致排序未完成就结束排序

        while (gap > 0) {
            for (int i = gap; i < list.length; i++) {
                T tmp = list[i];
                int j = i - gap;
                //相隔gap 步长向前取值，与tmp比较交换
                while (j >= 0 && needSwap(list[j], tmp, type)) {
                    list[j + gap] = list[j];
                    ++swapCount;
//                    System.out.println("\t\t" + (j + gap) + " <--> " + j);
                    j -= gap;
                }

//                最后一个位置一定是不满足交换条件的，所以要用交换至j+gap
                list[j + gap] = tmp;
//                System.out.println("\ti=" + i + " ; gap=" + gap + " : " + Arrays.toString(list));
            }
//            System.out.println("swapCount=" + swapCount + "  gap=" + gap);
            gap /= decay;// (int) Math.floor(gap / 2);
            if (decay <= 1) {
                throw new RuntimeException("步长衰变率不能小于2， 当前值[" + decay + "]");
            }
        }
        System.out.println("\tShellSort swapCount=" + swapCount + "; decay=" + decay);
        return list;
    }
}
