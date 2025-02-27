package leetcode.array;

import java.util.ArrayList;
import java.util.List;

public class _54螺旋矩阵 {
    /**
     * fail. 针对测试用例[[3],[2]]输出[3,2,2]
     * 变量的个数用少了，这使我想起来信息论，表达目的需要一定量的信息
     * 在这里就是说，实现特定功能的程序需要一定数量的变量
     */
    public List<Integer> spiralOrder_fail(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        int row = 0, col = 0, m = matrix.length, n = matrix[0].length;
        // 从[row][col]开始遍历
        while (ans.size() < m * n) {
            for (int j = col; j < n - col; ++j) {
                ans.add(matrix[row][j]);
            }
            for (int i = row + 1; i < m - row; ++i) {
                ans.add(matrix[i][n - col - 1]);
            }
            for (int j = n - col - 2; j > col; --j) {
                ans.add(matrix[m - row - 1][j]);
            }
            for (int i = m - row - 1; i > row; --i) {
                ans.add(matrix[i][col]);
            }
            row++;
            col++;
        }
        return ans;
    }

    /**
     * @idea LeetCode Official
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        int top = 0, bottom = matrix.length - 1;
        int left = 0, right = matrix[0].length - 1;
        int num = (bottom + 1) * (right + 1);
        while (ans.size() < num) {
            for (int j = left; j <= right; ++j) {
                ans.add(matrix[top][j]);
            }
            for (int i = top + 1; i <= bottom; ++i) {
                ans.add(matrix[i][right]);
            }
            if (left < right && top < bottom) {
                for (int j = right - 1; j > left; --j) {
                    ans.add(matrix[bottom][j]);
                }
                for (int i = bottom; i > top; --i) {
                    ans.add(matrix[i][left]);
                }
            }
            top++;
            left++;
            right--;
            bottom--;
        }
        return ans;
    }
}
