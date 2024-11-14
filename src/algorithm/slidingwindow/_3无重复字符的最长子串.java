package algorithm.slidingwindow;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class _3无重复字符的最长子串 {
    /**
     * failed version
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring_my(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int l = 0, n = s.length(), max = 0;
        for (int r = 0; r < n; r++) {
            char ch = s.charAt(r);
            Integer v = map.get(ch);
            if (v == null) {
                map.put(ch, 1);
            } else {
                map.put(ch, v + 1);
            }
            while (v > 1) {
                int t = map.get(s.charAt(l));
                map.put(s.charAt(l), t - 1);
                l++;
                v = map.get(ch);
            }
            max = Math.max(max, r - l + 1);
        }
        return max;
    }

    /**
     * @author 灵茶山艾府
     * @param S
     * @return
     */
    public int lengthOfLongestSubstring(String S) {
        char[] s = S.toCharArray(); // 转换成 char[] 加快效率（忽略带来的空间消耗）
        int n = s.length;
        int ans = 0;
        int left = 0;
        boolean[] has = new boolean[128]; // 也可以用 HashSet<Character>，这里为了效率用的数组
        for (int right = 0; right < n; right++) {
            char c = s[right];
            // 如果窗口内已经包含 c，那么再加入一个 c 会导致窗口内有重复元素
            // 所以要在加入 c 之前，先移出窗口内的 c
            while (has[c]) { // 窗口内有 c
                has[s[left++]] = false; // 缩小窗口
            }
            has[c] = true; // 加入 c
            ans = Math.max(ans, right - left + 1); // 更新窗口长度最大值
        }
        return ans;
    }

    @Test
    public void test() {
        String s = "abba";
        lengthOfLongestSubstring(s);
    }
}
