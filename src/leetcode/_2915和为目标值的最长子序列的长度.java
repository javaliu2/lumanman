package leetcode;

import java.util.Arrays;
import java.util.List;

public class _2915和为目标值的最长子序列的长度 {
    List<Integer> nums;
    int[][] memo;
    int dfs(int i, int target){
        if (i < 0) {
            if (target == 0) {
                return 0;
            }
            return -Integer.MAX_VALUE;
        }
        if (memo[i][target] != -1) {
            return memo[i][target];
        }
        int res;
        if (nums.get(i) > target) {
            res = dfs(i - 1, target);
        } else {
            res = Math.max(dfs(i - 1, target), dfs(i - 1, target - nums.get(i)) + 1); // 这个加1就代表子数组的长度
        }
        memo[i][target] = res;
        return res;
    }


    /**
     * 就是说，有点东西，嘿嘿嘿。
     * @param nums
     * @param target
     * @return
     */
    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        this.nums = nums;
        memo = new int[nums.size() + 1][target + 1];
        for (int[] arr : this.memo) {
            Arrays.fill(arr, -1);
        }
        int res = dfs(nums.size() - 1, target);
        if (res < 0){
            return -1;
        }
        return res;
    }
    int dp(List<Integer> nums, int target) {
        int n = nums.size();
        int[][] dp = new int[n + 1][target + 1];
        for (int[] item : dp) {
            Arrays.fill(item, -Integer.MAX_VALUE);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int c = 0; c <= target; c++) {
                if (c < nums.get(i - 1)){
                    dp[i][c] = dp[i - 1][c];
                } else {
                    dp[i][c] = Math.max(dp[i - 1][c], dp[i - 1][c - nums.get(i - 1)] + 1);
                }
            }
        }
        if (dp[n][target] < 0) {
            return -1;
        }
        return dp[n][target];
    }

    int dp_onedim(List<Integer> nums, int target) {
        int n = nums.size();
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            for (int c = target; c >= nums.get(i); c--) {
                // 需要从后往前算
                dp[c] = Math.max(dp[c], dp[c - nums.get(i)] + 1);
            }
        }
        if (dp[target] < 0) {
            return -1;
        }
        return dp[target];
    }

    public static void main(String[] args) {

    }
}
