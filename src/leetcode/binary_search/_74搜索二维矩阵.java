package leetcode.binary_search;

public class _74搜索二维矩阵 {
    public boolean searchMatrix(int[][] matrix, int target) {
        /**
         * 这道题和 240搜索二维矩阵II 相比的话，m*n个数有序
         */
        int m = matrix.length, n = matrix[0].length;
        int low = 0, high = m * n - 1, mid;
        // low和high是一维线性索引，通过索引映射获取matrix中对应的值
        while (low <= high) {
            mid = low + (high - low) / 2;
            int num = matrix[mid / n][mid % n];
            if (num == target) {
                return true;
            } else if (num < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }
}
