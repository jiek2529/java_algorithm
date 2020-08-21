package com.sample.leetcode;

import com.sample.leetcode.bean.ListNode;

import java.util.Stack;

/**
 * Created by jiek on 2020/8/21.
 * 一趟扫描，删除链表的倒数第N个节点
 * <p>
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 * 前提确保 n 不越界。
 * <p>
 * 示例：
 * <p>
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 * <p>
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * <p>
 * 你能尝试使用一趟扫描实现吗？
 * <p>
 * 链接：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list
 */
public class RemoveNthNodeRromEndOfList {

    public static void main(String[] args) {
        solve(CommonUtil.getListNode(new int[]{1, 2, 3, 4, 5}), 1);
        solve(CommonUtil.getListNode(new int[]{1, 2, 3, 4, 5}), 5);
        solve(CommonUtil.getListNode(new int[]{1, 2, 3, 4, 5}), 3);
    }

    /**
     * 删除单链表倒数第 n 个元素
     *
     * @param head 单链表头
     * @param n    倒数位数
     * @return
     */
    static ListNode solve(ListNode head, int n) {
        CommonUtil.println(head);

        head = new Solution().removeNthFromEnd(head, n);

        CommonUtil.println(head);

        return head;
    }

    static class Solution {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            Stack<ListNode> stack = new Stack<>();
            ListNode tail = head;
            while (tail != null) {
                stack.push(tail);
                tail = tail.next;
            }
            int tmp = 0;
            while (stack.peek() != null) {
                tail = stack.pop();
                tmp++;
                if (n == tmp) {
                    if (stack.empty()) {//删除头节点
                        return tail.next;
                    } else if (tmp == 1) {//删除最后一个节点
                        tail = stack.pop();
                        tail.next = null;
                    } else {
                        tail = stack.pop();
                        tail.next = tail.next.next;
                    }
                    break;
                }
            }

            return head;
        }
    }
}
