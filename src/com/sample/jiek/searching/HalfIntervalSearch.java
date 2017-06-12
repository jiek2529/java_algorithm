package com.sample.jiek.searching;

/**
 * Created by jiek on 13/06/2017.
 */
public class HalfIntervalSearch implements Searchable {
    @Override
    public boolean searching(int[] list, int target) {
        return fibonacciSearch(list, 0, list.length - 1, target) >= 0;//递归实现折半查找
//        return binarySearch2(list, 0, list.length - 1, target) >= 0;//非递归实现折半查找
    }


    /**
     * 递归方法实现折半查找
     *
     * @param data   顺序数组(选小至大排序，如改为其它排序方式，此处比较需要修改)
     * @param from   起始下标
     * @param to     终止下标
     * @param target 要查找的值
     * @return 查找值在数组中的下标，如果没找到则返回-1
     */
    private int fibonacciSearch(int[] data, int from, int to, int target) {
        if (from <= to) {
            int mid = from + (to - from) / 2;//记录中间值
            if (data[mid] < target) {//中间值比查询值大，则向左则继续查找
                return fibonacciSearch(data, mid + 1, to, target);
            } else if (data[mid] > target) {//中间值比查询值大，则向右则继续查找
                return fibonacciSearch(data, from, mid - 1, target);
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 非递归方法实现折半查找
     *
     * @param data   顺序数组(选小至大排序，如改为其它排序方式，此处比较需要修改)
     * @param from   起始下标
     * @param to     终止下标
     * @param target 要查找的值
     * @return 查找值在数组中的下标，如果没找到则返回-1
     */
    private int withWhileSearch(int[] data, int from, int to, int target) {
        while (from <= to) {
            int mid = from + (to - from) / 2;
            if (data[mid] < target) {
                from = mid + 1;
            } else if (data[mid] > target) {
                to = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
