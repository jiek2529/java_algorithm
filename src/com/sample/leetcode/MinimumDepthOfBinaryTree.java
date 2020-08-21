package com.sample.leetcode;

import com.sample.leetcode.bean.TreeNode;

/**
 * Created by jiek on 2020/8/21.
 * 二叉树的最小深度
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
//        solve(CommonUtil.getTreeNode(new Integer[]{3, 9, 20, null, null, 15, 7}, 0));//3
//        solve(CommonUtil.getTreeNode(new Integer[]{0, 1, null, null, 2}, 0));//3
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
//            当前root 为根时，叶节点最小深度。取小值，所以默认要用大值去判断
            int min_depth = Integer.MAX_VALUE;
            if (root.left != null) {
                min_depth = Math.min(minDepth(root.left), min_depth);
            }
            if (root.right != null) {
                min_depth = Math.min(minDepth(root.right), min_depth);
            }
//            子树的叶最小深度加自己一层
            return min_depth + 1;
        }
    }
}
