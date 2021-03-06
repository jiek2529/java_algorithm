package com.sample.jiek;

import com.sample.jiek.searching.HalfIntervalSearch;
import com.sample.jiek.searching.OrderSearch;
import com.sample.jiek.searching.Searchable;
import com.sample.jiek.sorting.*;

import java.util.*;

/**
 * Created by jiek on 09/06/2017.
 */
public class Main {
    int capacity = 1 << 4,  //创建数组容量申明
            mixTimes = 1 << 2;//打乱数据的轮数，每轮进行数据容量次随机调换。
    boolean comparisonSortFlag = false;//如果进行多算法间效率对比，此值设置为true。以保证多个算法的原始数组相同。
    private int[] list, clone_mixList;//clone_mixList是用于进行多算法对比时用的克隆初始数组。默认不使用。

    public static void main(String[] args) {
        Main mMain = new Main();

        mMain.experimentSort();//实验排序算法

//        mMain.experimentSearch();//实现搜索算法
    }

    private static void log(String msg) {
        System.out.println(msg);
    }

    /**
     * if list size is less than 100, then print it to String by Arrays.toString(list)
     *
     * @param list int[] list
     */
    private static void logList(int[] list) {
        if (list.length > 100) {
            return;
        }
        System.out.println(Arrays.toString(list));
    }

    //实验排序算法
    private void experimentSort() {
        comparisonSortFlag = true;
        for (int i = 0; i < 1; i++) {//以下以排序算法的效率进行先后的。
//            testSort(new BubbleSort());//冒泡排序；在进行64K的量级数据排序时，就非常的慢了。
//            testSort(new InsertSort());//插入排序；
//            log(i+ " 排序后是否有序："+mMixList.checkOrderly(mMain.list));
//            testSort(new SelectionSort());//选择排序
//            testSort(new QuickSort());//快速排序
//            testSort(new ShellSort());//希尔排序,数量少时效率比快速排序快
//            log(i+ " 排序后是否有序："+mMixList.checkOrderly(mMain.list));
//            testSort(new MergeSort());
//            testSort(new DualPivotQuickSort());
            /*testSort(new Sortable() {
                @Override
                public void sort(int[] list) {
                    Arrays.sort(list);
                }
            });*/
            log("\n");
        }
        comparisonSortFlag = false;

        /*for (int i = 0; i < 10; i++) {
            testForWhildEach_performance();
        }*/
    }

    /**
     * 实验顺序查询与折半查询方式的比较
     */
    private void experimentSearch() {
        capacity = 1 << 28;//因查询本就消耗不大和机器性能过好，所以使用数据量相当大时进行查询实验。
        if (list == null || list.length == 0) {
            list = new MixList().initList(capacity);
        } else {
            new DualPivotQuickSort().sort(list);
        }
        Random random = new Random();
        int searchTarget = 0, offset = 0;
        searchTarget = random.nextInt(capacity) + offset;
        for (int i = 0; i < 10; i++) {
            testSearch(new OrderSearch(), list, searchTarget);
            testSearch(new HalfIntervalSearch(), list, searchTarget);
        }
    }

    private void testSearch(Searchable search, int[] list, int searchTarget) {
        long start = System.currentTimeMillis();
        search.searching(this.list, searchTarget);
        log("search with [ " + search.getClass().getName() + " ] for " + (System.currentTimeMillis() - start) + " milliseconds");
    }

    /**
     * use sortable interface object to sort list.
     *
     * @param sortable 用于接收排序接口实例
     */
    private void testSort(Sortable sortable) {
        sorting_same_list();

        logList(list);
        long start = System.currentTimeMillis();
        sortable.sort(list);
        log("Sort with [ " + sortable.getClass().getName() + " ] for " + (System.currentTimeMillis() - start) + " milliseconds");
        logList(list);
//        log(sortable.getClass().getName()+" 有序： "+new MixList().checkOrderly(list));//可以对排序好的结果进行检查是否是顺序。
    }

    /**
     * 用于进行多个排序算法效率对比时，产生相同数组。
     */
    private void sorting_same_list() {
        if (list == null || list.length == 0) {
            initialList();
        }

        if (comparisonSortFlag) {
            if (clone_mixList == null || clone_mixList.length == 0) {
                clone_mixList = Arrays.copyOf(list, list.length);
            } else {
                list = Arrays.copyOf(clone_mixList, list.length);
            }
        }
    }

    /**
     * initial mix list
     * make sure the list is initialized.
     */
    private void initialList() {
        long start = System.currentTimeMillis();

        log("the task starts at " + start);
        System.out.println("\ninitial list capacity：" + capacity + " ,and mix times ：" + mixTimes);

        MixList mMixList = new MixList();

//  初始化数据
        if (list == null || list.length == 0) {
            list = mMixList.initList(capacity);
            log(System.currentTimeMillis() - start + " <------ initList used time");
            logList(list);
        }

// 打乱数组
        start = System.currentTimeMillis();
        mMixList.mixList(list, mixTimes);
        logList(list);
        log(System.currentTimeMillis() - start + " <------ mix " + mixTimes + " times used time\n");
    }


    /**
     * 使用 1M 数据来测试几种循环方式的运行速度
     * <p>
     * 进行数轮测试，基本无法确认哪个循环遍历方式耗时，只要调换其执行先后顺序，就看出不同的数据对比关系。
     * <p>
     * 真实遍历效率取决于对应获取方式的复杂度。(while 在编译后的会转成for的遍历方式，因此可推断其方式完全相同无差异。)
     * 详情请见官方说明：http://docs.oracle.com/javase/tutorial/java/nutsandbolts/while.html
     */
    private void testForWhildEach_performance() {
        List<Integer> list = new ArrayList<Integer>(1 << 20);

        int runTime = 1000;//执行次数
        for (int i = 0; i < (1 << 20); i++) {
            list.add(i);
        }
        int size = list.size(), tmp;

        long currTime = System.currentTimeMillis();//开始分析前的系统时间
        for (int j = 0; j < runTime; j++) {//迭代
            Iterator<Integer> iter = list.iterator();
            while (iter.hasNext()) {
                tmp = iter.next();
            }
        }
        long time5 = System.currentTimeMillis();
        //基本的for
        for (int j = 0; j < runTime; j++) {
            for (int i = 0; i < size; i++) {
                tmp = list.get(i);
            }
        }
        long time1 = System.currentTimeMillis();

        //foreach
        for (int j = 0; j < runTime; j++) {
            for (Integer integer : list) {
                tmp = integer;
            }
        }
        long time2 = System.currentTimeMillis();

        for (int j = 0; j < runTime; j++) {
            int i = 0;
            while (i < size) {
                tmp = list.get(i++);
            }
        }
        long time3 = System.currentTimeMillis();

        for (int j = 0; j < runTime; j++) {//普通for循环
            for (int i = 0; i < size; i++) {
                tmp = list.get(i);
            }
        }
        long time4 = System.currentTimeMillis();


        long time = time1 - time5;
        System.out.print("use for:" + time);
        time = time2 - time1;
        System.out.print("\tuse foreach:" + time);
        time = time3 - time2;
        System.out.print("\tuse while:" + time);
        time = time4 - time3;
        System.out.print("\tuse for2:" + time);
        time = time5 - currTime;
        System.out.print("\tuse iterator:" + time);
        System.out.println();
    }

}
