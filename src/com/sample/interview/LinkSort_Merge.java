package com.sample.interview;

/**
 * Created by jiek on 2020/8/19.
 * <p>
 * 单链表排序 归并排序
 * <p>
 * 时间复杂度： O(n lg n)  空间复杂度： 常数级
 */
public class LinkSort_Merge extends LinkSort_Basic {

    public static void main(String[] args) {
        sort2Print(new int[]{4, 2, 1, 3});//        输出: 1->2->3->4
        sort2Print(new int[]{-1, 5, 3, 4, 0});//        输出: -1->0->3->4->5
        sort2Print(new int[]{1, 2, 3, 4});//        输出: 1->2->3->4
    }

    /**
     * 排序并打印
     *
     * @param arr
     */
    static void sort2Print(int[] arr) {
        LinkSort_Merge.Solution solution = new LinkSort_Merge.Solution();
        LinkSort_Merge.ListNode head = solution.sortList(getListNode(arr));
        p(head);
    }

    static class Solution {
        public ListNode sortList(ListNode head) {
            if (head == null) {
                return null;
            }
            if (head.next == null) {
                return head;
            }
            ListNode mid = getmid(head), second = null;
            second = mid.next;
            mid.next = null;//将中间的断链

            return merge(sortList(head), sortList(second));
        }

        private ListNode merge(ListNode first, ListNode second) {
            ListNode head = null, tail = null;

            if (first == null && second == null) {
                return null;
            } else if (first != null && second == null) {
                return first;
            } else if (first == null && second != null) {
                return second;
            } else if (first != null && second != null) {
                if (first.val < second.val) {
                    head = first;
                    first = first.next;
                } else {
                    head = second;
                    second = second.next;
                }
                tail = head;
            }

            if (first != null) {
                while (second != null) {
                    if (first == null) {
                        tail.next = second;
                        while (tail.next != null) {
                            tail = tail.next;
                        }
                        break;
                    }
                    if (first.val < second.val) {
                        tail.next = first;
                        first = first.next;
                    } else {
                        tail.next = second;
                        second = second.next;
                    }
                    tail = tail.next;
                }
//                while 中 first==null 或 second==null 或设置头时先用完了 second 都会过来。此时 first 不一定为空
                tail.next = first;
            } else {
                tail.next = second;
            }

            return head;
        }

        public ListNode getmid(ListNode head) {
            ListNode fast = head, slow = head;
            while (fast != null && fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }
            return slow;
        }
    }
}
