package com.sample.sort;

import com.sample.util.MockArrayUtil;

import java.util.Arrays;

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
        for (int i = 0; i < 5; i++) {
            test_allSortPerformance();
        }

//        测试 ShellSort 的步长衰减性能对比
//        test_ShellSortDecay();
    }

    /**
     * 测试所有排序算法性能比
     */
    private static void test_allSortPerformance() {
        Class[] classes = {
//                数据量大时，排序性能低至高，依次如下

//                BubbleSort.class,//冒泡排序               【稳定排序】
//                BubbleSort.class,//冒泡排序               【稳定排序】
//                SelectionSort.class,//选择排序              【不稳定排序】
//                InsertionSort.class,//插入排序,一次移动一位 【稳定排序】
                ShellSort.class, //希尔排序 插入排序改进版，移动步长位   【不稳定排序】
                MergeSort.class, //归并排序                【稳定排序】
                QuickSort.class, //快速排序                   【不稳定排序】
                RadixSort.class, //基数排序 暂仅支持 Integer 数据排序        【稳定排序】
                HeapSort.class, //堆排序                      【不稳定排序】
//                CountingSort.class, // 计数排序仅适用于数字种类很少时的统计排序
//                Arrays.sort 默认使用的是双轴快速排序，经验证性能与快排差不多    【不稳定排序】

//                当数据量少时[约1-1024]，性能如下 QuickSort > HeapSort > RadixSort

//                相对来说，堆排序是比较高效的排序算法，时间复杂度 O(n*lgn) 空间复杂度 O(n) 不稳定算法
        };

        //使用工厂模式，对列表进行排序
        SortFactory sortFactory = new SortFactory();

//        模拟批量数据
        Comparable[] list = mockArray(1 << 20);//13 = 8K 量
        for (int i = 0; i < classes.length; i++) {
            Comparable[] datas = Arrays.copyOf(list, list.length);
            ((AbsSort<Comparable>) sortFactory.getSort(classes[i])).test(datas, sortType);//在原数据上进行排序
        }
        Comparable[] a = Arrays.copyOf(list, list.length);
        long usedTime = System.currentTimeMillis();
        Arrays.sort(a);//, (o1, o2) -> o1.compareTo(o2));//顺序或倒序
        System.out.println("Arrays.sort 使用的是双轴快排， 耗时时：" + (System.currentTimeMillis() - usedTime) + "\n");
    }

    /**
     * 根据 checkStable 标识选择原数据类型
     *
     * @return
     */
    private static Comparable[] mockArray(int len) {
        int[] array;

//        随机算法产生指定数量的原始数据
        if (randomNumsFlag) {
            array = MockArrayUtil.mock(len);
        } else {
//            指定少里数据进行排序验证
            array = new int[]{
//                9, 8, 7, 6, 5, 4, 3, 2, 1, 0
                    3, 5, 2, 2, 1, 2
//                    3, 5, 2, 26, 36, 19, 4, 4, 38, 44, 47, 50, 48, 4, 15
            };
        }

        if (!checkStable) {
//            return Util.getArrayBoxed(array);
            return Arrays.stream(array).boxed().toArray(Integer[]::new);

        } else {
            SortBean[] arrayList = new SortBean[array.length];
            for (int i = 0; i < array.length; i++) {
                arrayList[i] = new SortBean(array[i], i);
            }
            return arrayList;
        }
    }

    /**
     * 单测希尔排序的步长衰减效率比
     */
    private static void test_ShellSortDecay() {
//        模拟批量数据
        Comparable[] list = mockArray(1 << 15);

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
