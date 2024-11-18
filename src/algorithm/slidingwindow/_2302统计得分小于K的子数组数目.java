package algorithm.slidingwindow;

/**
 * 【题目描述】：https://leetcode.cn/problems/count-subarrays-with-score-less-than-k/
 * 【题解】：https://leetcode.cn/problems/count-subarrays-with-score-less-than-k/solutions/1595722/by-endlesscheng-b120/
 */
public class _2302统计得分小于K的子数组数目 {
    /**
     * @author 灵茶山艾府
     */
    public long countSubarrays_ling(int[] nums, long k) {
        long ans = 0L, sum = 0L;
        for (int left = 0, right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum * (right - left + 1) >= k) {
                sum -= nums[left++];
            }
            ans += right - left + 1;
        }
        return ans;
    }
}
