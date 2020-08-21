package com.sample.leetcode;

import com.sample.leetcode.bean.ListNode;
import com.sample.leetcode.bean.TreeNode;

/**
 * Created by jiek on 2020/8/20.
 * <p>
 * 通用算法外的工具
 */
public class CommonUtil {
    /**
     * 模拟产生为单链表
     *
     * @param arr
     * @return
     */
    @Deprecated
    static ListNode getListNode(int[] arr) {
        ListNode head = null, tail = null;
        for (int i = 0; i < arr.length; i++) {
            if (head == null) {
                head = new ListNode(arr[i]);
                tail = head;
            } else {
                ListNode ln = new ListNode(arr[i]);
                tail.next = ln;
                tail = ln;

            }
        }
        return head;
    }

    /**
     * 调试通用打印单链表
     *
     * @param head
     */
    @Deprecated
    static void println(ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val).append(" -> ");
            head = head.next;
        }
        System.out.println(sb.toString());
    }

    /**
     * 模拟产生二叉树
     *
     * @param arr
     * @return
     */
    @Deprecated
    public static TreeNode getTreeNode(Integer[] arr, int pos) {
        if (pos >= arr.length || arr[pos] == null) return null;
        TreeNode parent = new TreeNode(arr[pos]);
        parent.left = getTreeNode(arr, pos * 2 + 1);//(pos << 1) + 1 位与一定要加括号
        parent.right = getTreeNode(arr, pos * 2 + 2);
        return parent;
    }
}
