package leetcode.dynamic_programming;

public class _5最长回文子串 {
    public String longestPalindrome(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int max_len = 1, idx = 0;
        for (int i = 0; i < n; ++i) {
            dp[i][i] = true;
            if (i + 1 < n && s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                max_len = 2;
                idx = i;
            }
        }
        int len = 3;
        // 要按照长度来计算
        while (len <= n) {
            for (int i = 0; i < n; ++i) {
                // [i, i+len)
                int j = i + len - 1;
                if (j < n) {
                    dp[i][j] = dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
                    if (dp[i][j]) {
                        if (len > max_len) {
                            max_len = len;
                            idx = i;
                        }
                    }
                }
            }
            len++;
        }
        return s.substring(idx, idx + max_len);
    }
}
