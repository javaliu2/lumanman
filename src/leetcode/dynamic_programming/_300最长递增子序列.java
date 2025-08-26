package leetcode.dynamic_programming;

import java.util.ArrayList;
import java.util.List;

public class _300最长递增子序列 {

    public int lengthOfLIS_dp(int[] nums) {
        // DP，dp[i]表示前i个元素，以第i个元素结尾的最长上升子序列的长度
        // 转移方程，dp[i] = max(dp[j])+1 s.t. 0 <= j < i and nums[j] < nums[i]
        // 也就是说，枚举前i-1个元素，要求该元素小于nums[i]，这样的话满足元素递增的条件
        // 如果有多个满足条件的元素，那么选择以该元素为结尾的递增子序列长度最长的那一个
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = 1;
        for (int i = 1; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j]);
                }
            }
            dp[i] += 1;
        }
        int max = dp[0];
        for (int i = 1; i < n; ++i) {
            if (dp[i] > max) {
                max = dp[i];
            }
        }
        return max;
    }

    /**
     * 贪心 + 二分查找
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        // d[i]: 长度为i的最长上升子序列的末尾元素的最小值
        List<Integer> d = new ArrayList<>();
        d.add(nums[0]);
        for (int i = 1; i < nums.length; ++i) {
            if (nums[i] > d.get(d.size() - 1)) {
                d.add(nums[i]);
            } else {
                int l = 0, r = d.size() - 1, pos = -1;
                // 找到第一个比nums[i]小的数
                while (l <= r) {
                    int mid = l + ((r - l) >> 1);
                    if (d.get(mid) < nums[i]) {
                        pos = mid;
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                d.set(pos + 1, nums[i]);  // 更新d[k+1] = nums[i]
            }
        }
        return d.size();
    }
}
