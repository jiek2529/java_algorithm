package com.sample.interview;

/**
 * Created by jiek on 2020/8/19.
 * <p>
 * 链表的排序基类
 *
 * 链表排序的自写方案 https://leetcode-cn.com/problems/sort-list/
 * 1. Merge 归并排序 3ms
 * 2. LinkSortQuick 快速排序 330ms
 */
public abstract class LinkSort_Basic {

    static void p(LinkSort_Merge.ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val).append(" -> ");
            head = head.next;
        }
        System.out.println(sb.toString());
    }

    static LinkSort_Merge.ListNode getListNode(int[] arr) {
        LinkSort_Merge.ListNode head = null, tail = null;
        for (int i = 0; i < arr.length; i++) {
            if (head == null) {
                head = new LinkSort_Merge.ListNode(arr[i]);
                tail = head;
            } else {
                LinkSort_Merge.ListNode ln = new LinkSort_Merge.ListNode(arr[i]);
                tail.next = ln;
                tail = ln;

            }
        }
        return head;
    }

    static class ListNode {
        int val;
        LinkSort_Merge.ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
