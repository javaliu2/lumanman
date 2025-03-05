package leetcode.binary_search;

public class _240搜索二维矩阵II {
    private boolean binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {  // 有等号的注意
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                return true;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        /**
         * 行或者列数组元素升序，很容易想到二分查找
         * 逐行二分查找，首先判断target是否 >=arr[0]且<=arr[n-1]
         * 是的话，对该数组arr进行查找，否则判断下一个行数组
         */
        int n = matrix[0].length;
        for (int[] arr : matrix) {
            if (target >= arr[0] && target <= arr[n - 1]) {
                boolean exist = binarySearch(arr, target);
                if (exist) {
                    return true;
                }
            }
        }
        return false;
    }
}

/**
 * 官方题解，通过返回target的下标来判断该元素是否存在
 */
class SolutionOfficial {
    public boolean searchMatrix(int[][] matrix, int target) {
        for (int[] row : matrix) {
            int index = search(row, target);
            if (index >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 模板代码了属于是
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            int num = nums[mid];
            if (num == target) {
                return mid;
            } else if (num > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }
}
