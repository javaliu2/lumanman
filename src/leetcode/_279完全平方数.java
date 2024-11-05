package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _279完全平方数 {
    List<Integer> nums;
    int[][] memo;

    int dfs(int i, int target) {
        if (i < 0) {
            if (target == 0) {
                return 0;
            }
            return Integer.MAX_VALUE / 2;
        }
        if (memo[i][target] != -1) {
            return memo[i][target];
        }
        int t = nums.get(i);
        if (t > target) {
            return memo[i][target] = dfs(i - 1, target);
        }
        return memo[i][target] = Math.min(dfs(i - 1, target), dfs(i, target - t) + 1);
    }

    public int numSquares(int n) {
        // 完全背包，物品体积为平方数数值，价值均为1
        // 1、物品集合
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i * i <= n; i++) {
            nums.add(i * i);
        }
        // 2、dfs
        this.nums = nums;
        int end = nums.size();
        memo = new int[end][n + 1];
        for (int[] arr : memo) {
            Arrays.fill(arr, -1);
        }
        int res = dfs(end - 1, n);
        if (res < Integer.MAX_VALUE / 2) {
            return res;
        }
        return -1;
    }

    int dp_onedim(int n) {
        // 3、dynamic programming
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int item : nums) {
            for (int c = item; c <= n; c++) {
                // 完全背包，从前往后
                dp[c] = Math.min(dp[c], dp[c - item] + 1);
            }
        }
        return dp[n];
    }
}
