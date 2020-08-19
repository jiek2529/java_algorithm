package com.sample.interview;

/**
 * Created by jiek on 2020/8/19.
 * <p>
 * 单链表排序 快排
 * <p>
 * 时间复杂度： O(n lg n)  空间复杂度： 常数级
 */
public class LinkSort_Quick extends LinkSort_Basic {

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
        LinkSort_Quick.Solution solution = new LinkSort_Quick.Solution();
        ListNode head = solution.sortList(getListNode(arr));
        p(head);
    }


    static class Solution {

        public ListNode sortList(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            int lens = getLens(head);
            ListNode[] list = new ListNode[lens];
            for (int i = 0; i < lens; i++) {
                list[i] = head;
                head = head.next;
            }
            quickSort(list, 0, lens - 1);
            head = list[0];
            for (int i = 1; i < list.length; i++) {
                list[i - 1].next = list[i];
            }
            list[lens - 1].next = null;
            return head;
        }

        void swap(ListNode[] arr, int l, int r) {
            if (l == r) {
                return;
            }
            ListNode temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
        }

        void quickSort(ListNode[] arr, int left, int right) {
            if (left < right) {
                int pivot = partition(arr, left, right);
                quickSort(arr, left, pivot - 1);
                quickSort(arr, pivot + 1, right);
            }
        }

        int partition(ListNode[] arr, int left, int right) {
            ListNode temp = arr[left];
            int index = left + 1;
            for (int i = index; i <= right; i++) {
                try {
                    if (arr[i].val < temp.val) {
                        swap(arr, index, i);
                        index++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            index--;
            swap(arr, left, index);
            return index;
        }

        public int getLens(ListNode head) {
            int count = 0;
            if (head != null) count = 1;
            while (head.next != null) {
                head = head.next;
                count++;
            }
            return count;
        }
    }
}
