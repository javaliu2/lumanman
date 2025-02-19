package leetcode.dynamic_programming;

public class _53最大子数组和 {
    /**
     * @idea LeetCode Official
     */
    public int maxSubArray(int[] nums) {
        int max_sum = Integer.MIN_VALUE, prev_sum = 0;
        for (int num : nums) {
            if (prev_sum >= 0) {
                prev_sum += num;
            } else {
                prev_sum = num;
            }
            max_sum = Math.max(prev_sum, max_sum);
        }
        return max_sum;
    }
}
