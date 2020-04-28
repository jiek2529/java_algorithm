package com.sample.jiek;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * 单例方式：懒汉式（加重检查加锁双判空）、饿汉式（静态成员）、静态内部类实现单例模式、枚举类等
 * <p>
 * 防反射、防克隆、防序列化
 * <p>
 * Created by jiek on 2020/4/15.
 */
public class SingletonTest {

    public static void main(String[] args) throws Exception {

        System.out.println("-----------序列化----------------------");
        Singleton originSingleton = Singleton.getInstance();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(Singleton.getInstance());
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Singleton serializeSingleton = (Singleton) ois.readObject();
        System.out.println(originSingleton == serializeSingleton);//false

        System.out.println("-----------反射----------------------");
        //通过反射获取
        Constructor<Singleton> cons = Singleton.class.getDeclaredConstructor();
        cons.setAccessible(true);
        Singleton reflextSingleton = cons.newInstance();
        System.out.println(reflextSingleton == originSingleton);//false

        System.out.println("---------------------------克隆----------------------");

        Singleton cloneSingleton = (Singleton) originSingleton.clone();
        System.out.println(cloneSingleton == originSingleton);//false

    }

    private static class Singleton implements Serializable, Cloneable {

        private static volatile boolean isCreate = false;//默认是第一次创建
        //2.本类内部创建对象实例
        private static volatile Singleton instance;


        /**
         * 1.构造方法私有化，外部不能new
         */
        private Singleton() {
            if (isCreate) {
                throw new RuntimeException("已然被实例化一次，不能在实例化");
            }
            isCreate = true;
        }

        //3.提供一个公有的静态方法，返回实例对象
        public static Singleton getInstance() {
            if (instance == null) {
                synchronized (Singleton.class) {
                    if (instance == null) {
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return instance;
        }

        /**
         * 防止序列化破环
         *
         * @return
         */
        private Object readResolve() {
            return instance;
        }
    }
}
