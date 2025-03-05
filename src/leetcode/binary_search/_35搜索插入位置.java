package leetcode.binary_search;

public class _35搜索插入位置 {
    public int searchInsert(int[] nums, int target) {
        int low = 0, high = nums.length - 1, mid;
        while (low <= high) {
            mid = low + (high - low) / 2;
            int num = nums[mid];
            if (num == target) {
                return mid;
            } else if (num < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        // 最后low保存的是target被插入位置的索引
        return low;
    }
}
