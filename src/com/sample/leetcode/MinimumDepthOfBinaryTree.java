package com.sample.leetcode;

import com.sample.leetcode.bean.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by jiek on 2020/8/21.
 * 二叉树的最小深度
 * <p>
 * 优化方案 BFS 算法，
 * 广度遍历先出队列的，一定是树层最少的，
 * 每层数据都带上它的层级数
 * 第一个出现无子节点的节点所包含的层数就是结果
 * <p>
 * 给定一个二叉树，找出其最小深度。
 * <p>
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 * <p>
 * 说明: 叶子节点是指没有子节点的节点。
 * <p>
 * 示例:
 * <p>
 * 给定二叉树 [3,9,20,null,null,15,7],
 * <p>
 * 链接：https://leetcode-cn.com/problems/minimum-depth-of-binary-tree
 */
public class MinimumDepthOfBinaryTree {
    public static void main(String[] args) {
        solve(CommonUtil.getTreeNode(new Integer[]{3, 9, 20, null, null, 15, 7}, 0));//2
        solve(CommonUtil.getTreeNode(new Integer[]{0, 1, null, null, 2}, 0));//3
        solve(CommonUtil.getTreeNode(new Integer[]{0, 1, 2, null, 3}, 0));//2
    }

    static int solve(TreeNode root) {
//        使用广度遍历方式找叶节点最小深度
        int minDepth = new Solution().minDepth(root);
        System.out.println(minDepth);
        return minDepth;
    }

    static class Solution {
        public int minDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            if (root.left == null && root.right == null) {
                return 1;
            }
            Queue<BFSTreeNode> queue = new LinkedList<>();
            queue.add(new BFSTreeNode(root, 1));
            while (!queue.isEmpty()) {
                BFSTreeNode bfsTreeNode = queue.poll();
                TreeNode currNode = bfsTreeNode.node;
                int currDepth = bfsTreeNode.depth;

                if (currNode.left == null && currNode.right == null) {
                    return currDepth;
                }

//                有子节点的节点就不会是结果，继续入队列
                if (currNode.left != null) {
                    queue.add(new BFSTreeNode(currNode.left, currDepth + 1));
                }
                if (currNode.right != null) {
                    queue.add(new BFSTreeNode(currNode.right, currDepth + 1));
                }
            }
            return 0;
        }
    }

    static class BFSTreeNode {
        TreeNode node;
        int depth;

        public BFSTreeNode(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
}
