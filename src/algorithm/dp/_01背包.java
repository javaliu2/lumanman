package algorithm.dp;

/**
 * 1) 前提：<br>
 * 有一个容量为capacity的背包；<br>
 * 有一堆物品，每一个物品有两个属性：<br>
 * 分别是重量weight和value.<br>
 * 2) 目标：<br>
 * 选择物品集合 {i} 使得
 * {weights_i} <= capacity
 * 且 {values_i} 在所有方案中最大，返回最大价值
 */
public class _01背包 {
    static int[] weights;
    static int[] values;
    static int capacity;

    /**
     * 01背包问题的递归实现，说是递归，其实也是深搜，搜索所有方案，找出价值最大的方案
     *
     * @param i：前 i 个物品（子问题）
     * @param c：背包剩余容量
     * @return 当前问题的所有选择方案中的最大价值
     */
    static int dfs(int i, int c) {
        // 一、思路
        // 1、递
        // 当前物品 i 可以被 选/不选
        // 1）选的价值：前 i-1 个物品且背包容量为 c-当前物品重量 的问题的答案 + 当前物品的价值，即下式：
        // dfs(i-1, c-weights[i]) + values[i]
        // 2）不选：前 i-1 个物品且背包容量不变 问题的答案，即下式：
        // dfs(i-1, c)
        // 2、归
        // 选择以上两种选择/方案的较大值作为当前问题 (i, c) 的答案
        // 二、代码实现：
        // 边界条件
        if (i < 0) {
            return 0;
        }
        // 当前物品的重量大于背包容量，*一定* 选不了
        if (weights[i] > c) {
            return dfs(i - 1, c);
        }
        return Math.max(dfs(i - 1, c - weights[i]) + values[i], dfs(i - 1, c));
    }

    /**
     * 背包问题的DP版本
     */
    static int dp(){
        // 一、思路
        // 1、dfs方法调用变数组计算
        // f[i, c] = Math.max( f[i - 1, c - weights[i]] + values[i], f[i - 1, c] )
        // 2、数组初始化（递归边界）
        // 3、循环（递归调用）
        // 二、实现
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        for (int i = 1; i <= n; i++) {
            for (int c = 0; c <= capacity; c++) {
                if (weights[i - 1] > c) {  // attention：i-1表示的是第一个物品，因为为了防止数组越界
                    dp[i][c] = dp[i - 1][c];
                } else {
                    // dp[i][c]：i个物品背包容量为c时，该问题的解
                    dp[i][c] = Math.max(dp[i - 1][c - weights[i - 1]] + values[i - 1], dp[i - 1][c]);
                }
                if (weights[i - 1] > c) {
                    dp[i][c] = dp[i - 1][c];
                }
                dp[i][c] = Math.max(dp[i - 1][c - weights[i - 1]] + values[i - 1], dp[i - 1][c]);
            }
        }
        return dp[n][capacity];
    }


    public static void main(String[] args) {
        int[] _weights = {3, 4, 5, 6, 2};
        int[] _values = {1, 6, 4, 2, 8};
        int _capacity = 10;
        // 随机生成weights，values，和capacity，检查两种解是否一致
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                _weights[j] = (int)Math.round(Math.random() * 100);
                _values[j] = (int)Math.round(Math.random() * 100);
            }
            _capacity = (int)Math.round(Math.random() * 1000);
            weights = _weights;
            values = _values;
            capacity = _capacity;
            int n = weights.length;
            int res = dfs(n - 1, capacity);
//            System.out.println("dfs: " + res);
            int res2 = dp();
//            System.out.println("dp: " + res);
            System.out.println(res == res2);
        }
        weights = _weights;
        values = _values;
        capacity = _capacity;
        int n = weights.length;
        int res = dfs(n - 1, capacity);
        System.out.println(res);
        res = dp();
        System.out.println(res);
    }
}
