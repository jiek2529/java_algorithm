package com.sample.jiek.searching;

/**
 * Created by jiek on 13/06/2017.
 * 顺序查找，找到返回TRUE，否则返回FALSE；
 */
public class OrderSearch implements Searchable {
    @Override
    public boolean searching(int[] list, int target) {
        return indexOf(list, target) >= 0;
    }

    private int indexOf(int[] list, int o) {
        for (int i = 0; i < list.length; i++)
            if (o == list[i])
                return i;
        return -1;
    }
}
