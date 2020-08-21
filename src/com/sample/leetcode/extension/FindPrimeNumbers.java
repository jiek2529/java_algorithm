package com.sample.leetcode.extension;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiek on 2020/8/22.
 * <p>
 * 筛法求素数
 * <p>
 * 可按范围，3-10查素数级：结果 [3,5,7]
 * <p>
 * 方式一： findPrimeNumbers_int int[]数组存数据
 * 方式二： findPrimeNumbers_bit 进阶版，bit 位存数据，节省32倍额外空间
 */
public class FindPrimeNumbers {
    public static void main(String[] args) {
        int minVal = 3, maxVal = 1_000_0;

        FindPrimeNumbers fp = new FindPrimeNumbers();
        for (int i = 0; i < 5; i++) {
            long start;

            start = System.currentTimeMillis();
            int[] intArray = fp.findPrimeNumbers_bit(minVal, maxVal);
//            System.out.println(Arrays.toString(intArray));
            System.out.println("bit findPrimeNumbers_bit used time: " + (System.currentTimeMillis() - start));

            start = System.currentTimeMillis();
            int[] bitArray = fp.findPrimeNumbers_int(minVal, maxVal);
//            System.out.println(Arrays.toString(bitArray));
            System.out.println("int findPrimeNumbers_int used time: " + (System.currentTimeMillis() - start));

            System.out.println("\n");
        }
    }

    //    使用 bit 位存数据值空间节省32倍，同一级别
    public int[] findPrimeNumbers_bit(int minVal, int maxVal) {
        int tmp = maxVal + 1;

//        按位存素数状态
        int lens = tmp / 8 + 1;
        byte[] arr = new byte[lens];

        int i;
        if (minVal <= 2) {
            i = 3;
            setPrimeNumber(arr, 2, true);
        } else {
            if (minVal % 2 == 1) {
                i = minVal;
            } else {
                i = minVal + 1;
            }
        }
        while (i <= maxVal) {
            setPrimeNumber(arr, i, true);
            i += 2;
        }

        i = 3;
        int index = 3;
        while (i <= maxVal) {
            index = 3;
            while (i * index < maxVal) {
                setPrimeNumber(arr, i * index, false);
                index += 2;
            }
            i += 2;
//            2步一找的下一位是质数时，再执行外围的 while
            while (!getBitState(arr, i) && i < maxVal) {
                i += 2;//两步一前进
            }
        }

        List<Integer> list = new ArrayList<>(16);
        if (minVal <= 2) {
            i = 3;
            list.add(2);
        } else {
            if (minVal % 2 == 1) {
                i = minVal;
            } else {
                i = minVal + 1;
            }
        }
        while (i <= maxVal) {
            if (!getBitState(arr, i)) {
                i += 2;
                continue;
            }
            list.add(i);
            i += 2;
        }
        System.out.println("bit primeNumber list lens: " + list.size());
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }

    //取 index 位是否有1
    private boolean getBitState(byte[] arr, int index) {
        return (arr[index / 8] & (1 << (index % 8))) > 0;
    }

    //    使用 int 数组存数据值空间节省32倍，同一级别
    public int[] findPrimeNumbers_int(int minVal, int maxVal) {
        int lens = 1, tmp = maxVal + 1;
        while ((tmp >> 1) > 0) {
            tmp = tmp >> 1;
            lens++;
        }
//        按位存素数状态
        int[] arr = new int[1 << lens];

        int i = 3;
        if (minVal <= 2) {
            i = 3;
            arr[2] = 2;
        } else {
            if (minVal % 2 == 1) {
                i = minVal;
            } else {
                i = minVal + 1;
            }
        }
        while (i <= maxVal) {
            arr[i] = i;
            i += 2;
        }

        i = 3;
        int index = 3;
        while (i <= maxVal) {
            index = 3;
            while (i * index < maxVal) {
                arr[i * index] = 0;
                index += 2;
            }
            i += 2;
            while (arr[i] == 0 && i < maxVal) {
                i += 2;//两步一前进
            }
        }

        List<Integer> list = new ArrayList<>(16);
        if (minVal <= 2) {
            i = 3;
            list.add(2);
        } else {
            if (minVal % 2 == 1) {
                i = minVal;
            } else {
                i = minVal + 1;
            }
        }
        while (i <= maxVal) {
            if (arr[i] == 0) {
                i += 2;
                continue;
            }
            list.add(i);
            i += 2;
        }
        System.out.println("int primeNumber list lens: " + list.size());
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }

    /**
     * 低位为小位，如：0000 00001 表示第一位为素数
     *
     * @param arr
     * @param index bit位
     * @param flag  true: 设值 false: 删值
     */
    private void setPrimeNumber(byte[] arr, int index, boolean flag) {
        if (flag) {
            arr[index / 8] = (byte) (arr[index / 8] | (1 << (index % 8)));
        } else {
            arr[index / 8] = (byte) (arr[index / 8] & (0xff ^ (1 << (index % 8))));
        }
    }
}
