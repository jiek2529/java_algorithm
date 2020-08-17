package com.sample.sort;

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
 * 4.
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

    public static void main(String[] args) {
//        冒泡排序 测试
        new BubbleSort().test(mockList(), sortType);
//        选择排序 测试
        new SelectionSort().test(mockList(), sortType);
//        插入排序 测试
        new InsertionSort().test(mockList(), sortType);
    }

    /**
     * 根据 checkStable 标识选择原数据类型
     *
     * @return
     */
    private static Comparable[] mockList() {
        Integer[] nums = {3, 5,
                2, 2, 1, 2
//                2, 26, 36, 19, 4, 4, 38, 44, 47, 50, 48, 4, 15
        };

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
}
