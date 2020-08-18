package com.sample.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by jiek on 2020/8/18.
 * <p>
 * 产生随机数组
 * <p>
 * int[] <--> Integer[] <--> List<Integer>
 * 相互转换详见
 * {@link Util#getArray(Integer[])}
 * {@link Util#getArray(List)}
 * {@link Util#getArrayBoxed(int[])}
 * {@link Util#getArrayBoxed(List)}
 * {@link Util#getList(int[])}
 * {@link Util#getList(Integer[])}
 */
public class MockArrayUtil {

    /**
     * 随机产生指定数量的随机数
     *
     * @param len
     * @return
     */
    public static int[] mock(int len) {
        return mock(len, 0, len);
    }

    public static int[] mock(int len, int min, int max) {
        if (len < 0 || min >= max) {
            throw new Error("产生随机参数错误");
        }

        Random r = new Random();
        if (len > 1 << 16) {
            len = 1 << 16;
        }

        int[] array = new int[len];
        for (int i = 0; i < array.length; i++) {
            array[i] = r.nextInt(array.length);
            if (array[i] < 0)
                System.out.println(array[i]);
        }
        return array;
    }

    public static void main(String[] args) {
        int[] array_1 = mock(1_000);

        Integer[] array_2 = Arrays.stream(array_1).boxed().toArray(Integer[]::new);

        List<Integer> list_1 = Arrays.stream(array_1).boxed().collect(Collectors.toList());

        int[] array_3 = Arrays.stream(array_2).mapToInt(Integer::valueOf).toArray();

        int[] array_4 = list_1.stream().mapToInt(Integer::valueOf).toArray();

        Integer[] array_5 = list_1.toArray(new Integer[0]);

        List<Integer> list_3 = Arrays.asList(array_5);

        System.out.println(array_1.length);
    }
}
