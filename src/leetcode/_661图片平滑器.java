package leetcode;

/**
 * 【题目描述】：https://leetcode.cn/problems/image-smoother/description/
 * 【思路】：逐元素遍历，注意边界判定
 */
public class _661图片平滑器 {
    /**
     * My Solution
     *
     * @param img
     * @return
     */
    public int[][] imageSmoother(int[][] img) {
        int m = img.length, n = img[0].length;
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int sum = img[i][j], cnt = 1;
                if (i - 1 >= 0) {
                    if (j - 1 >= 0) {
                        sum += img[i - 1][j - 1];
                        cnt++;
                    }
                    if (j + 1 < n) {
                        sum += img[i - 1][j + 1];
                        cnt++;
                    }
                    sum += img[i - 1][j];
                    cnt++;
                }
                if (i + 1 < m) {
                    if (j - 1 >= 0) {
                        sum += img[i + 1][j - 1];
                        cnt++;
                    }
                    if (j + 1 < n) {
                        sum += img[i + 1][j + 1];
                        cnt++;
                    }
                    sum += img[i + 1][j];
                    cnt++;
                }
                if (j - 1 >= 0) {
                    sum += img[i][j - 1];
                    cnt++;
                }
                if (j + 1 < n) {
                    sum += img[i][j + 1];
                    cnt++;
                }
                res[i][j] = sum / cnt;
            }
        }
        return res;
    }
}
