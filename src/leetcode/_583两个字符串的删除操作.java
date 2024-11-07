package leetcode;

import java.util.Arrays;

public class _583两个字符串的删除操作 {
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
            return memo[i][j] = dfs(i - 1, j - 1);  // 子问题
        }
        int res = Math.min(dfs(i, j - 1), dfs(i - 1, j)) + 1;  // 删除s或t的较小值
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
}
