package algorithm.dp;

/**
 * 【问题描述】：给定两个字符串，返回他们的最长公共子序列（或长度）
 * 子序列的定义为在字符串中，不改变字符前后顺序，所挑选的字符所组成的新字符串
 * （按照这个定义，原字符串也是子序列）
 * 【思路】：【参照】https://www.bilibili.com/video/BV1TM4y1o7ug?vd_source=f5fd365e0b1873b7d68462bb85723e95
 * 两个字符串s和t
 * 1）当s[i]==t[j]的时候，肯定是选该字符，这样才能保证得到的子序列长度最大。因为选该字符不影响子问题，何乐而不为呢。
 * 形式化证明见【参照】；
 * 2）当s[i]!=t[j]时，从子问题 (s[i-1], t[j]) 和 (s[i], t[j-1]) 中选取较大，
 * 不必考虑 (s[i-1], t[j-1])因为它一定比前两个子问题的解小，因为没有考虑t[j]或者s[i]嘛，直接略过了
 * 【迭代式】：
 * dfs(i, j) = dfs(i - 1, j - 1) + 1,             when s[i]==t[j]
 *           = max{dfs(i - 1, j), dfs(i, j - 1)}, when s[i]!=t[j]
 */
public class _最长公共子序列 {
    String s;
    String t;

    int dfs(int i, int j) {
        if (i < 0 || j < 0) {
            return 0;
        }
        if (s.charAt(i) == t.charAt(j)) {
            return dfs(i - 1, j - 1) + 1;
        }
        return Math.max(dfs(i - 1, j), dfs(i, j - 1));
    }

    int dp(String s, String t) {
        int m = s.length(), n = t.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    /**
     * @author 灵茶山艾府
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        char[] t = text2.toCharArray();
        int m = t.length;
        int[] f = new int[m + 1];
        for (char x : text1.toCharArray()) {
            int pre = 0; // f[0]
            for (int j = 0; j < m; j++) {
                int tmp = f[j + 1];
                f[j + 1] = x == t[j] ? pre + 1 : Math.max(f[j + 1], f[j]);
                pre = tmp;
            }
        }
        return f[m];
    }
}
