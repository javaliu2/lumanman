package leetcode;


/**
 * 【题目描述】：https://leetcode.cn/problems/interleaving-string/description/
 * 【思路】：dp[i][j]表示可否由 s1 的前 i 个字符和 s2 的前 j 个字符组成 s3 的前 i+j 个字符
 * 【迭代式】：dp[i][j] = (dp[i-1][j] && s1[i-1]==s3[i+j-1]) || (dp[i][j-1] && s2[j-1]==s3[i+j-1])
 * attention：dp数组下标和实例下标表示的不同
 * dp[i-1][j]：前i-1个字符；s1[i-1]：s1中的第i个字符
 */
public class _97交错字符串 {
    // 判断s1 / s2 是否是 s3 的公共子序列
    // 通过93个测试用例
    // 当s1=aabd, s2=abdc, s3=aabdbadc 时，代码出现逻辑未覆盖，意料之中
    // s1满足s3中的aabd, 那么s3中的aabd不应该再被使用了，但是计算s2时使用了s3中的a,这是错误的逻辑
    boolean dfs(String s, String t, int i, int j) {
        if (i < 0) {
            return true;
        }
        if (j < 0) {
            return false;
        }
        if (s.charAt(i) == t.charAt(j)) {
            return dfs(s, t, i - 1, j - 1);
        }
        return dfs(s, t, i, j - 1);
    }

    public boolean isInterleave_dfs(String s1, String s2, String s3) {
        int n1 = s1.length(), n2 = s2.length(), n = s3.length();
        if (n1 + n2 != n) {
            return false;
        }
        return dfs(s1, s3, n1 - 1, n - 1) && dfs(s2, s3, n2 - 1, n - 1);
    }

    public boolean isInterleave(String s1, String s2, String s3) {
        int n1 = s1.length(), n2 = s2.length(), n = s3.length();
        if (n1 + n2 != n) {
            return false;
        }
        boolean[][] dp = new boolean[n1 + 1][n2 + 1];
        // 1、初始化
        dp[0][0] = true;
        for (int i = 1; i <= n1; i++) {
            dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
        }
        for (int j = 1; j <= n2; j++) {
            dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
        }
        // 2、迭代
        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1)) || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
            }
        }
        return dp[n1][n2];
    }
}
