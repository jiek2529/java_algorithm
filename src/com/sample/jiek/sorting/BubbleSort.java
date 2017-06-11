package com.sample.jiek.sorting;

/**
 * Created by jiek on 09/06/2017.
 * reference: https://en.wikipedia.org/wiki/Bubble_sort
 * <p>
 * 耗时在循环次数、循环方法（for|while）、交换次数（交换是最耗时间，约9成时间耗在交换数据上）
 * <p>
 * 在数据量大时，如果使用fibonacci方式调用时，存在抛stackoverflowError。在写算法时，值得注意！
 */
public class BubbleSort extends SortableSwap {

    /**
     * 对数据量大时的测试，发现while 比for 慢一半。
     *
     * @param list
     */
    @Override
    public void sort(int[] list) {
        if (list != null && list.length > 1) {
//        sortWithWhile(list);
            sortWithFor(list);
        }
    }

    /**
     * 使用两层for循环完成比较交换
     *
     * @param list int数组
     */
    public void sortWithFor(int[] list) {
        for (int i = list.length - 1; i > 0; i--) {//循环列表长减一次。
            for (int j = 0; j < i; j++) {//多最左向右比较交换
                if (list[j + 1] < list[j]) {
                    swap(list, j, j + 1);
                }
            }
        }
    }

    /**
     * 使用while去交换
     *
     * @param list int数组
     */
    public void sortWithWhile(int[] list) {
        int lastIndex = list.length - 1;
        while (lastIndex > 0) {
            swapNeighbor(list, lastIndex--);//此处可以把下边方法内容体放过来；保留它因要做fibonacci的栈溢出错误的。
        }

    }

    /**
     * 从index开始向last冒泡
     *
     * @param list      数组
     * @param lastIndex 冒泡的最右端索引
     */
    private void swapNeighbor(int[] list, int lastIndex) {
        /*if (index < lastIndex) {
            int extra = list[index];
            int j = index + 1;
            if (list[j] < extra) {
                list[index] = list[j];
                list[j] = extra;
            }
            sortNeighbor(list, ++index, last);//在fibonacci，存在被虚机识别为死循环问题，使用while或for循环去解决。
        }*/

        int index = 0, //索引当前操作位置
                temp;
        while (index < lastIndex) {
            if (list[index + 1] < list[index]) {
                swap(list, index, index + 1);
            }
            index++;
        }
    }
}
