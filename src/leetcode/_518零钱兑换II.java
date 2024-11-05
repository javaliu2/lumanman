package leetcode;

import java.util.Arrays;

/**
 * 【原题】：https://leetcode.cn/problems/coin-change-ii/description/
 * 【思路】：
 */
public class _518零钱兑换II {
    int[][] memo;
    int dfs(int i, int amount) {
        if (i < 0) {
            if (amount == 0) {
                return 1;
            }
            return 0;
        }
        if (memo[i][amount] != -1) {
            return memo[i][amount];
        }
        if (coins[i] > amount) {
            return memo[i][amount] = dfs(i - 1, amount);
        }
        int res = dfs(i - 1, amount) + dfs(i, amount - coins[i]);
        memo[i][amount] = res;
        return res;
    }
    int[] coins;
    public int change(int amount, int[] coins) {
        // 完全背包，满足背包容量的物品集合的个数（物品可重复选取）
        int n = coins.length;
        this.coins = coins;
        memo = new int[n][amount + 1];
        for (int[] arr : memo) {
            Arrays.fill(arr, -1);
        }
        return dfs(n - 1, amount);
    }

    public int dp(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int c = 0; c <= amount; c++) {
                if (coins[i - 1] > c) {
                    dp[i][c] = dp[i - 1][c];
                } else {
                    dp[i][c] = dp[i - 1][c] + dp[i][c - coins[i - 1]];
                }
            }
        }
        return dp[n][amount];
    }

    public int dp_onedim(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int num : coins) {
            for (int c = num; c <= amount; c++) {
                // 从前往后算
                dp[c] = dp[c] + dp[c - num];
            }
        }
        return dp[amount];
    }
}
