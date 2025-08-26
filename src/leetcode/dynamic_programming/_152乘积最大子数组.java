package leetcode.dynamic_programming;

public class _152乘积最大子数组 {
    /**
     * consider [-2,3,-4]
     */
    public int maxProduct_fail(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n + 1];
        dp[1] = nums[0];
        int max = dp[1];
        for (int i = 1; i < n; ++i) {
            dp[i + 1] = Math.max(nums[i] * dp[i], nums[i]);
            max = Math.max(dp[i + 1], max);
        }
        return max;
    }

    public int maxProduct(int[] nums) {
        int n = nums.length;
        int[] dp_max = new int[n];
        int[] dp_min = new int[n];
        dp_max[0] = nums[0];
        dp_min[0] = nums[0];
        int max = dp_max[0];
        for (int i = 1; i < n; ++i) {
            dp_max[i] = Math.max(dp_max[i - 1] * nums[i], dp_min[i - 1] * nums[i]);
            dp_max[i] = Math.max(dp_max[i], nums[i]);
            dp_min[i] = Math.min(dp_max[i - 1] * nums[i], dp_min[i - 1] * nums[i]);
            dp_min[i] = Math.min(dp_min[i], nums[i]);
            max = Math.max(dp_max[i], max);
        }
        return max;
    }
}
