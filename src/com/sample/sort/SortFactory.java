package com.sample.sort;

/**
 * Created by jiek on 2020/8/17.
 */
public class SortFactory {

    public ISort getSort(Class clazz) {
        if (ISort.class.isAssignableFrom(clazz)) {
            try {
                return (ISort) clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
