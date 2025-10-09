package leetcode.dynamic_programming;

public class _3494酿造药水需要的最少总时间 {
    public long minTime(int[] skill, int[] mana) {
        // times[i][j]: 第i位巫师完成第j个药水的时间
        int n = skill.length, m = mana.length;
        long[][] times = new long[n][m];
        // 1、初始化
        // 第一个药水的完成时间，顺序计算就可
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                times[i][0] = times[i - 1][0] + skill[i] * mana[0];
            } else {
                times[i][0] = skill[i] * mana[0];
            }
        }
        // 2、迭代(正反两次)
        for (int j = 1; j < m; ++j) {
            for (int i = 0; i < n; ++i) {
                // times[i][j] = max(times[i-1][j], times[i][j-1]) + skill[i] * mana[j];
                if (i == 0) {
                    times[i][j] = times[i][j - 1] + skill[i] * mana[j];
                } else {
                    times[i][j] = Math.max(times[i - 1][j], times[i][j - 1]) + skill[i] * mana[j];
                }
            }
            for (int i = n - 2; i >= 0; --i) {
                times[i][j] = times[i + 1][j] - skill[i + 1] * mana[j];
            }
        }
        // 3、返回结果
        return times[n - 1][m - 1];
    }
}
