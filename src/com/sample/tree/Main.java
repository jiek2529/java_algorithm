package com.sample.tree;

/**
 * Created by jiek on 2020/4/28.
 */
public class Main {
    public static void main(String[] args) {
//        RedBlackTree<Integer, String> redBlackTree = new RedBlackTree<Integer, String>();
        MyTreeMap<Integer, String> redBlackTree = new MyTreeMap<>();
        int[] tmp = {27, 35, 40, 45, 48, 50, 56, 78, 90};
        for (int i : tmp) {
            redBlackTree.put(i, "v: " + i);
        }
        redBlackTree.printEntry(redBlackTree.root, 0);
    }
}
