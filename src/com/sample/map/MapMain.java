package com.sample.map;

import com.sample.util.Util;

import java.util.HashMap;
import java.util.Map;

import static com.sample.util.Util.traverseIterator;

/**
 * Created by jiek on 2020/6/4.
 */
public class MapMain {
    private int MAXIMUM_CAPACITY = 1 << 30;

    public static void main(String[] args) {
        Map map = new HashMap();//HashTash 不能有空键和空值，HashMap至多一个空键若干空值
        map.put(null, "1");
        map.put("2", null);
        map.put("3", null);
        map.put(null, "4");

        Util.splitLine("traverseKey:");
        traverseIterator(map.keySet().iterator());
        Util.splitLine("traverseValue:");
        traverseIterator(map.values().iterator());

        map.put(null, null);

        traverseIterator(map.keySet().iterator());
        traverseIterator(map.values().iterator());

        Util.splitLine();
        MapMain tm = new MapMain();
        tm.initHashMap();
    }

    /**
     * HashMap进行resize时的空间确定，是以最近2的N次方确定重设容量的。
     *
     * @param cap
     * @return
     */
    private int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    private void initHashMap() {
        /*for (int i = 0; i < 30; i++) {
            System.out.println(tableSizeFor((1 << i)));
        }*/
//        推荐容量为2 ^ n, table[]连续空间是以threshold阀值的2 ^ n去申请空间的。非常可能导致空间浪费
        HashMap<Integer, Object> map = new HashMap<Integer, Object>(10);// 最后容量设置为16.
        System.out.println(map.size());
        for (int i = 0; i < 18; i++) {
            map.put(i, "i-" + i);
        }
        System.out.println(map.size());
        System.out.println(map.get(3));

        for (int i = 0; i < 3; i++) {
            map.put(3, "123");
            map.put(null, "123");
        }
        System.out.println("null:" + map.get(null));
        map.put(null, null);
        System.out.println(map.size());
        System.out.println(map.get(3));
        System.out.println("null:" + map.get(null));
    }
}
