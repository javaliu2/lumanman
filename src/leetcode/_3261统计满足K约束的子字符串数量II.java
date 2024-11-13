package leetcode;

public class _3261统计满足K约束的子字符串数量II {
    /**
     * My Solution(brute force)
     * 620 / 627 个通过的测试用例
     * @param s
     * @param k
     * @param queries
     * @return
     */
    public long[] countKConstraintSubstrings(String s, int k, int[][] queries) {
        int m = queries.length;
        char[] ch = s.toCharArray();
        long[] ans = new long[m];
        for (int idx = 0; idx < m; idx++) {
            int start = queries[idx][0], end = queries[idx][1];
            int t = 0;
            for (int i = start; i <= end; i++) {
                int[] cnt = new int[2];
                for (int j = i; j <= end; j++) {
                    cnt[ch[j] - '0']++;
                    if (cnt[0] > k && cnt[1] > k) {
                        break;
                    }
                    t++;
                }
            }
            ans[idx] = t;
        }
        return ans;
    }
}
