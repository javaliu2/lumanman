package leetcode.dynamic_programming;

public class _322零钱兑换 {
    public int coinChange(int[] coins, int amount) {
        int m = coins.length;
        int[][] dp = new int[m][amount + 1];
        // dp[i][j]: 前i个硬币选项凑成j钱所需最少数量
        // 1、初始化
        int coin = coins[0];
        int INF = (int) 1e9;
        for (int j = 1; j <= amount; ++j) {
            int t = j / coin;
            if (t * coin == j) {
                dp[0][j] = t;
            } else {
                dp[0][j] = INF;  // 凑不出来，用INF表示
            }
        }
        // 2、计算
        for (int i = 1; i < m; ++i) {
            for (int j = 1; j <= amount; ++j) {
                dp[i][j] = dp[i - 1][j];
                if (j >= coins[i]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - coins[i]] + 1);  // 完全背包
                }
            }
        }
        // 3、结果
        return dp[m - 1][amount] == INF ? -1 : dp[m - 1][amount];
    }
}
