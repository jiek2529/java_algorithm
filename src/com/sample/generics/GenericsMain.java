package com.sample.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiek on 2020/5/26.
 * 验证泛型
 * <p>
 * JDK1.5中引入的新特性，本质是参数化类型； 可用于类、接口、方法中
 * <p>
 * 作用：泛型化、类型安全、消除类型强制转换、向后兼容
 * <p>
 * 优点：类型安全、消除类型强制转换、更高运行效率、潜在的性能收益
 */
public class GenericsMain {
    public static void main(String[] args) {
        testArray();

        testList();

        testGenericsList();
    }

    /**
     * 测试 List
     */
    private static void testList() {
        splitLine("测试数组列表");
        List<Animal> list = new ArrayList<>();
        list.add(new Dog());
        list.add(new Cat());
        list.add(new Dog());
        takeAnimals(list);
    }

    /**
     * 测试泛型
     */
    private static void testGenericsList() {
        splitLine("测试泛型列表");
        List<Dog> list = new ArrayList<>();
        list.add(new Dog());
        list.add(new Dog());
        /*list.add(new Cat());
        list.add(new Animal() {
            @Override
            public void eat() {
                System.out.println("anonymous Animals eat everythings");
            }
        });*/
        takeAnimals(list);
    }

    /*private static void takeAnimals(List<Animal> list) {//参数必须为 Animal对象，防止子类对象混入
        for (Animal animal : list) {
            animal.eat();
        }
    }*/

    /*private static void takeAnimals(List<? extends Animal> list) {//泛型参数可为 Animal 及其子类类型
        for (Animal animal : list) {
            animal.eat();
        }
    }*/

    private static <T extends Animal> void takeAnimals(List<T> list) {//泛型参数, 另一种书写方式，与上方作用原理一致
        for (Animal animal : list) {
            animal.eat();
        }
    }

    /**
     * 测试数组方式，无泛型
     */

    private static void testArray() {
        splitLine("测试数组");
        Animal[] animals = {new Dog(), new Cat(), new Dog()};
        Dog[] dogs = {new Dog(), new Dog(), new Dog()};

        takeAnimals(animals);
        takeAnimals(dogs);
    }

    private static void takeAnimals(Animal[] animals) {
        for (Animal animal : animals) {
            animal.eat();
        }
    }

    private static void splitLine(String msg) {
        System.out.println("\n -----  " + msg + "  -----");
    }
}
