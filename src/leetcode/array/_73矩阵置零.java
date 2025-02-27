package leetcode.array;

import java.util.ArrayList;
import java.util.List;

public class _73矩阵置零 {
    public void setZeroes(int[][] matrix) {
        /**
         * 有思路就写。喵的
         * 两次遍历，第一次遍历，保存所有0值所在行和列
         * 然后将这些行和列上的值均置为0
         */
        List<int[]> l = new ArrayList<>();
        int m = matrix.length, n = matrix[0].length;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (matrix[i][j] == 0) {
                    l.add(new int[]{i, j});
                }
            }
        }
        // 置0
        for (int[] ele : l) {
            for (int i = 0; i < m; ++i) {
                matrix[i][ele[1]] = 0;
            }
            for (int j = 0; j < n; ++j) {
                matrix[ele[0]][j] = 0;
            }
        }
    }
}
