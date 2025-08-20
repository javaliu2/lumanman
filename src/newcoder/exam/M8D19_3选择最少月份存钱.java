package newcoder.exam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class M8D19_3选择最少月份存钱 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int target = in.nextInt();
        int[] money = new int[n];
        for (int i = 0; i < n; ++i) {
            money[i] = in.nextInt();
        }
        List<Integer> path = new ArrayList<>();
        int res = dfs(money, target, 0, path);
        int res2 = dfs2(money, target, 0, 0);
        int res3 = dp(money, target);
        System.out.println(res);
        System.out.println(res2);
        System.out.println(res3);
    }

    /**
     * 有若干月份，每一个月有money[i]多零花钱，不能选择连续两个月份存钱，
     * 问存至少target钱，所需最少月份数？
     * dp[i][j]: 前i个月份存至少j钱花费的最少月份数
     * dp[i][j] = min(dp[i-1][j], dp[i-2][j-money[i]])
     */
    private static int dp(int[] money, int target) {
        int m = money.length;
        int[][] dp = new int[m][target + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, Integer.MAX_VALUE);
            ints[0] = 0;  // target是0的时候，所需月份数是0
        }
        // 1、初始化
        for (int i = 0; i < 2; i++) {
            for (int j = 1; j <= target; j++) {
                if (money[i] >= j) {
                    dp[i][j] = 1;
                }
            }
        }
        // 2、计算
        for (int i = 2; i < m; i++) {
            for (int j = 1; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];  // 2.1、不选当前月份i
                // 2.2、选择当前月份
                if (money[i] >= j) {
                    dp[i][j] = 1;  // 1）只靠当前月就够j钱
                } else if (dp[i-2][j - money[i]] != Integer.MAX_VALUE) {  // 前i-2个月可以凑j-money[i]钱
                    dp[i][j] = Math.min(dp[i][j], dp[i-2][j - money[i]] + 1);
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            ans = Math.min(ans, dp[i][target]);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int dfs2(int[] money, int target, int month, int count) {
        if (month >= money.length) {
            if (target <= 0) {
                return count;
            }
            return -1;
        }
        if (target <= 0) {
            return count;
        }
        // 选择当前月份存储，到达目标所需最少月份
        int res_yes = dfs2(money, target - money[month], month + 2, count + 1);
        // 不选择当前月份存储
        int res_no = dfs2(money, target, month + 1, count);
        if (res_yes == -1) {
            return res_no;
        }
        if (res_no == -1) {
            return res_yes;
        }
        return Math.min(res_yes, res_no);
    }

    static int dfs(int[] money, int target, int month, List<Integer> path) {
        if (month >= money.length) {
            if (target <= 0) {
                return path.size();
            }
            return -1;
        }
        if (target <= 0) {
            return path.size();
        }
        // 选择当前月份存储，到达目标所需最少月份
        path.add(month);
        int res_yes = dfs(money, target - money[month], month + 2, path);
        path.remove(path.size() - 1);
        // 不选择当前月份存储
        int res_no = dfs(money, target, month + 1, path);
        if (res_yes == -1) {
            return res_no;
        }
        if (res_no == -1) {
            return res_yes;
        }
        return Math.min(res_yes, res_no);
    }
}
