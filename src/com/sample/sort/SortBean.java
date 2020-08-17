package com.sample.sort;

/**
 * Created by jiek on 2020/8/17.
 * 用户进行排序的对象
 * <p>
 * serial 是原序号，用于排序后判断算法是否是稳定排序（同值序号是否会乱序）
 */
public class SortBean implements Comparable {
    //排序数
    int num;
    //    序号
    int serial;

    public SortBean(int num, int serial) {
        this.num = num;
        this.serial = serial;
    }

    @Override
    public String toString() {
        return "\t{num = " +
                num +
                ", serial = " + serial +
                "}";
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof SortBean) {
            return this.num - ((SortBean) o).num;
        } else {
            return -1;
        }
    }
}
