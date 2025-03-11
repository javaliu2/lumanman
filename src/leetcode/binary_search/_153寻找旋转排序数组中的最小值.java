package leetcode.binary_search;

public class _153寻找旋转排序数组中的最小值 {
    /**
     * @author LeetCode Official
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int pivot = low + (high - low) / 2;
            if (nums[pivot] < nums[high]) {
                high = pivot;
            } else {
                low = pivot + 1;
            }
        }
        return nums[low];
    }
}
