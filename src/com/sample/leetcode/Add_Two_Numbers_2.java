package com.sample.leetcode;

import com.sample.leetcode.bean.ListNode;

import static com.sample.leetcode.CommonUtil.println;

/**
 * Created by jiek on 2020/8/19.
 * <p>
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * <p>
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * <p>
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * 示例：
 * <p>
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 * <p>
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 */
public class Add_Two_Numbers_2 {

    public static void main(String[] args) {
        solve(new int[]{2, 4, 3}, new int[]{5, 6, 4});
        solve(new int[]{1, 5, 5}, new int[]{5, 6, 6});
        solve(new int[]{1, 8}, new int[]{0});

    }

    static void solve(int[] pair_a, int[] pair_b) {
        ListNode l1 = CommonUtil.getListNode(pair_a);
        ListNode l2 = CommonUtil.getListNode(pair_b);
        ListNode result = new Solution().addTwoNumbers(l1, l2);
        println(result);
    }

    static class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode root = new ListNode(0);
            ListNode cursor = root;
            int carry_over = 0;
            while (l1 != null || l2 != null || carry_over != 0) {
                int l1Val = l1 != null ? l1.val : 0;
                int l2Val = l2 != null ? l2.val : 0;
                int sumVal = l1Val + l2Val + carry_over;
                carry_over = sumVal / 10;

                ListNode sumNode = new ListNode(sumVal % 10);
                cursor.next = sumNode;
                cursor = sumNode;

                if (l1 != null) l1 = l1.next;
                if (l2 != null) l2 = l2.next;
            }
            return root.next;
        }
    }
}
