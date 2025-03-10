package leetcode;

import java.util.Arrays;

public class _1458两个子序列的最大点积 {
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int n1 = nums1.length, n2 = nums2.length;
        int[][] dp = new int[n1 + 1][n2 + 1];
        for (int[] arr : dp) {
            Arrays.fill(arr, -Integer.MAX_VALUE / 2);  // 防止上溢，变成正数
        }
        dp[0][0] = 0;
        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                int res = Math.max(dp[i - 1][j], dp[i][j - 1]);
                res = Math.max(res, dp[i - 1][j - 1] + nums1[i - 1] * nums2[j - 1]);
                dp[i][j] = Math.max(res, nums1[i - 1] * nums2[j - 1]);
            }
        }
        return dp[n1][n2];
    }
}
