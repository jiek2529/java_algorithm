package com.sample.util;


import java.util.HashMap;

/**
 * Created by jiek on 11/06/2017.
 */
public class TestMap {
    private int MAXIMUM_CAPACITY = 1 << 30;

    public static void main(String[] args) {
        TestMap tm = new TestMap();

        tm.initHashMap();
    }

    /**
     * HashMap进行resize时的空间确定，是以最近2的N次方确定重设容量的。
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

        HashMap<Integer, Object> map = new HashMap<Integer, Object>(10);
        System.out.println(map.size());
        for (int i = 0; i < 18; i++) {
            map.put(i, "i-"+i);
        }
        System.out.println(map.size());
        System.out.println(map.get(3));

        for (int i = 0; i < 3; i++) {
            map.put(3, "123");
            map.put(null, "123");
        }
        System.out.println("null:"+map.get(null));
        map.put(null, null);
        System.out.println(map.size());
        System.out.println(map.get(3));
        System.out.println("null:"+map.get(null));
    }
}
