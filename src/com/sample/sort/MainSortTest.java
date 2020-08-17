package com.sample.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by jiek on 2020/8/17.
 * <p>
 * 测试所有算法的主入口
 * <p>
 * 可使用 SortBean 或 Integer 进行数据排序
 * 可设定顺序或倒序排序
 * <p>
 * 1. BubbleSort 冒泡排序      【稳定排序】
 * 2. SelectionSort 选择排序 【不稳定排序】
 * 3. InsertionSort 插入排序   【稳定排序】
 * 4. ShellSort 希尔排序     【不稳定排序】
 */
public class MainSortTest {

    /**
     * 验证算法稳定性: true 数据用 SortBean，false 数据用 Integer
     */
    final static boolean checkStable = false;
    /**
     * sortType?"顺序":"倒序"
     */
    final static boolean sortType = true;
    /**
     * 是否随机产生数据
     */
    final static boolean randomNumsFlag = true;

    public static void main(String[] args) {
//        所有算法性能对比
        test_allSortPerformance();

//        测试 ShellSort 的步长衰减性能对比
//        test_ShellSortDecay();

//   -------- 以下为逐一测试 -------
//        sortFactory.getSort(SelectionSort.class)  .sort(mockList(), sortType);//在原列表中进行移位排序
//        sortFactory.getSort(SelectionSort.class)  .test(mockList(), sortType);//测试输出排序结果

////        冒泡排序 测试
//        new BubbleSort().test(mockList(), sortType);
////        选择排序 测试
//        new SelectionSort().test(mockList(), sortType);
////        插入排序 测试
//        new InsertionSort().test(mockList(), sortType);

    }

    /**
     * 测试所有排序算法性能比
     */
    private static void test_allSortPerformance() {
        Class[] classes = {
                BubbleSort.class,//冒泡排序               【稳定排序】
                BubbleSort.class,//冒泡排序               【稳定排序】
                BubbleSort.class,//冒泡排序               【稳定排序】
                SelectionSort.class,//选择排序              【不稳定排序】
                InsertionSort.class,//插入排序,一次移动一位 【稳定排序】
                ShellSort.class, //希尔排序 插入排序改进版，移动步长位   【不稳定排序】
                HeapSort.class, //堆排序                      【不稳定排序】
        };

        //使用工厂模式，对列表进行排序
        SortFactory sortFactory = new SortFactory();

//        模拟批量数据
        Comparable[] list = mockList(1 << 13);
        for (int i = 0; i < classes.length; i++) {
            Comparable[] datas = Arrays.copyOf(list, list.length);
            ((AbsSort<Comparable>) sortFactory.getSort(classes[i])).test(datas, sortType);//在原数据上进行排序
        }
    }

    /**
     * 根据 checkStable 标识选择原数据类型
     *
     * @return
     */
    private static Comparable[] mockList(int len) {
        Integer[] nums;

//        随机算法产生指定数量的原始数据
        if (randomNumsFlag) {
            Random r = new Random();
            nums = new Integer[len];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = r.nextInt(nums.length);
            }
        } else {
//            指定少里数据进行排序验证
            nums = new Integer[]{
//                9, 8, 7, 6, 5, 4, 3, 2, 1, 0
                    3, 5, 2, 2, 1, 2
//                    3, 5, 2, 26, 36, 19, 4, 4, 38, 44, 47, 50, 48, 4, 15
            };
        }

        if (!checkStable) {
            return nums;
        } else {
            SortBean[] arrayList = new SortBean[nums.length];
            for (int i = 0; i < nums.length; i++) {
                arrayList[i] = new SortBean(nums[i], i);
            }
            return arrayList;
        }
    }

    /**
     * 单测希尔排序的步长衰减效率比
     */
    private static void test_ShellSortDecay() {
//        模拟批量数据
        Comparable[] list = mockList(1 << 6);

        Comparable[] datas;
        long time;
        ShellSort shellSort = new ShellSort();
        for (int i = 1; i < 10; i++) {
            datas = Arrays.copyOf(list, list.length);
            time = System.currentTimeMillis();
            shellSort.sort(datas, sortType, i);
            System.out.println("ShellSort 排序耗时：" + (System.currentTimeMillis() - time));
        }
    }
}
