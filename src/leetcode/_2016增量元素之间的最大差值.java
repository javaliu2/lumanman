package leetcode;

import java.util.Vector;

public class _2016增量元素之间的最大差值 {
    /**
     * @author LCO
     * @param nums
     * @return
     */
    public int maximumDifference(int[] nums) {
        int n = nums.length;
        int ans = -1, premin = nums[0];
        for (int i = 1; i < n; ++i) {
            if (nums[i] > premin) {
                ans = Math.max(ans, nums[i] - premin);
            } else {
                premin = nums[i];
            }
        }
        return ans;
    }
        public int maximumDifference_my(int[] nums) {
            // brute force
            int n = nums.length, ans_max = Integer.MIN_VALUE;
            for (int i = 0; i < n; ++i) {
                for (int j = i + 1; j < n; ++j) {
                    if (nums[j] - nums[i] > 0) {
                        ans_max = Math.max(ans_max, nums[j] - nums[i]);
                    }
                }
            }
            if (ans_max == Integer.MIN_VALUE) {
                return -1;
            }
            return ans_max;
        }
}
