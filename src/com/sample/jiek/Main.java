package com.sample.jiek;

import com.sample.jiek.sorting.BubbleSort;
import com.sample.jiek.sorting.Sortable;

import java.util.Arrays;

/**
 * Created by jiek on 09/06/2017.
 */
public class Main {
    private int[] list;

    public static void main(String[] args) {
        Main mMain = new Main();

//    冒泡排序，在调fibonacci，存在被虚机识别为死循环问题，使用while循环去解决。
        mMain.testSort(new BubbleSort());
    }

    private static void log(String msg) {
        System.out.println(msg);
    }

    /**
     * if list size is less than 100, then print it to String by Arrays.toString(list)
     *
     * @param list int[] list
     */
    private static void logList(int[] list) {
        if (list.length > 100) return;
        System.out.println(Arrays.toString(list));
    }

    /**
     * use sortable interface object to sort list.
     *
     * @param sortable
     */
    private void testSort(Sortable sortable) {
        checkedList();

        long start = System.currentTimeMillis();
        sortable.sort(list);
        log("Sort with [ " + sortable.getClass().getName() + " ] for " + (System.currentTimeMillis() - start) + " milliseconds");
        logList(list);
    }

    /**
     * initial mix list
     */
    private void initialList() {
        int capacity = 1 << 16,  //创建数组容量申明
                mixTimes = 1 << 1;//打乱数据的轮数，每轮进行数据容量次随机调换。

        long start = System.currentTimeMillis();

        log("the task starts at " + start);
        System.out.println("\ninitial list capacity：" + capacity + " ,and mix times ：" + mixTimes);

        MixList mMixList = new MixList();

//  初始化数据
        list = mMixList.initList(capacity);
        log(System.currentTimeMillis() - start + " <------ initList used time");
        logList(list);

// 打乱数组
        start = System.currentTimeMillis();
        mMixList.mixList(list, mixTimes);
        logList(list);
        log(System.currentTimeMillis() - start + " <------ mix " + mixTimes + " times used time");
    }

    /**
     * make sure the list is initialized.
     */
    private void checkedList() {
        if (list == null || list.length == 0) {
            initialList();
        }
    }

}
