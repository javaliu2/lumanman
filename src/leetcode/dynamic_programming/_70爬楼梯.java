package leetcode.dynamic_programming;

import java.util.HashMap;
import java.util.Map;

public class _70爬楼梯 {
    Map<Integer, Integer> memories = new HashMap<>();

    public int climbStairs_recursion(int n) {
        if (memories.get(n) != null) {
            return memories.get(n);
        }
        if (n == 0) {
            return 1;
        } else if (n < 0) {
            return 0;
        }
        int ans = climbStairs(n - 1) + climbStairs(n - 2);
        memories.put(n, ans);
        return ans;
    }

    public int climbStairs_dim(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; ++i) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        int pp = 1, p = 1, curr = p + pp;
        for (int i = 2; i <= n; ++i) {
            curr = p + pp;
            pp = p;
            p = curr;
        }
        return curr;
    }
}
