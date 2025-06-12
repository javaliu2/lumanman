package leetcode.array;

public class _3423循环数组中相邻元素的最大差值 {
    public int maxAdjacentDistance(int[] nums) {
        int i, max = Integer.MIN_VALUE;
        for (i = 1; i < nums.length; ++i) {
            max = Math.max(max, Math.abs(nums[i] - nums[i-1]));
        }
        max = Math.max(max, Math.abs(nums[0] - nums[i-1]));
        return max;
    }
}
