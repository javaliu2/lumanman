package leetcode;

import java.util.Arrays;

/**
 * 【题目描述】：https://leetcode.cn/problems/longest-increasing-subsequence/description/
 * 【视频题解】：【最长递增子序列【基础算法精讲 20】】https://www.bilibili.com/video/BV1ub411Q7sB?vd_source=f5fd365e0b1873b7d68462bb85723e95
 * 【思路】：
 * 一、回溯三问：
 *      1、子问题：以 nums[i] 结尾的 LIS 长度
 *      2、当前操作？枚举 nums[j]
 *      3、下一个子问题？以 nums[j] 结尾的 LIS 长度
 * 【迭代式】：
 * dfs(i) = max{dfs(j)} + 1, s.t j < i and nums[j] < nums[i]
 */
public class _300最长递增子序列 {
    int[] nums;
    int[] memo;
    int dfs(int i) {
        // 不需要边界条件，因为当i==0时，for循环不会被执行
        if (memo[i] != -1) {
            return memo[i];
        }
        int max = 0;
        for (int j = i - 1; j >= 0; j--) {
            if (nums[j] < nums[i]) {
                max = Math.max(max, dfs(j));
            }
        }
        memo[i] = max + 1;
        return max + 1;
    }
    public int lengthOfLIS_dfs(int[] nums) {
        this.nums = nums;
        int n = nums.length, max = 0;
        memo = new int[n];
        Arrays.fill(memo, -1);
        for (int i = n - 1; i >= 0; i--) {
            max = Math.max(max, dfs(i));
        }
        return max;
    }
    int dp(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j]);
                }
            }
            dp[i] += 1;
        }
        int res = 0;
        for (int num : dp) {
            if (num > res) {
                res = num;
            }
        }
        return res;
    }
}
