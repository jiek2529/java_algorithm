package com.sample.util;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.sample.util.Util.*;

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
    static List<String> staticList = new CopyOnWriteArrayList<String>();

    public static void main(String[] args) {
        try {
            Integer a = 1;
            Integer b = 2;
            Integer c = null;
            Boolean flag = false;
// a*b 的结果是 int 类型，那么 c 会强制拆箱成 int 类型，抛出 NPE 异常
            Integer result = (flag ? a * b : c);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("封装类的四则运算会导致自动拆箱，在三目表达式中会导致两种结果都拆箱，导致 NPE！");
        }

        testListFeatures();

        testSetFeatures();

        testHashMap();

        foreachRemove();

//        copyOnWriteArrayListIteratorModification();
    }

    private static void testHashMap() {
        int len = 3;
        Map<String, Integer> map = new HashMap<>(len);

        for (int i = 0; i < 18; i++) {
            map.put("1_" + i, i);
        }
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

        Set treeset = new TreeSet(new Comparator<String>() {//TreeSet --> TreeMap.put compare key, possibly null need
            // to check -> maybe throws NullPointerException
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

    /**
     * 验证iterator遍历时的抛并发修改异常的场景与逻辑
     * <p>
     * ali 的 java 开发指南推荐
     */
    private static void foreachRemove() {
        splitLine();
        List<String> stringList = new ArrayList<>();
        stringList.add("1");
        stringList.add("2");
        stringList.add("3");

        /*抛异常是因为checkForComodification的 modcount!=expectedModCount,因为每次修改都会导致其加1，
        所以在只删除遍历倒数第二条时，永远不会有问题，否则导致next为null而结束，而不触发 ConcurrentModificationException*/
        try {
            for (String s : stringList) {//语法糖会编译成 iterator next 遍历方式
                if (Objects.equals(s, "2")) {//删除不是倒数第二条记录时，结果会抛 ConcurrentModificationException
                    stringList.remove(s);
                }
            }
            System.out.println(stringList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        splitLine();
        //第一种
        List<String> stringList_1 = new ArrayList<>();
        stringList_1.add("1");
        stringList_1.add("2");
        stringList_1.add("3");

        for (int i = 0; i < stringList_1.size(); i++) {
            if (Objects.equals(stringList_1.get(i), "1")) {
                stringList_1.remove(stringList_1.get(i));
            }
        }
        System.out.println(stringList_1);

//第二种
        splitLine();
        List<String> stringList_2 = new ArrayList<>();
        stringList_2.add("1");
        stringList_2.add("2");
        stringList_2.add("3");

        for (int i = 0; i < stringList_2.size(); i++) {
            if (Objects.equals(stringList_2.get(i), "2")) {
                stringList_2.remove(stringList_2.get(i));
            }
        }
        System.out.println(stringList_2);

//第三种
        splitLine();
        List<String> stringList_3 = new ArrayList<>();
        stringList_3.add("1");
        stringList_3.add("2");
        stringList_3.add("3");

        for (int i = 0; i < stringList_3.size(); i++) {
            if (Objects.equals(stringList_3.get(i), "3")) {
                stringList_3.remove(stringList_3.get(i));
            }
        }
        System.out.println(stringList_3);

        splitLine();
        List<String> stringList_4 = new ArrayList<>();
        stringList_4.add("0");
        stringList_4.add("4");
        stringList_4.add("1");
        stringList_4.add("5");
        stringList_4.add("2");
        stringList_4.add("6");
        stringList_4.add("3");

        for (int i = 0; i < stringList_4.size(); i++) {
            if (Objects.equals(stringList_4.get(i), "" + i)) {//会依上方数组[0,4,1,5,2,6,3]的次序，将0，1，2，3给删除掉，导致不安全
                stringList_4.remove(stringList_4.get(i));
            }
        }
        System.out.println(stringList_4);//结果为[4,5,6],所以遍历时删除是很不稳定的过程，固然ali 推荐使用iterator 去遍历删除，会直接抛异常

        iteratorRemove();
    }

    /**
     * 单线程下使用iterator 安全删除元素
     */
    private static void iteratorRemove() {
        splitLine();
        List<String> stringList = new ArrayList<>();
        stringList.add("1");
        stringList.add("2");
        stringList.add("3");

        Iterator<String> iterator = stringList.iterator();
        while (iterator.hasNext()) {
            String it = iterator.next();
            if (Objects.equals(it, "" + it)) {
                /*这样删除，不会导致抛 ConcurrentModificationException，因为它重置了 expectedModCount=modcount
                来解决问题了。但这不适用于多线程并发时。*/
                iterator.remove();
            }
        }
        System.out.println(stringList);

        copyOnWriteArrayListIteratorModification();
    }

    private static void copyOnWriteArrayListIteratorModification() {
        splitLine();
        for (int i = 0; i < 12; i++) {
            staticList.add("" + i);
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                Iterator<String> iterator = staticList.iterator();
                while (iterator.hasNext()) {
                    String str = iterator.next();
                    System.out.println(str);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        new Thread(() -> {
//            Iterator<String> iterator = staticList.iterator();//CopyOnWriteArrayList的 Iterator 是不支持 remove
            /*while (iterator.hasNext()) {
                String it = iterator.next();
                if (Objects.equals(it, "" + (Integer.valueOf(it) * 2))) {
                    iterator.remove();
                }
            }*/
            staticList.remove("0");
            staticList.remove("3");
            staticList.remove("5");
            System.out.println("线程里删除 1："+staticList);
        }).start();

//        lambda
        (new Thread(() -> {
            staticList.remove("7");
            staticList.remove("11");
            System.out.println("线程里删除2 ：" + staticList);
        })).start();
        System.out.println("主线程打印原值："+staticList);
    }
}
