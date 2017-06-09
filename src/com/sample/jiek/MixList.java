package com.sample.jiek;

import java.util.Random;

/**
 * Created by jiek on 09/06/2017.
 */
class MixList {

    private int startNum = 0;//用于制作连续的数字数组的起始值。
    private Random random = new Random();

    /**
     * 打乱数据顺序，每轮换位list.length次数。
     *
     * @param list 需打乱数组
     */
    private void mixList(int[] list) {
        int left, right, extra;
        for (int i = 0; i < list.length; i++) {
            left = random.nextInt(list.length);
            right = random.nextInt(list.length);
            extra = list[left];
            list[left] = list[right];
            list[right] = extra;
        }
//        return list;
    }

    /**
     * 控制打乱回数为0-65536回。并执行打乱。
     *
     * @param list     数组
     * @param mixTimes 打乱次数
     */
    void mixList(int[] list, int mixTimes) {
        if (mixTimes > 0) {
            if (mixTimes > 1 << 16) {
                mixTimes = 1 << 16;
            }
            for (int i = 0; i < mixTimes; i++) {
                mixList(list);
            }
        }
    }

    /**
     * initial list data 初始化数组
     *
     * @param capacity 数组容量
     * @return list
     */
    int[] initList(int capacity) {
        int[] list = new int[capacity];
        for (int i = 0; i < list.length; i++) {
            list[i] = startNum + i;
        }
        return list;
    }
}
