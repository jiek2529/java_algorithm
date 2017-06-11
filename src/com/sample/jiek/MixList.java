package com.sample.jiek;

import java.util.Random;

/**
 * Created by jiek on 09/06/2017.
 * 此处提供数组起始值,默认起始值为0
 */
class MixList {

    private int startNum = 0;//用于制作连续的数字数组的起始值。
    private Random random = new Random();

    public MixList() {
    }

    public MixList(int startNum) {
        this.startNum = startNum;
    }

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
     * @return list 有序数组
     */
    int[] initList(int capacity) {
        if (capacity < 0) {
            throw new OutOfMemoryError();
        }
        capacity = (capacity > Integer.MAX_VALUE - 8) ? (Integer.MAX_VALUE - 8) : capacity;//防止定义数组容量越界

        int[] list = new int[capacity];
        for (int i = 0; i < list.length; i++) {
            list[i] = startNum + i;
        }
        return list;
    }

    /**
     * 列表有序检查； 使用遍历，判断前值不大于后值。
     * @param list int[]数组
     * @return 是有序为真，无序为假。
     */
    public boolean checkOrderly(int[] list) {
        for (int i = 0; i < list.length - 1; i++) {
            if (list[i] > list[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
