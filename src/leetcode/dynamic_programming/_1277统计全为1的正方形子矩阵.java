package leetcode.dynamic_programming;

/**
 * dp[i][j]表示，在matrix中以matrix[i][j]为右下角的正方形子矩阵的最大边长，
 * 这个值等价于以matrix[i][j]为右下角的正方形子矩阵的个数，因为假设最大边长为x，
 * 那么所有正方形子矩阵的边长分别为1、2、3、...、x，共x个
 * 正推，反推，得出递推公式为
 *            { matrix[i][j]  ,if i == 0 or j == 0
 * dp[i][j] = { 0             ,if matrix[i][j] == 0
 *            { min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1, otherwise
 */
public class _1277统计全为1的正方形子矩阵 {
    public int countSquares(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        // 1、初始化
        for (int i = 0; i < m; ++i) {
            dp[i][0] = matrix[i][0];
        }
        for (int j = 0; j < n; ++j) {
            dp[0][j] = matrix[0][j];
        }
        // 2、计算
        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                if (matrix[i][j] == 0) {
                    dp[i][j] = 0;
                } else {
                    int t = Math.min(dp[i][j - 1], dp[i - 1][j]);
                    dp[i][j] = Math.min(t, dp[i - 1][j - 1]) + 1;
                }
            }
        }
        int sum = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                sum += dp[i][j];
            }
        }
        return sum;
    }
}
