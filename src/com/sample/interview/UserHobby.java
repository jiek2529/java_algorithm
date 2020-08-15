package com.sample.interview;

import java.util.HashMap;

/**
 * Created by jiek on 2020/8/15.
 * <p>
 * 用户喜好
 * <p>
 * 为了不断优化推荐效果，今日头条每天要存储和处理海量数据。假设有这样一种场景：我们对用户按照它们的注册时间先后来标号，
 * 对于一类文章，每个用户都有不同的喜好值，我们会想知道某一段时间内注册的用户（标号相连的一批用户）中，有多少用户对这类文章喜好值为k。
 * 因为一些特殊的原因，不会出现一个查询的用户区间完全覆盖另一个查询的用户区间(不存在L1<=L2<=R2<=R1)。
 * <p>
 * 时间复杂度：O(N), 空间复杂度：O(N)
 * <p>
 * 原理：使用 HashMap 和单链表 存储；以喜好值为HashMap键，以用户 Node 为值，将喜好值相同的用户，串在一起成单链表 Node。
 *      查询满足喜好为 k 的用户时，只需查询 HashMap 中 k 的 Node 链中，满足序号范围的用户进行统计。
 */
public class UserHobby {

    /**
     * 将喜好值相同的人，链式管理起来
     * <p>
     * key: 喜好值
     * value: Node节点【 index为用户序号，从1开始】
     */
    public static HashMap<Integer, Node> hashMap = new HashMap<>();

    public static void main(String[] args) {
//       连续的用户，对同一文章的喜好值数组
        int hobby[];
//        共 N 组查询，每组查询从l 至 r 的 喜好值为 k 的用户个数
        int q_list[][];

        {//模拟数字输入
//            值含义：第1个用户 k=1, 第2个用户k=2,第3个用户 k=5, 第4个用户 k=3,第5个用户 k=5...
            hobby = new int[]{1, 2, 5, 3, 5, 3};

//            第一组：查询用户序号[1-2]的 k=1的用户数量；查询用户序号[2-4]的 k=3的用户数量；查询用户序号[1-6]的 k=5的用户数量；
            q_list = new int[][]{{1, 2, 1}, {2, 4, 3}, {1, 6, 5}};
        }

//        查询下标范围
        int l, r;
//        喜好值, 临时变量
        int k, num;

        for (int i = 1; i <= hobby.length; i++) {
            num = hobby[i - 1];
            System.out.println("第 " + i + " 位用户对此文章的喜好值为：" + num);

            Node node = new Node(i);
            if (hashMap.get(num) != null) {
                hashMap.get(num).addNode(node);
            } else if (hashMap.get(num) == null) {
                hashMap.put(num, node);
            }
        }

        for (int i = 0; i < q_list.length; i++) {
            l = q_list[i][0];
            r = q_list[i][1];
            k = q_list[i][2];
            int count = 0;
            Node root = hashMap.get(k);

            while (root != null && root.index <= r) {
                if (root.index >= l && root.index <= r) {
                    count++;
                }
                root = root.next;
            }
            System.out.println("查询用户序号 [" + l + ", " + r + "] 的 k=" + k + " 的用户数量：" + count);
        }
    }

    //单链表
    static class Node {
        //序号
        int index;
        Node next;

        /**
         * @param index 用户序号
         */
        public Node(int index) {
            this.index = index;
        }

        //        链尾追加
        public void addNode(Node node) {
            if (next == null) {
                next = node;
            } else {
                next.addNode(node);
            }
        }

    }

}
