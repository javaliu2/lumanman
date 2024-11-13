package leetcode;

public class _3258统计满足K约束的子字符串数量I {
    /**
     * My Solution(brute force)
     * @param s
     * @param k
     * @return
     */
    public int countKConstraintSubstrings(String s, int k) {
        char[] ch = s.toCharArray();
        int res = 0, n = ch.length;
        for (int i = 0; i < n; i++) {
            int[] cnt = new int[2];
            int t = 0;
            for (int j = i; j < n; j++) {
                cnt[ch[j] - '0']++;
                if (cnt[0] > k && cnt[1] > k) {
                    break;
                }
                t++;
            }
            res += t;
        }
        return res;
    }
}
