package algorithm.dp;

import java.util.Arrays;

/**
 * 【原题描述】：https://leetcode.cn/problems/coin-change/description/
 * 【视频题解】：【0-1背包 完全背包】https://www.bilibili.com/video/BV16Y411v7Y6?vd_source=f5fd365e0b1873b7d68462bb85723e95
 * 【思路】：完全背包的变形，恰好装 capacity ，求最小价值和
 * 可以将每一枚硬币（物品）的价值定义为 1，
 * 所以就是在硬币面值和（体积和）等于 amount（capacity）的情况下，返回最小价值和（就是硬币的最小数量）
 * 【转移方程】：dfs(i, amount) = min{dfs(i - 1, amount), dfs(i, amount - coins[i]) + 1}
 */
public class _322零钱兑换 {
    int[] coins;
    int[][] memories;

    int dfs(int i, int amount) {
        if (i < 0) {
            if (amount == 0) {
                return 0;
            }
            return Integer.MAX_VALUE / 2; // 除 2 防止下面 +1 溢出
        }
        if (memories[i][amount] != -1) {
            return memories[i][amount];
        }
        if (coins[i] > amount) {
            return dfs(i - 1, amount);
        }
        int res = Math.min(dfs(i - 1, amount), dfs(i, amount - coins[i]) + 1);
        memories[i][amount] = res;
        return res;
    }

    public int coinChange(int[] coins, int amount) {
        this.coins = coins;
        int n = coins.length;
        memories = new int[n][amount + 1];
        for (int[] row : memories) {
            Arrays.fill(row, -1);
        }
        int res = dfs(n - 1, amount);
        if (res < Integer.MAX_VALUE / 2) {  // 不存在合法的方案
            return res;
        }
        return -1;
    }

    int dp(int[] coins, int amount) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        // dp数组初始化为inf
        for (int[] item : dp) {
            Arrays.fill(item, Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;  // 对应dfs中的边界条件
        // dp(i, amount) = min{dp(i - 1, amount), dp(i, amount - coins[i]) + 1}
        // 枚举每一个钱币，i表示的是dp数组的索引，表示第i个物品，其数组coins索引为i-1
        for (int i = 1; i <= n; i++) {
            // 枚举背包的容量，即要凑的数值
            for (int c = 0; c <= amount; c++) {
                if (coins[i - 1] > c) {
                    dp[i][c] = dp[i - 1][c];
                } else {
                    dp[i][c] = Math.min(dp[i - 1][c], dp[i][c - coins[i - 1]] + 1);
                }
            }
        }
        return dp[n][amount] < Integer.MAX_VALUE / 2 ? dp[n][amount] : -1;
    }
    int dp_onedim(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int i = 0; i < coins.length; i++) {
            for (int c = coins[i]; c <= amount; c++) {
                dp[c] = Math.min(dp[c], dp[c - coins[i]] + 1);
            }
        }
        return dp[amount] < Integer.MAX_VALUE / 2 ? dp[amount] : -1;
    }
    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 11;
        _322零钱兑换 obj = new _322零钱兑换();
        int res = obj.coinChange(coins, amount);
        System.out.println(res);
        res = obj.dp(coins, amount);
        System.out.println(res);
//        int e = Integer.MAX_VALUE + 1;
//        System.out.println(e);
    }
}
