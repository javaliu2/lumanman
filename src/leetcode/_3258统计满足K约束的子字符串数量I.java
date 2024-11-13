package leetcode;

import org.junit.Test;

public class _3258统计满足K约束的子字符串数量I {
    /**
     * My Solution(brute force)
     *
     * @param s
     * @param k
     * @return
     */
    public int countKConstraintSubstrings_my(String s, int k) {
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

    /**
     * @author: 灵茶山艾府
     * 与 713 有相似之处
     */
    public int countKConstraintSubstrings(String S, int k) {
        char[] s = S.toCharArray();
        int ans = 0;
        int left = 0;
        int[] cnt = new int[2];
        for (int i = 0; i < s.length; i++) {
            cnt[s[i] & 1]++;
            while (cnt[0] > k && cnt[1] > k) {
                cnt[s[left] & 1]--;
                left++;
            }
            ans += i - left + 1;
        }
        return ans;
    }
    @Test
    public void test(){
        char ch = '0';
        int res = ch & 1;  // 先将字符 '0' 转换为整数 48，然后与 1 进行按位与运算
        System.out.println(res);
    }
}
