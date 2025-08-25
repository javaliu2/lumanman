package leetcode.array;

public class _498对角线遍历 {
    public static int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[] nums = new int[m * n];
        boolean rightUp = true;
        int i = 0, j = 0;
        for (int k = 0; k < nums.length; ++k) {
            if (rightUp) {
                nums[k] = mat[i][j];
                if (i - 1 < 0) {
                    if (j + 1 == n) {
                        i = i + 1;
                    } else {
                        j = j + 1;
                    }
                    rightUp = false;
                } else if (j + 1 == n) {
                    i = i + 1;
                    rightUp = false;
                } else {
                    i--;
                    j++;
                }
            } else {
                nums[k] = mat[i][j];
                if (i + 1 == m) {
                    j = j + 1;
                    rightUp = true;
                } else if (j - 1 < 0) {
                    i = i + 1;
                    rightUp = true;
                } else {
                    i++;
                    j--;
                }
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        int[][] mat = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[] diagonalOrder = findDiagonalOrder(mat);
        System.out.println(diagonalOrder);
    }
}
