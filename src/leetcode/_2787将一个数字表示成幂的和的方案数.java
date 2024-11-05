package leetcode;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.Arrays;

/**
 * 【原题】：https://leetcode.cn/problems/ways-to-express-an-integer-as-sum-of-powers/description/
 * 【题解】：https://leetcode.cn/problems/ways-to-express-an-integer-as-sum-of-powers/solutions/2354970/0-1-bei-bao-mo-ban-ti-by-endlesscheng-ap09/
 * 【我的思路】：物品集合 item={1, 2, ..., n_k}, 其中 n_k <= sqrt(n)
 * 所以问题转化为从物品集合 item 中选取互不相同的元素，使得其x次方和等于n，这即是01背包问题求方案数的变形
 */

/**
 * fatal error：
 * 1) 10 ^ 9 + 7 *NOT* equals 100_000_000_7
 * 2) 注意取余，否则数值溢出
 */
public class _2787将一个数字表示成幂的和的方案数 {
    @Test
    public void test() {
        int a = 10 ^ 9 + 7;  // 服，大写的服。Java中的'^'表示异或
        System.out.println(a);  // 结果为什么是26 ?
//        int n = 15, x = 1;
//        int res = numberOfWays(n, x);
//        System.out.println(res);
//        long r = fast_pow(3, 5);
//        System.out.println(r);
    }

    final int M = 100_000_000_7;
    int x;
    int[][] memo;

    int dfs(int i, int n) {
        if (i == 0) {
            if (n == 0) {
                return 1;
            }
            return 0;
        }
        if (memo[i][n] != -1) {
            return memo[i][n];
        }
        int res;
        if (fast_pow(i, x) > n) {
            res = dfs(i - 1, n) % M;
            memo[i][n] = res;
            return res;
        }
        res = (dfs(i - 1, n) % M + dfs(i - 1, n - fast_pow(i, x)) % M) % M;
        memo[i][n] = res;
        return res;
    }

    public int numberOfWays(int n, int x) {
        this.x = x;
        int nk = (int) (Math.pow(n, 1.0 / x) + 1);
        memo = new int[nk + 1][n + 1];
        for (int[] arr : memo) {
            Arrays.fill(arr, -1);
        }
        return dfs(nk, n);
    }

    int fast_pow(int a, int b) {
        int ans = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans *= a;
            }
            b >>= 1;
            a *= a;
        }
        return ans;
    }

    public static void main(String[] args) {
        int a = 10 ^ 9 + 7;  // 服，大写的服。Java中的'^'表示异或
        // 其次，'+'优先级高于'^'，/(ㄒoㄒ)/~~
        //所以9+7->16, 10^16->26结果为 26
        // 细节啊。细节决定成败。
        System.out.println(a);
    }
    int dp(int n, int x) {
        int nk = (int) (Math.pow(n, 1.0 / x) + 1);
        int[][] dp = new int[nk + 1][n + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= nk; i++) {
            for (int c = 0; c <= n; c++) {
                int t = fast_pow(i, x);  // 物品的体积
                // 从后往前算
                if (t > c) {
                    dp[i][c] = dp[i - 1][c] % M;
                } else {
                    dp[i][c] = (dp[i - 1][c] + dp[i - 1][c - t]) % M;
                }
            }
        }
        return dp[nk][n] % M;
    }
    int dp_onedim(int n, int x) {
        int nk = (int) (Math.pow(n, 1.0 / x) + 1);
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= nk; i++) {
            int t = fast_pow(i, x);  // 物品的体积
            for (int c = n; c >= t; c--) {
                // 从后往前算
                dp[c] = (dp[c] + dp[c - t]) % M;
            }
        }
        return dp[n] % M;
    }
}

