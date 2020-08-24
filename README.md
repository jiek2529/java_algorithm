# java_algorithm

this is a sorting algorithm demo.
created by InterlliJ.

that is the java main program.

! [多种排序算法性能对比](./多种排序算法性能对比.png)

com.sample.sort包下

平均时间复杂度 O(n^2) 空间复杂度 O(1)
* BubbleSort 冒泡排序
* SelectionSort 选择排序
* InsertionSort 插入排序

平均时间复杂度 O(n (log n)^2) 空间复杂度 O(1)
* ShellSort 希尔排序 | 优化版插入排序；多轮步长缩小的方式，步长为 x = x * k + 1 每轮缩短的方式，如：15 > 7 > 3 > 1，并确保最后一轮步长为1

平均时间复杂度 O(n (log n) 空间复杂度 O(1)
* MergeSort | 归并排序 | 外排序 空间复杂度 O(n)，需申请与原空间同大空间
* HeapSort 堆排序
* QuickSort 快速排序

平均时间复杂度 O(n + k) 空间复杂度 O(k)
* CountingSort | 计数排序 | 用一个计算器列表维护，一般下标为原值同等含义。

平均时间复杂度 O(n + k) 空间复杂度 O(n + k)
* BucketSort | 桶排序 | 有限个桶，并设定其每个值范围，遍历存入。再逐个二次排序每个桶。

平均时间复杂度 O(n * k) 空间复杂度 O(k)
* RadixSort | 基数排序 | 定义十个从个位

# 力扣算法题编程记录

代码目录 https://github.com/jiek2529/java_algorithm/tree/master/src/com/sample/leetcode
| date | difficulty | problems | summary |
|---|---|---|---|
2020.8.24 | hard | 5497 FindLatestStep | 查找大小为 M 的最新分组；使用新数组内维护链表下标，免排序算法
2020.8.24 | medium | KMPSearch | 字符匹配，单个匹配结果与查询所有匹配结果 
2020.8.23 | hard | LCP 14 : SplitArray | 切分数组，给定一个数组nums，将nums 切割成若干个非空子数组，使得每个子数组最左边的数和最右边的数的最大公约数大于 1 
2020.8.22 | medium | PrimeNumbers | 筛法取范围内素数集，进阶：使用 bit 位存数据，节省32倍空间
2020.8.21 | medium | 111 : MinimumDepthOfBinaryTree | 查询二叉树叶最小深度
2020.8.21 | hard | 19 : RemoveNthNodeRromEndOfList | 一趟扫描，删除链表的倒数第N个节点
2020.8.21 | hard | 32 : LongestValidParentheses | 统计字符串中连续有效最长闭合括号个数（双数）；
2020.8.21 | medium | 3 : LongestSubstringWithoutRepeatingCharacters | 求字符串无重复子串最大长度
2020.8.21 | medium | 5 : LongestPalindrome | 字符串中查找最大回文子串，使用动态规划方案，可配置检索方向、最小回文长度和缺省值
2020.8.20 | medium | BinaryTreeTraversal | BFS DFS 二叉树的广度与深度遍历算法
2020.8.19 | medium | 2 ：Add_Two_Numbers | 两个单链表（每个节点值为从个位至高位的一个数）相加，输出结果为同格式单链表
2020.8.18 | easy | 1 ：Two_Sum | 数组中两个数之和等于目标数，每个数只能使用一次

