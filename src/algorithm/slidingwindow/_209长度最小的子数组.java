package algorithm.slidingwindow;

/**
 * 【题目描述】：https://leetcode.cn/problems/minimum-size-subarray-sum/description/
 * 【题解】：https://www.bilibili.com/video/BV1hd4y1r7Gq/?spm_id_from=333.999.0.0&vd_source=b76f30947c3cefec640464bec427558c
 * 【思路】： 枚举右端点，维护区间[left, right]，该区间元素和 sum >= target
 * 【使用场景】：应用双指针，要求 单调
 */
public class _209长度最小的子数组 {
    /**
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        // 枚举右端点，缩小左端点
        // 因为子数组最长就是数组本身，因此 min值 设置为数组长度加1是绝对没问题的
        int l = 0, n = nums.length, min = n + 1, sum = 0;
        for (int r = 0; r < nums.length; r++) {
            sum += nums[r];
            // 不需要判断left<=right，因为当left==right时，两者相减为0，不可能大于正整数target
            while (sum - nums[l] >= target) {
                sum -= nums[l];
                l++;
            }
            if (sum >= target) {
                min = Math.min(r - l + 1, min);
            }
        }
        return min <= n ? min : 0;
    }

    /**
     * 第二种写法
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen2(int target, int[] nums) {
        int l = 0, n = nums.length, min = n + 1, sum = 0;
        for (int r = 0; r < nums.length; r++) {
            sum += nums[r];
            // 满足要求 => 不满足要求
            while (sum >= target) {
                min = Math.min(r - l + 1, min);
                sum -= nums[l];
                l++;
            }
        }
        return min <= n ? min : 0;
    }
}
