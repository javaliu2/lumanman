package algorithm.slidingwindow;

/**
 * 【题目描述】： https://leetcode.cn/problems/subarray-product-less-than-k/description/
 * 【视频题解】： B站灵神
 * 【思路】： 枚举right，表示[left, right]区间，该区间所有数乘积【小于】k，
 *          因为均是正整数，那么[left+1, right]乘积【肯定】小于 k，
 *          同理，[left+2, right]，...，[right, right]
 *          那么以right为右端点的子数组的个数为 right-left+1，将该值累加到 ans 中
 * 【变量名】：
 *          1）product：乘积
 */
public class _713乘积小于K的子数组 {
    /***
     * 存在 bug，考虑 nums=[10,9,10,4,3,8,3,3,6,2,10,10,9,3]，k=19
     * p = 4 * 3 * 8 = 96, 移除4，p->24, 但是24 > 19，故需要继续移除
     * 所以移除左侧元素【直至】p < k, 应采用while逻辑
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k <= 1) {
            return 0;
        }
        int left = 0, ans = 0, p = 1;
        for (int right = 0; right < nums.length; right++) {
            p *= nums[right];
            while (p >= k) {
                p /= nums[left];  // 将左侧元素剔除窗口（窗口移动）直至 p<k
                left++;
            }
            ans += right - left + 1;
        }
        return ans;
    }
}
