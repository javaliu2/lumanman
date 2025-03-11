package leetcode.binary_search;

public class _34在排序数组中查找元素的第一个和最后一个位置 {
    private int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1, mid;
        while (left < right) {
            mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] == target) {
                // mid可能是第一个位置也可能不是，所以需要将right置为mid
                // 去前面的区间查找target，因此区间置为[left, mid]
                right = mid;
            } else {
                // nums[mid] > target
                right = mid - 1;
            }
        }
        // when while end, left==right, but [left]?=target
        if (nums[left] == target) {
            return left;
        }
        return -1;
    }

    private int upperBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1, mid;
        while (left < right) {
            // '='表示赋值, '=='表示变量的值
            // 考虑实例[5,7,7,8,8,10],target==8
            // 索引[0,1,2,3,4,5]
            // Round 1: l = 0, r = 5, mid = 2, [2]==7 less_than 8 ==> l = mid+1==3, r == 5
            // Round 2: l = 3, r = 5, mid = 4, [4]==8 equals 8 ==> l = mid==4, r == 5
            // Round 3: l = 4, r = 5, mid = 4, [4]==8 equals 8 ==> l = mid==4, r == 5
            // 死循环了
            mid = left + (right - left + 1) / 2;  // 这里加一，这样就不死循环了
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] == target) {
                // 去后面的区间继续查找target，因此区间变为[mid, right]
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    /**
     * @idea LeetCode Official Video tips
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }
        int[] ans = new int[2];
        ans[0] = lowerBound(nums, target);
        if (ans[0] == -1) {
            ans[1] = -1;
            return ans;
        }
        ans[1] = upperBound(nums, target);
        return ans;
    }
}
