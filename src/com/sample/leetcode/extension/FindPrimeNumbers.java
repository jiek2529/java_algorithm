package com.sample.leetcode.extension;

import com.sample.leetcode.bean.ListNode;

import java.util.*;

/**
 * Created by jiek on 2020/8/22.
 * <p>
 * 筛法求质数
 * <p>
 * 可按范围，3-10查质数级：结果 [3,5,7]
 * <p>
 * 方式一： findPrimeNumbers_int int[]数组存数据
 * 方式二： findPrimeNumbers_bit 进阶版，bit 位存数据，节省32倍额外空间
 */
public class FindPrimeNumbers {
    //是否获取范围内每个数的最小公约数集，即产生Map<Integer, PNNode>
    private boolean fetchNumberPMHM = false;
    /**
     * key: 当前值
     * val：PNNode key 的所有最大公约数集
     */
    private Map<Integer, PNNode> map = null;

    /**
     * 查询范围内的所有质数集
     * findPrimeNumbers_bit(minVal, maxVal)： 获取范围内质数数组
     */
    public FindPrimeNumbers() {
    }

    /**
     * 是否计算每个数的最小公约数的质数集
     * <p>
     * findPrimeNumbers_bit(minVal, maxVal)： 获取范围内质数数组
     * getPrimeNumbersHM(): HashMap 范围内每个数的最小公约数的质数单链表
     *
     * @param fetchNumberPMHM
     */
    public FindPrimeNumbers(boolean fetchNumberPMHM) {
        this.fetchNumberPMHM = fetchNumberPMHM;
    }

    public static void main(String[] args) {
        int minVal = 7, maxVal = 18;

        FindPrimeNumbers fp = new FindPrimeNumbers(true);
        Map map = null;
        for (int i = 0; i < 1; i++) {
            long start;

            start = System.currentTimeMillis();
            int[] intArray = fp.findPrimeNumbers_bit(minVal, maxVal);
            map = fp.getPrimeNumbersHM();

            if (intArray.length < 32) {
                System.out.println(Arrays.toString(intArray));
            }
            System.out.println("bit findPrimeNumbers_bit used time: " + (System.currentTimeMillis() - start));

//            start = System.currentTimeMillis();
//            int[] bitArray = fp.findPrimeNumbers_int(minVal, maxVal);
//            if (bitArray.length < 32) {
//                System.out.println(Arrays.toString(bitArray));
//            }
//            System.out.println("int findPrimeNumbers_int used time: " + (System.currentTimeMillis() - start));

            System.out.println("\n");
        }
    }

    //    使用 bit 位存数据值空间节省32倍，同一级别
    public int[] findPrimeNumbers_bit(int minVal, int maxVal) {
        if (maxVal > (1 << 20)) {
            throw new IllegalArgumentException("maxVal is too large(max=1048576): " + maxVal);
        }
        initMap(minVal, maxVal);
        int tmp = maxVal + 1;

//        按位存质数状态
        int lens = tmp / 8 + 1;
        byte[] arr = new byte[lens];

        int i;
        if (minVal <= 2) {
            i = 3;
            setPrimeNumber(arr, 2, 2, true);
        } else {
            if (minVal % 2 == 1) {
                i = minVal;
            } else {
                i = minVal + 1;
            }
        }
        {//第一遍循环，对所有偶数设置2为最小公约数，对所有奇数不设置最小公约数
            //将所有奇数存入数组
            while (i < maxVal) {
                setPrimeNumber(arr, i, 0, true);//奇数，待定设置最小公约数
                if (fetchNumberPMHM) {
                    setPrimeNumber(arr, i + 1, 2, false);//偶数
                }
                i += 2;
            }
            if (i == maxVal) {
                setPrimeNumber(arr, i, 0, true);//边界奇数，待定设置最小公约数
            }
        }

        pm_multipleCheck(arr, maxVal);

//        if (fetchNumberPMHM) {
//            i = minVal;
//            while (i <= maxVal) {
//                map.get(i).clean();
//            }
//        }

        return getListPMFromArr_bits(minVal, maxVal, arr);
    }

    /**
     * 排除质数的 N 倍为质数
     * <p>
     * 将3开始的所有质数的倍数的最小公约质数添加此质数
     * <p>
     * 如：3、6、9 添加3
     * 如：5、10、15 添加5
     * 如：7，14，21 添加7
     *
     * @param arr
     * @param maxVal
     */
    private void pm_multipleCheck(byte[] arr, int maxVal) {
//        第一个质数开始
        int firstPM = 3;
//        倍数
        int multiple;
        while (firstPM <= maxVal) {
            //下一个质数 如3 5 7 11...
            multiple = fetchNumberPMHM ? 1 : 3;
            while (firstPM * multiple <= maxVal) {
//                multiple=1表示当前数为质数
                if (multiple == 1) {//质数本身
                    setPrimeNumber(arr, firstPM, firstPM, true);//质数
                } else {
                    setPrimeNumber(arr, firstPM * multiple, firstPM, false);//质数的倍数
                }
                multiple += fetchNumberPMHM ? 1 : 2;
            }

            //奇数向后二步快步，找下一个范围内的质数
            firstPM += 2;
//            2步一找的下一位是质数时，再执行外围的 while
            while (!getBitState(arr, firstPM) && firstPM < maxVal) {
                firstPM += 2;//两步一前进
            }
        }
    }

    //        所有质数已经筛出，以下为瓶装成所需要的格式
    private int[] getListPMFromArr_bits(int minVal, int maxVal, byte[] arr) {
        int i;
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
//        按位存质数状态
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
     * 设置 index 是可能质数或不是质数
     * 低位为小位，如：0000 00001 表示第0位为质数 0011 0100 表示第2，3，5位和为质数
     * <p>
     * 设置 index 对应二进制位设1或0，表示不是该数是不是质数
     *
     * @param arr
     * @param index bit位
     * @param maxPN 当前数的最大公约质数 ，0表示不设置公约数，未知最小公约数
     * @param flag  true: 设值(可能为质数） false: 删值(一定不为质数)
     */
    private void setPrimeNumber(byte[] arr, int index, int maxPN, boolean flag) {
        if (flag) {
            //index 可能为质数，但一定是奇数
            arr[index / 8] = (byte) (arr[index / 8] | (1 << (index % 8)));

            if (fetchNumberPMHM) {
                getPNNode(index).add(maxPN);

//                if (index == maxPN) {
//                    getPNNode(index).clean();//素数
//                }
            }
        } else {
            //index 一定不为质数，但不一定是偶数
            arr[index / 8] = (byte) (arr[index / 8] & (0xff ^ (1 << (index % 8))));

            if (fetchNumberPMHM && maxPN > 0) {
                getPNNode(index).add(maxPN);
//
            }
        }
    }

    private PNNode getPNNode(int index) {
        if (fetchNumberPMHM && map.get(index) == null) {
            map.put(index, new PNNode(index));
        }
        return map.get(index);
    }

    /**
     * 查询范围内的最小至最大值范围内的最小公约数集
     *
     * @return
     */
    public Map<Integer, PNNode> getPrimeNumbersHM() {
        return map;
    }

    private void initMap(int minVal, int maxVal) {
        if (fetchNumberPMHM) {
            int lens = 1;
            if (minVal < maxVal) {
                maxVal /= 2;
                while (maxVal > 0) {
                    lens++;
                    maxVal /= 2;
                }
            }
            map = new HashMap<>(1 << lens);
            System.out.println("map capacity: " + (1 << lens));
        }
    }

    /**
     * 数的最小公约质数器
     */
    public static class PNNode {
        public int val;
        //最大公约质数个数
        public int common_divisor_lens = 0;
        public int[] common_divisors;

        //第一个公约质数
        ListNode first = null;
        //最后一个公约质数
        ListNode last = null;

        public PNNode(int val) {
            this.val = val;
        }

        public void add(int primeNumber) {
            if (primeNumber == 0) {
                return;
            }
            if (first == null) {
                first = new ListNode(primeNumber);
                last = first;
            } else {
                last.next = new ListNode(primeNumber);
                last = last.next;
            }

            common_divisor_lens++;
        }

        //        移除链表，转为数组
        public void clean() {
            if (first == null) {
                return;
            }
            common_divisors = new int[common_divisor_lens];
            for (int i = 0; i < common_divisor_lens; i++) {
                common_divisors[i] = first.val;
                first = first.next;
            }
            first = null;
            last = null;
        }
    }
}
