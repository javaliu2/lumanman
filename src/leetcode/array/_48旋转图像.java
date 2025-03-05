package leetcode.array;

import java.util.Arrays;

public class _48旋转图像 {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        int i = 0, j = 0, t, t2, ii, jj;
        for (i = 0; i < n / 2; ++i) {
            for (j = i; j < n - 1 - i; ++j) {
                // [i][j] ==> [j][n-1-i]
                ii = i;
                jj = j;
                t = matrix[ii][jj];
                for (int k = 0; k < 4; ++k) {
                    t2 = matrix[jj][n-1-ii];
                    matrix[jj][n-1-ii] = t;
                    t = t2;
                    int tt = ii;
                    ii = jj;
                    jj = n-1-tt;  // 这里出错了，这里的ii已经是修改后的ii
                }
            }
        }
    }

    public static void main(String[] args) {
        _48旋转图像 obj = new _48旋转图像();
        int[][] matrix = {{1,2,3}, {4,5,6},{7,8,9}};
        obj.rotate(matrix);
        for (int[] arr : matrix) {
            System.out.println(Arrays.toString(arr));
        }
    }
}
