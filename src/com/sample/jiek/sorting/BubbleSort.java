package com.sample.jiek.sorting;

/**
 * Created by jiek on 09/06/2017.
 * reference: https://en.wikipedia.org/wiki/Bubble_sort
 */
public class BubbleSort implements Sortable {

    @Override
    public void sort(int[] list) {
        for (int i = list.length; i > 0; i--) {
            sortNeighbor(list, i - 1);//此处可以把下边方法内容体放过来；保留它因要做fibonacci的栈溢出错误的。
        }
    }

    /**
     * 从index开始向last冒泡
     *
     * @param list 数组
     * @param last 冒泡的无序处的顶
     */
    private void sortNeighbor(int[] list, int last) {
        /*if (index < last) {
            int extra = list[index];
            int j = index + 1;
            if (list[j] < extra) {
                list[index] = list[j];
                list[j] = extra;
            }
            sortNeighbor(list, ++index, last);//在fibonacci，存在被虚机识别为死循环问题，使用while循环去解决。
        }*/

        int index = 0, //索引当前操作位置
                extra, j;//交换临时缓存

        while (index < last) {
            extra = list[index];
            j = index + 1;
            if (list[j] < extra) {
                list[index] = list[j];
                list[j] = extra;
            }
            index++;
        }
//        return list;
    }
}
