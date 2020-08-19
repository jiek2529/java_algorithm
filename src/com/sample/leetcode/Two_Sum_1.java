package com.sample.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiek on 2020/8/19.
 * <p>
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 * <p>
 * <p>
 * 示例:
 * <p>
 * 给定 nums = [2, 7, 11, 15], target = 9
 * <p>
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 * <p>
 * 链接：https://leetcode-cn.com/problems/two-sum
 */
public class Two_Sum_1 {
    public static void main(String[] args) {
        solve(new int[]{2, 7, 10, 22}, 9);
    }

    static void solve(int[] nums, int target) {
        int[] a = new Solution().twoSum(nums, target);
        if (a != null) {
            System.out.println(Arrays.toString(a));
        } else {
            System.out.println("查无结果！");
        }
    }

    static class Solution {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int differ = target - nums[i];
                if (map.containsKey(target - differ)) {
                    return new int[]{map.get(target - differ), i};
                }
                map.put(differ, i);
            }
            return null;
        }
    }
}
