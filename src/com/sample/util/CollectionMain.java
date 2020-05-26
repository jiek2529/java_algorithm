package com.sample.util;

import java.util.*;

/**
 * Created by jiek on 2020/5/26.
 * <p>
 * 集合类的常规功能检验
 * <p>
 * HashSet 内部使用 HashMap 存数据：无序、不重复、可存一个null
 * <p>
 * TreeSet 内部使用 TreeMap 存数据:红黑树、不重复、可有可无一个 null（由 Comparator 决定）
 * <p>
 * 转线程安全有两种方式：java.util.concurrent.Concurrent** ;  Collections.synchronied**
 */
public class CollectionMain {
    public static void main(String[] args) {
        testListFeatures();
        testSetFeatures();
    }

    private static void testSetFeatures() {
        Util.splitLine();
        System.out.println("Set特性：无序存储、不重复；可存null");
        System.out.println("HashSet特性：无序存储、不重复；" +
                "可存null，不可排序");
        System.out.println("TreeSet特性：有序、不重复；实现了SortedSet接口，能够对集合中的对象进行排序。" +
                "不能存null， 使用 TreeMap 的红黑树存储");

        Set set = new HashSet();
        set.add("a");
        set.add("B");
        set.add("D");
        set.add("C");
        set.add("D");

        set.add(null);
        set.add(null);
        set.add(null);

        set.add("1");
        set.add("3");
        set.add("1");
        set.add(null);
        set.add("0");
        System.out.println(set);//[null, a, B, C, D]  //[null, 0, a, 1, B, C, 3, D]

        traverseIterator(set.iterator());

        Set treeset = new TreeSet(new Comparator<String>() {//TreeSet --> TreeMap.put compare key, possibly null need to check -> maybe throws NullPointerException
            @Override
            public int compare(String o1, String o2) {
                if (o1 == null) return -1;//添加null 是否抛异常由此区域控制，默认无 Comparator 时，不检测null 值
                if (o2 == null) return 1;
                return o1.compareTo(o2);
            }
        });
        treeset.addAll(set);
        Util.splitLine("TreeSet 排序");
        traverseIterator(treeset.iterator());
    }

    private static void testListFeatures() {
        Util.splitLine();
        System.out.println("List特性：有序存储、可重复；");

        System.out.println("ArrayList特性：有序存储、可重复、访问快；" +
                "\n\t内存连续、扩容变容效率低、增删效率可能低；");

        System.out.println("LinkedList特性：有序存储、可重复、访问慢；" +
                "\n\t内存可不连续、增删效率高；");

        Util.splitLine("testListFeatures");
        List<String> list = new ArrayList<>();//new LinkedList<>(); //两种操作方式基本相同
        list.add("1");
        list.add("3");
        list.add("1");
        list.add(null);
        list.add(null);
        list.add(null);
        list.add("0");
        System.out.println(list);// [1, 3, 1, null, null, null, 0]
        traverseList(list);

        Comparator<String> stringComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o2 == null) return -1;
                if (o1 == null) return 1;
                return o1.compareTo(o2);
            }
        };

        Util.splitLine("\t\tCollections.sort list");
//        Collections.sort(list);//会出现NullPointerException
        Collections.sort(list, stringComparator);//带比较器的排序，排序 ArrayList
//        Arrays.sort(list.toArray(), (Comparator) stringComparator);//排序 Array []

        traverseList(list);
    }

    private static void traverseList(List<String> list) {
        Util.splitLine("traverse the List using for each");
        for (String s : list) {
            System.out.println(s);
        }
        traverseListIterator(list.listIterator());
        traverseIterator(list.iterator());
    }

    private static void traverseListIterator(ListIterator listIterator) {
        Util.splitLine("traverse the List using ListIterator");
        while (listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }
    }

    private static void traverseIterator(Iterator iterator) {
        Util.splitLine("traverse the List using Iterator");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
