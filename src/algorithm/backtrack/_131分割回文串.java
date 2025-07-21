package algorithm.backtrack;

import java.util.ArrayList;
import java.util.List;

public class _131分割回文串 {
    private int n;
    private String s;
    private List<List<String>> ans;
    private boolean[][] dp;

    public List<List<String>> partition(String s) {
        // dp[i][j]: s[i, j]表示的子字符串是否是回文串
        // 计算顺序：i: m -> 0, j: 0->n || n->0，j无所谓因为[i+1]行的所有数都有了
        n = s.length();
        this.s = s;
        dp = new boolean[n][n];
        for (int i = n - 1; i >= 0; --i) {
            for (int j = 0; j < n; ++j) {
                if (i >= j) {
                    dp[i][j] = true;
                } else {
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
            }
        }
        // 搜索可分割的回文串集合
        ans = new ArrayList<>();
        List<String> path = new ArrayList<>();
        dfs(0, path);
        return ans;
    }

    void dfs(int start, List<String> path) {
        if (start >= n) {
            ans.add(new ArrayList<>(path));
            return;
        }
        // 枚举以start起始的所有回文串，回文串结束索引记为j
        for (int j = start; j < n; ++j) {
            if (dp[start][j]) {
                path.add(s.substring(start, j + 1));  // 将回文子串加入集合
                dfs(j + 1, path);  // 搜索剩下的子串
                path.remove(path.size() - 1);
            }
        }
    }

    boolean isPalindrome(int i, int j, String s) {
        if (i >= j) {
            return true;
        }
        if (s.charAt(i) == s.charAt(j)) {
            return isPalindrome(i + 1, j - 1, s);
        }
        return false;
    }
}
