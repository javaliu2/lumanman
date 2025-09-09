package leetcode.greedy_policy;

import java.util.Arrays;

public class _45跳跃游戏II {
    public int jump(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n]; // dp[i]: 从0到达i的最少跳跃次数
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i < n; ++i) {
            // 查找所有方案中跳跃次数最少的
            for (int j = i - 1; j >= 0; --j) {
                if (nums[j] + j >= i) {
                    dp[i] = Math.min(dp[i], dp[j]);
                }
            }
            dp[i] += 1;
        }
        return dp[n-1];
    }
}
