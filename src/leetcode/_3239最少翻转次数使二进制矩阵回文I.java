package leetcode;

public class _3239最少翻转次数使二进制矩阵回文I {
    /**
     * 一遍过，嘿嘿嘿
     * @param grid
     * @return
     */
    public int minFlips(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        // 行翻转 和 列翻转，取较小值
        int cnt = 0, cnt2 = 0;
        for (int[] arr : grid) {
            int i = 0, j = arr.length - 1;
            while (i < j) {
                if (arr[i] != arr[j]) {
                    cnt++;
                }
                i++;
                j--;
            }
        }
        for (int k = 0; k < n; k++) {
            int i = 0, j = m - 1;
            while (i < j) {
                if (grid[i][k] != grid[j][k]) {
                    cnt2++;
                }
                i++;
                j--;
            }
        }
        return Math.min(cnt, cnt2);
    }
}
