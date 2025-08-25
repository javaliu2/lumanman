package leetcode.dynamic_programming;

import java.util.List;

public class _139单词拆分 {
    private List<String> wordDict;

    public boolean wordBreak(String s, List<String> wordDict) {
        this.wordDict = wordDict;
        int m = s.length();
        // dp[i]: s中前i个字符组成的字符串s[0,...,i-1]能否被拆分为wordDict中的单词
        boolean[] dp = new boolean[m + 1];
        dp[0] = true;
        for (int i = 1; i <= m; ++i) {  // 枚举字符串
            for (int j = 0; j < i; ++j) {  // 枚举分割点
                if (dp[j] && check(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[m];
    }

    private boolean check(String s) {
        return wordDict.contains(s);
    }
}
