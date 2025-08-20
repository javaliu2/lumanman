package leetcode.dynamic_programming;

public class _198打家劫舍 {
    public int rob(int[] nums) {
        // 一维dp
        int n = nums.length;
        int[] dp = new int[n+2];
        for (int i = 0; i < nums.length; ++i) {
            dp[i+2] = Math.max(dp[i+1], dp[i] + nums[i]);
        }
        return dp[n+1];
    }
}
