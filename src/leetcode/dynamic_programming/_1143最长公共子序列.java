package leetcode.dynamic_programming;

import org.junit.Test;

public class _1143最长公共子序列 {
    public int longestCommonSubsequence(String s, String t) {
        int m = s.length(), n = t.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }
    public String longestCommonSubsequence_str(String s, String t) {
        int m = s.length(), n = t.length();
        int[][] dp = new int[m + 1][n + 1];
        Index[][] idx = new Index[m + 1][n + 1];
        for (int i = 0; i <= m; ++i) {
            idx[i][0] = new Index(i, -1);
        }
        for (int j = 0; j <= n; ++j) {
            idx[0][j] = new Index(-1, j);
        }
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    idx[i][j] = new Index(i - 1, j - 1);
                } else {
                    if (dp[i - 1][j] >= dp[i][j - 1]) {
                        dp[i][j] = dp[i - 1][j];
                        idx[i][j] = new Index(i - 1, j);
                    } else {
                        dp[i][j] = dp[i][j - 1];
                        idx[i][j] = new Index(i, j - 1);
                    }
                }
            }
        }
        if (dp[m][n] > 0) {
            StringBuilder sb = new StringBuilder();
            int current_i = m, current_j = n;
            while (current_i != -1 && current_j != -1) {
                Index ij = idx[current_i][current_j];
                if (ij.i == current_i-1 && ij.j == current_j-1) {
                    sb.append(s.charAt(current_i - 1));
                }
                current_i = ij.i;
                current_j = ij.j;
            }
            return sb.reverse().toString();
        }
        return "";
    }
    public String longestCommonSubsequence_gbro(String s, String t) {
        int m = s.length(), n = t.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        int i = m, j = n;
        StringBuilder sb = new StringBuilder();
        while (i > 0 && j > 0) {
            if (s.charAt(i - 1) == t.charAt(j - 1)) {
                sb.append(s.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i-1][j] >= dp[i][j-1]) {
                i--;
            } else {
                j--;
            }
        }
        return sb.reverse().toString();
    }
    @Test
    public void test() {
        String s = "abc", t = "abde";
        String s1 = longestCommonSubsequence_str(s, t);
        String s2 = longestCommonSubsequence_gbro(s, t);
        System.out.println(s1);
        System.out.println(s2);
    }
}
class Index {
    int i;
    int j;
    Index(int i, int j) {
        this.i = i;
        this.j = j;
    }
}
