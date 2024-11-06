package leetcode;

import java.util.Arrays;

/**
 * 【题目描述】：https://leetcode.cn/problems/edit-distance/description/
 * 1、将字符串 s 通过三种的不同操作转化为 t 的最少操作数
 * 2、三种操作分别是：
 * 1）插入一个字符
 * 2）删除一个字符
 * 3）替换一个字符
 * 【题解】：【最长公共子序列 编辑距离】https://www.bilibili.com/video/BV1TM4y1o7ug?vd_source=f5fd365e0b1873b7d68462bb85723e95
 * 【思路】：
 * 示例：s=horse, t=ros
 * i=s.length-1, j=t.length-1
 * <p>
 * 1) 当s[i]==t[j]，计算子问题dfs(i-1, j-1)
 * 2)不相等时，计算三种操作的个数取min，再加1（表示进行了某种操作）
 * 插入的子问题：dfs(i, j-1)，比如上例，'e' != 's', 那么可以在e后追加's'，这样计算子问题 horse(s)->ro(s)
 * 删除的子问题：dfs(i-1, j)，比如上例，将'e'删除，计算子问题 hors->ros
 * 替换的子问题：dfs(i-1, j-1)，如上例，可以将'e'替换为's'，这样的话，计算子问题hors(s)->ro(s)
 * 【迭代式】：
 * dfs(i, j) = dfs(i-1, j-1),                                    when s[i]==t[j]
 * = min{dfs(i, j-1), dfs(i-1, j), dfs(i-1, j-1)} + 1, when s[i]!=t[j]
 */
public class _72编辑距离 {
    String s;
    String t;
    int[][] memo;

    int dfs(int i, int j) {
        if (i < 0) {
            return j + 1;  // s为空串，那么只能将t中剩余字符全部'删除'
        }
        if (j < 0) {
            return i + 1;  // 同上理
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        if (s.charAt(i) == t.charAt(j)) {
            return dfs(i - 1, j - 1);
        }
        int res = Math.min(dfs(i, j - 1), dfs(i - 1, j));
        res = Math.min(dfs(i - 1, j - 1), res) + 1;
        memo[i][j] = res;
        return res;
    }

    public int minDistance(String word1, String word2) {
        this.s = word1;
        this.t = word2;
        int m = s.length(), n = t.length();
        memo = new int[m][n];
        for (int[] arr : memo) {
            Arrays.fill(arr, -1);
        }
        return dfs(m - 1, n - 1);
    }

    int dp(String s, String t) {
        int m = s.length(), n = t.length();
        int[][] dp = new int[m + 1][n + 1];
        // 1、初始化(对应dfs中的边界)
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }
        // 2、dp计算
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int res = Math.min(dp[i][j - 1], dp[i - 1][j]);
                    dp[i][j] = Math.min(dp[i - 1][j - 1], res) + 1;
                }
            }
        }
        return dp[m][n];
    }
}
