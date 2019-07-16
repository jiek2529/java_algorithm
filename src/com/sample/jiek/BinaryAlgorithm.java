package com.sample.jiek;

import java.util.Arrays;

/**
 * Created by gaopj on 2019/7/16.
 */
public class BinaryAlgorithm {
    public static void main(String[] args) {
        int base = 1;
        int[] intList;
        int capacity = 6;
        intList = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            intList[i] = base << i;
        }

        System.out.println(Arrays.toString(intList));

        for (int i = 0; i < intList.length; i++) {
            System.out.println(intList[i] + "\t\t" + Integer.toBinaryString(intList[i]) + "\t\t" + Integer.toOctalString(intList[i]) + "\t\t" + Integer.toHexString(intList[i]));
        }

        System.out.println("位与(&) : 1 & 101 = 1       位同1 -> 1" +
                "\n" + "位或(|) : 11 & 101 = 111    位有1 -> 1" +
                "\n" + "位异或(^) : 1100 ^ 1001 = 101 位不同 -> 1" +
                "\n" + "位非(~) : ~100 = 11111111111111111111111111111011   位取反");
//        System.out.println("位与& : 1 & 100 = "+Integer.toBinaryString(intList[0] & intList[2]));// 1 & 100 = 0
//        System.out.println("位或| : 1 | 1000 | 100 = "+Integer.toBinaryString(intList[0] | intList[3] | intList[2]));// 1 | 1000 | 100 = 1101
//        System.out.println("位异或^ : 1 | 1000 ^ 1000 | 100 = "+Integer.toBinaryString((intList[0] | intList[3] ^ (intList[3]) | intList[2])));//1 | 1000 ^ 1000 | 100 = 1001 ^ 1000 | 100 = 1 | 100 = 101
//        System.out.println("位非~ : ~100 = "+(~intList[2])+"\t=\t"+Integer.toBinaryString(~intList[2]));
        System.out.println("--------------");


        int tmp = (intList[1] | intList[2] | intList[5]) & ~intList[2];
        System.out.println(tmp + " = "+Integer.toBinaryString(tmp));
        for (int i = 0; i < capacity; i++) {
            int i1 = tmp & intList[i];
            if (i1 > 0) {
                System.out.println(i + " : " + i1);
            }
        }
        System.out.println( "101 二进制车十进制数据："+ Integer.valueOf("101", 2));
    }
}
