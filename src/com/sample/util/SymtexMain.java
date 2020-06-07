package com.sample.util;

import java.io.*;

/**
 * Created by jiek on 2020/6/8.
 * <p>
 * 验证一些语法功能
 * 1. switch string 是以 hashcode 进行判断，应排查null的 case，
 * 2. try-with-resourse try()语法糖，自动完成 finally 的 close 任务
 * </p>
 */
public class SymtexMain {
    public static void main(String[] args) {
        try {
            testSwitchStringNull(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 先防空处理， throws NPE,switch行
     *
     * @param param
     */
    private static void testSwitchStringNull(String param) {
        switch (param) {//使用的是 hashcode 来比较匹配的
// 肯定不是进入这里
            case "sth":
                System.out.println("it's sth");
                break;
// 也不是进入这里
            case "null":
                System.out.println("it's null");
                break;
// 也不是进入这里
            default:
                System.out.println("default");
        }
    }

    /**
     * 验证 java7支持的 try()语法糖 autocloseable
     */
    private static void copy(String src, String dst) {
        try (InputStream in = new FileInputStream(src);//此 java7语法糖，能运行生成需 finally中手动close() 的代码 try-with-resourse
             OutputStream out = new FileOutputStream(dst)) {
            byte[] buff = new byte[1024];
            int n;
            while ((n = in.read(buff)) >= 0) {
                out.write(buff, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
