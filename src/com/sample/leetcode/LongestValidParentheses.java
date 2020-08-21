package com.sample.leetcode;

import com.sample.leetcode.bean.ListNode;

/**
 * Created by jiek on 2020/8/21.
 * 最长连续有效括号个数（双数）
 * 使用单链表实现，合法(=1,合法）=2，非常括号=0，再将2出现时，删除head 向后第一个1。
 * 统计：将连续的2相加，取最大数
 */
public class LongestValidParentheses {
    public static void main(String[] args) {
        solve("");
        solve("()(())");
        solve(")");
        solve("))");
        solve(")()");
        solve(")(()");
        solve(")()((((())())");
        solve(")()())()()(");
        solve(")(()()))");
        solve(")()())((()))()");
    }

    static void solve(String literal) {
        System.out.println("\noriginal literal: " + literal);

        int result = new Solution().longestValidParentheses(literal);

        System.out.println("longestPalindrome result: " + result);
    }

    static class Solution {

        public int longestValidParentheses(String literal) {
            int tmp = 0;
            int index = 0;
            int lens = literal.length();
            ListNode head = new ListNode(0);

            while (index < lens) {
                if (literal.charAt(index) == '(') {
                    tmp++;
                    head = addHead(head, 1);
                } else {
                    tmp--;
                    if (tmp < 0) {
                        tmp = 0;
                        head = addHead(head, 0);
                    } else {
                        head = addHead(head, 2);
                    }
                }
                index++;
            }

            int maxCount = 0;
            tmp = 0;
            while (head.next != null) {
                if (head.val == 2) {
                    tmp++;
                    if (tmp > maxCount) {
                        maxCount = tmp;
                    }
                } else {
                    tmp = 0;
                }
                head = head.next;

            }
            return maxCount * 2;
        }

        /**
         * 当 val = 2,删除 head后第一个出现的1节点。
         *
         * @param head
         * @param val
         * @return
         */
        private ListNode addHead(ListNode head, int val) {
            ListNode tail = head;
            head = new ListNode(val);
            head.next = tail;
            if (val == 2) {
                tail = head.next;

                if (tail.val == 1) {
                    head.next = head.next.next;
                } else {
                    while (true) {
                        if (tail.next.val == 1) {
                            tail.next = tail.next.next;
                            break;//删除一个第一次的1
                        }
                        tail = tail.next;
                    }
                }
            }
            return head;
        }
    }
}
