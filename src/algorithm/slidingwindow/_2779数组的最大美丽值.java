package algorithm.slidingwindow;

import java.util.Arrays;

/**
 * 【题目描述】：https://leetcode.cn/problems/maximum-beauty-of-an-array-after-applying-operation/description/
 * 【题解】：https://leetcode.cn/problems/maximum-beauty-of-an-array-after-applying-operation/solutions/2345805/pai-xu-shuang-zhi-zhen-by-endlesscheng-hbqx/
 */

public class _2779数组的最大美丽值 {
    /**
     * My Implement
     */
    public int maximumBeauty(int[] nums, int k) {
        Arrays.sort(nums);
        int l = 0, max = 0;
        for (int r = 0; r < nums.length; r++) {
            while (nums[r] - nums[l] > 2 * k) {
                l++;
            }
            max = Math.max(max, r - l  + 1);
        }
        return max;
    }
}
