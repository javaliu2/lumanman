package leetcode.binary_search;

public class _33搜索旋转排序数组 {
    private int binarySearch(int[] nums, int target, int l, int r) {
        int mid;
        while (l <= r) {
            mid = l + (r - l) / 2;
            if (nums[mid] > target) {
                r = mid - 1;
            } else if (nums[mid] < target) {
                l = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public int search(int[] nums, int target) {
        /**
         * 旋转后的数组变成两个升序子数组
         * 首先找到第二个数组的起始元素坐标k，然后进行两次二分
         * 分别在[0,...,k-1]和[k,...,n-1]上进行
         */
        int k = 0, n = nums.length;
        while (k + 1 < n && nums[k] < nums[k + 1]) {
            k++;
        }
        // when while end, [k]>[k+1]
        // two interval: [0,k], [k+1,n-1]
        int res = binarySearch(nums, target, 0, k);
        if (res != -1) {
            return res;
        }
        res = binarySearch(nums, target, k + 1, n - 1);
        if (res != -1) {
            return res;
        }
        return -1;
    }
}
