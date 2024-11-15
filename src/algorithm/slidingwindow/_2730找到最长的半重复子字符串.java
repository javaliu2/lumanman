package algorithm.slidingwindow;

public class _2730找到最长的半重复子字符串 {
    /**
     * My Solution(一遍过)
     *
     * @param s
     * @return
     */

    public int longestSemiRepetitiveSubstring(String s) {
        char[] ch = s.toCharArray();
        int n = ch.length;
        int left = 0, idx = -1;
        int max = 1;
        boolean flag = false;
        for (int right = 1; right < n; right++) {
            if (flag && ch[right] == ch[right - 1]) {
                left = idx;
            }
            if (ch[right] == ch[right - 1]) {
                idx = right;
                flag = true;
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }


    /**
     * elegant
     * @author 灵茶山艾府
     * @param S
     * @return
     */
    public int longestSemiRepetitiveSubstring2(String S) {
        var s = S.toCharArray();
        int ans = 1, left = 0, same = 0, n = s.length;
        for (int right = 1; right < n; right++) {
            if (s[right] == s[right - 1] && ++same > 1) {
                for (left++; s[left] != s[left - 1]; left++) ;
                same = 1;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}