package com.sample.interview;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by jiek on 2020/8/19.
 * <p>
 * 二叉树遍历算法
 */
public class BinaryTreeTraversal {

    public static void main(String[] args) {
        System.out.println("二叉树的广度遍历算法");
        int l = 1, r = 2;

//        模拟起始数
        int start = 0;
//        模拟树节点总个数
        int lens = 10;
//        模拟出好叉数
        TreeNode root = buildTree(start, lens);

//        广度优先算法遍历二叉树
        tree_bfs(root, lens);

//        深度优先算法遍历二叉树
        tree_dfs(root);
    }

    /**
     * 树的深度度遍历 deepth first search
     * <p>
     * 使用堆结构缓存数据，特点：后进先出，LIFO
     *
     * @param node
     */
    private static void tree_dfs(TreeNode node) {
        Stack<TreeNode> stack = new Stack();
        stack.push(node);
        TreeNode currentNode = node;
        stack.push(currentNode);
        while (stack.peek() != null) {
            stack.pop();
            if (currentNode.leftChild != null) {
                stack.push(currentNode.leftChild);
            }
            if (currentNode.rightChild != null) {
                stack.push(currentNode.rightChild);
            }
        }
    }

    /**
     * 树的广(宽)度遍历 breath first search
     * <p>
     * 使用队列结构缓存数据，特点：先进先出，FIFO
     *
     * @param node
     * @param lens
     */
    private static void tree_bfs(TreeNode node, int lens) {
        Queue<TreeNode> queue = new ArrayBlockingQueue(lens);

        queue.add(node);
        while (queue.peek() != null) {
            TreeNode curr = queue.poll();
            if (curr.leftChild != null) {
                queue.add(curr.leftChild);
            }
            if (curr.rightChild != null) {
                queue.add(curr.rightChild);
            }
            System.out.println(curr.val);
        }
    }

    /**
     * 从 start 连续产生 lens 个节点的二叉树（堆树结构）
     *
     * @param start 起始数
     * @param lens  总个数
     * @return root 返回二叉树根节点
     */
    private static TreeNode buildTree(int start, int lens) {
        if (lens < 0) return null;
        TreeNode root = null;
        TreeNode[] array = new TreeNode[lens];//临时数组，方便查找父节点用
        if (lens > 0) {
            root = new TreeNode(start);
            array[0] = root;
        }
        for (int i = 1; i < lens; i++) {
            array[i] = new TreeNode(start + i);
            int parentIndex = (i - (i % 2 > 0 ? 1 : 0)) >> 1;//找父节点下标
            if (i % 2 == 0) {
                array[parentIndex].leftChild = array[i];
            } else {
                array[parentIndex].rightChild = array[i];
            }
        }
        array = null;
        return root;
    }

    static class TreeNode {
        int val;
        TreeNode //parent,
                leftChild, rightChild;

        public TreeNode(int val) {
            this.val = val;
        }
    }
}
