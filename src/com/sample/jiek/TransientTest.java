package com.sample.jiek;

import java.io.*;

/**
 * Transient 关键字测试
 *
 * 不被序列化的字段
 *
 * @note 类必须实现 Serializable 接口
 *
 * Created by jiek on 2020/4/18.
 */
public class TransientTest implements Serializable {

    public String name;
    public transient String grade;
    public volatile String info;
    public static String remark = "static Remark field!";

    public TransientTest(String name, String grade, String info) {
        this.name = name;
        this.grade = grade;
        this.info = info;
    }

    @Override
    public String toString() {
        return "TransientTest{" +
                "name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    /**
     * 输出结果：
     *          序列化前：TransientTest{name='frank', grade='three', info='i'm frank.'}
     *          序列化后：TransientTest{name='frank', grade='null', info='i'm frank.'}
     * @param args
     */
    public static void main(String[] args) {
        TransientTest test = new TransientTest("frank", "three", "i'm frank.");
        System.out.println("序列化前："+test);
        ObjectOutputStream outStream;
        ObjectInputStream inStream;
        String filePath = "./TransientTest.obj";
        try {
            outStream = new ObjectOutputStream(new FileOutputStream(filePath));
            outStream.writeObject(test);

            inStream = new ObjectInputStream(new FileInputStream(filePath));
            TransientTest readObject = (TransientTest)inStream.readObject();
            System.out.println("序列化后："+readObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
