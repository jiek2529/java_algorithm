package com.sample.interview;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiek on 2020/8/14.
 * 两条无环单链求交集
 * <p>
 * 分析，单链，只知子不知父的单链结构，每个节点只能至多有一个子节点。
 * <p>
 * 如果两个无环单链有交集，一定从交集口至链尾是相同的；有交集时为单头双尾结构，或两链相同。
 * 所以需要将 A链头接到B 链尾上；
 * 根据快慢步长进行判交来判断此时是否有环；
 * 有环表示此两链表有交集。无环则表示无交集。
 */
public class SingleLinkIntersection {
    static Map<Integer, Node> map = new HashMap<>();

    public static void main(String[] args) {

        int list_a[] = {1, 3, 5, 7, 9};
        int list_b[] = {3, 5, 7, 9};//有交集的两单链，尾部一定相同，此处不做校验

        Node node_A = list2Node(list_a);
        Node node_B = list2Node(list_b);
        intersection(node_A, node_B);
    }

    /**
     * 求两单链表的交集
     *
     * @param node_a_head
     * @param node_b_head
     */
    private static void intersection(Node node_a_head, Node node_b_head) {
        if (checkHasRange(node_a_head) != null) {
            System.out.println("node_a 有环：");
            return;
        }
        if (checkHasRange(node_b_head) != null) {
            System.out.println("node_b 有环：");
            return;
        }

        //找到 A 链链尾
        Node a_last = node_a_head;
        //当a b 都无环时，将两单链表合为一个链，如果有环就表示两单链有交集
        while (a_last.next != null) {
            a_last = a_last.next;
        }

//        将 B 链接入 A 链中
        a_last.next = node_b_head;

//        快慢步的交叉节点
        Node junction_node = checkHasRange(node_a_head);
        if (junction_node != null) {
            System.out.println("新 A 链有环，说明两条单链有交集");
        } else {
            System.out.println("两条链无交集");
            return;
        }

        /**
         * 以下前提，一定有环。
         * 一边从头单步前进，另一边从环碰撞点单步前进，两方向前进的第一个碰撞点一定是环入口。
         */
        {
            Node curr = node_a_head;
            while (curr != null) {
                if (curr == junction_node) {
                    System.out.println("环入口：" + curr.obj);
                    break;
                }
                curr = curr.next;
                junction_node = junction_node.next;
            }
            //断链，恢复原来链表结构
            a_last.next = null;

            StringBuilder sb = new StringBuilder();
            //打印链表交集
            while (curr != null) {
                sb.append(curr.obj).append(" ");
                curr = curr.next;
            }
            System.out.print("两条链的交集：" + sb.toString());
        }

    }

    /**
     * 判断单链表是否有环
     *
     * @param node 检测是否有环的原链
     * @return 快慢步中环中相遇的节点，null表示无环，否则表示有环
     */
    private static Node checkHasRange(Node node) {
        if (node != null) {
            Node fastStep = node, slowStep = node;
            while (fastStep != null && fastStep.next != null) {
                fastStep = fastStep.next.next;//快，步长2
                slowStep = slowStep.next;//慢，步长1
                if (fastStep == slowStep)
                    return fastStep;
            }
        }
        return null;
    }

    private static Node list2Node(int[] list_b) {
        Node pre = null, root = null;
        for (int i : list_b) {
            if (pre != null) {
                Node next = getNodeCache(i);
                pre.setNext(next);
                pre = next;
            } else {
                root = getNodeCache(i);
                pre = root;
            }
        }
        return root;
    }

    //对同值建同一 Node
    private static Node getNodeCache(int i) {
        if (!map.containsKey(i)) {
            map.put(i, new Node(i));
        }
        return map.get(i);

//        return new Node(i);
    }

    //    单链表
    static class Node<T> {
        T obj;
        Node next;

        public Node(T obj) {
            this.obj = obj;
        }


        public void setNext(Node next) {
            this.next = next;
        }
    }


}
