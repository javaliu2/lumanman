package leetcode.dynamic_programming;

public class _2140解决智力问题 {
    /**
     * 不出所料，超时。passed 32/54
     */
    long dfs(int start, int[][] qs) {
        if (start >= qs.length) {
            return 0;
        }
        if (start == qs.length - 1) {
            return qs[start][0];
        }
        long score_yes = qs[start][0] + dfs(start + qs[start][1] + 1, qs);
        long score_no = dfs(start + 1, qs);
        return Math.max(score_yes, score_no);
    }

    public long mostPoints(int[][] questions) {
        // DP or DFS
        // 1、先写一版dfs的
        // return dfs(0, questions);
        // 2、DP，从后往前算
        int n = questions.length;
        long[] dp = new long[n];
        // 2.1、初始化
        dp[n - 1] = questions[n - 1][0];
        // 2.2、迭代计算
        for (int i = n - 2; i >= 0; --i) {
            int idx = i + questions[i][1] + 1;
            long score_yes = questions[i][0];
            if (idx < n) {
                score_yes += dp[idx];
            }
            dp[i] = Math.max(score_yes, dp[i + 1]);
        }
        // 2.3、返回结果，即dp[0]
        return dp[0];
    }
}
