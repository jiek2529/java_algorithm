package com.sample.sort;

import java.util.Arrays;

/**
 * Created by jiek on 2020/8/15.
 * <p>
 * 堆排序算法
 * <p>
 * 堆排序结构特点：
 *      每个节点至根节点都是有序的
 * 堆结构：左子下标 = 2 * N + 1；
 *      右子下标 = 左下标 + 1。
 * 如：{0 1 2 3 4 5 6}
 * ___0：左 = 2 * 0 + 1, 右 = 左 + 1；
 * ___2：左 = 2 * 2 + 1, 右 = 左 + 1;
 * ___4：左 = 2 * 4 + 1, 右 = 左 + 1。
 * <p>
 * l0:         0
 * l1:    1         2
 * l2:  3   4     5   6
 * l3: 7 8 9 10  11
 * <p>
 * 时间复杂度(默认最坏)：O(n*log(n)),空间复杂度 O(1)
 * <p>
 * 【不稳定排序】
 */
public class HeapSort<T extends Comparable> extends AbsSort<T> {

    /**
     * 选择排序-堆排序
     *
     * @param array 待排序数组
     * @return 已排序数组
     */
    public void sort(T[] array, boolean type) {
        //最后一个非叶节点下标: array.length / 2 - 1, 下标从0开始
//        System.out.println("先从非叶节点向前调整，将最大值交换至其堆顶");

        for (int i = array.length / 2 - 1; i >= 0; i--) {
            adjustHeap(array, i, array.length, type);  //调整i位置的最大值至其堆顶。
        }
//        System.out.println("建堆完成，最大值已经调整至堆顶了：" + Arrays.toString(array));
//        System.out.println("------\n");

        // 下面，开始排序逻辑
        for (int j = array.length - 1; j > 0; j--) {
            // 元素交换,作用是堆顶移除堆结构
            // 把大顶堆的根元素，放到数组的最后；换句话说，就是每一次的堆调整之后，都会有一个元素到达自己的最终位置
            swap(array, 0, j);
            // 元素交换之后，毫无疑问，最后一个元素无需再考虑排序问题了。
            // 接下来我们需要排序的，就是已经去掉了部分元素的堆了，这也是为什么此方法放在循环里的原因
            // 而这里，实质上是自上而下，自左向右进行调整的
            adjustHeap(array, 0, j, type);
        }
    }

    /**
     * 在 array 的堆结构中，从 index 下标开始，将其下所有节点调整为父节点是子节点中最大值。
     * <p>
     * 每个节点与左右子节点比较，将大值交换至堆顶，（左右节点无大小关系限制）；
     * 无交换时结束循环。
     * 交换后，将游标移至被交换位置，再与其子节点循环交换，直至无交换而结束循环。
     *
     * @param array  待交换堆
     * @param index  起始结点下标
     * @param length 堆的长度
     * @param type
     */
    void adjustHeap(T[] array, int index, int length, boolean type) {
        // index 的数据，用于在所有子结构中移至堆顶
        T temp = array[index];

//        循环找出 index 子树中最大值与 index节点比较，将大值交换至堆顶
//        将 k 跳至交换位，依次调堆，实现
        for (int k = 2 * index + 1; k < length; k = 2 * k + 1) {  //2*i+1为左子树i的左子树(因为i是从0开始的),2*k+1为k的左子树
            // 找出 index 的左右子节点较大数的下标，节点左右子节点下标是挨着的
            if (k + 1 < length && needSwap(array[k + 1], array[k], type)) {
                k++;
            }
            //如果发现结点(左右子结点)大于根结点，则进行值的交换
            if (needSwap(array[k], temp, type)) {
                swap(array, index, k);
                // 如果子节点更换了，那么，以子节点为根的子树会受到影响,所以，循环对子节点所在的树继续进行判断
                index = k;
            } else {  //当堆顶为最大值时，无需交换，将终止根为index下标的循环交换
                break;
            }
        }
//        System.out.println("adjustHeap: " + Arrays.toString(array) + "  i=" + index + "; len=" + length);
    }
}
