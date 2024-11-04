package leetcode;

/**
 * 【原题】：https://leetcode.cn/problems/partition-equal-subset-sum/description/
 * 【思路】：https://leetcode.cn/problems/partition-equal-subset-sum/solutions/2785266/0-1-bei-bao-cong-ji-yi-hua-sou-suo-dao-d-ev76/
 */
public class _416分割等和子集 {
    int[] nums;
    boolean dfs(int i, int target) {
        if (i < 0) {
            return target == 0;
        }
        return dfs(i - 1, target) || dfs(i - 1, target - nums[i]);
    }
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int item : nums) {
            sum += item;
        }
        if (sum % 2 == 1) {
            return false;
        }
        // nums中是否存在一个子序列，使得其和等于sum/2
        int target = sum / 2;
        this.nums = nums;
        return dfs(nums.length - 1, target);
    }

    boolean dp(int[] nums){
        int sum = 0;
        for (int item : nums) {
            sum += item;
        }
        if (sum % 2 == 1) {
            return false;
        }
        int target = sum / 2, n = nums.length;
        boolean[][] dp = new boolean[n + 1][target + 1];
        dp[0][0] = true;
        for (int i = 1; i <= n; i++) {
            for (int c = 0; c <= target; c++) {
                if (nums[i - 1] > c) {
                    dp[i][c] = dp[i - 1][c];
                } else {
                    dp[i][c] = dp[i - 1][c] || dp[i - 1][c - nums[i - 1]];
                }
            }
        }
        return dp[n][target];
    }

    boolean dp_onedim(int[] nums){
        int sum = 0;
        for (int item : nums) {
            sum += item;
        }
        if (sum % 2 == 1) {
            return false;
        }
        int target = sum / 2, n = nums.length;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int i = 0; i < n; i++) {
            for (int c = target; c >= nums[i]; c--) {
                dp[c] = dp[c] || dp[c - nums[i]];
            }
        }
        return dp[target];
    }
}
