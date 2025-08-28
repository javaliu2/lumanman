package leetcode.dynamic_programming;

public class _416分割等和子集 {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; ++i) {
            sum += nums[i];
        }
        if (sum % 2 == 1) {
            return false;
        }
        // 问nums能否被划分为两个子集，满足子集和相等
        // 问题转化，假设两个子集和分别为sum1，sum2，nums的和为sum
        // 那么sum1==sum2 且 sum1+sum2==sum ==> sum1==sum2==sum/2
        // 也就是问nums是否存在一个子集，其和等于sum/2
        // 这是01背包的变形，要求物品重量等于capacity，这里是说选取的数之和等于sum/2
        // 不存在物品的价值，那么用boolean[][] dp来计算
        int target = sum / 2;
        // dp[i][j]: 前i个数和是否等于j
        int n = nums.length;
        boolean[][] dp = new boolean[n][target + 1];
        for (int i = 0; i < n; ++i) {
            dp[i][0] = true;
        }
        for (int j = 0; j <= target; ++j) {
            if (nums[0] == j) {
                dp[0][j] = true;
            }
        }
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j <= target; ++j) {
                if (j >= nums[i]) {
                    dp[i][j] = dp[i - 1][j - nums[i]] || dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n - 1][target];
    }
}
