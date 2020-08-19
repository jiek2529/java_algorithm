package com.sample.leetcode;

import com.sample.leetcode.bean.ListNode;

/**
 * Created by jiek on 2020/8/20.
 * <p>
 * 通用算法外的工具
 */
public class CommonUtil {
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

    static void println(ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val).append(" -> ");
            head = head.next;
        }
        System.out.println(sb.toString());
    }
}
