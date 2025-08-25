package leetcode.dynamic_programming;

public class _279完全平方数 {
    public int numSquares(int n) {
        int i;
        for (i = 1; i * i <= n; ++i) ;
        // i是大于n的最小的完全平方数的基
        int[][] dp = new int[i][n + 1];
        // dp[i][j]: 1,2,...,i个平方数选项，凑整数j所需最少数量
        // 1、初始化，dp[1][1,...,n] = [j]
        for (int j = 1; j <= n; ++j) {
            dp[1][j] = j;
        }
        // 2、计算
        for (int k = 2; k < i; ++k) {
            for (int j = 1; j <= n; ++j) {
                dp[k][j] = dp[k - 1][j];  // 不选
                if (j >= k * k) {  // 是否可选
                    dp[k][j] = Math.min(dp[k][j], dp[k][j - k * k] + 1);  // 完全背包，物品可以重复选取，这里对应前k个完全平方数可以重复使用
                    // +1的意思是说，我选择了当前的完全平方数，构成j的完全平方数数量加1
                }
            }
        }
        // 3、返回结果
        return dp[i - 1][n];
    }
}
