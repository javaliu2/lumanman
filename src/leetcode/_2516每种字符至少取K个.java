package leetcode;

import java.util.HashMap;
import java.util.Map;

public class _2516每种字符至少取K个 {
    char[] s;
    int k;
    int cnt = Integer.MAX_VALUE;

    public int takeCharacters(String s, int kk) {
        Map<Character, Integer> cnts = new HashMap<>();
        this.k = kk;
        cnts.put('a', 0);
        cnts.put('b', 0);
        cnts.put('c', 0);
        char[] ss = s.toCharArray();
        this.s = ss;
        for (int i = 0; i < ss.length; i++) {
            int value = cnts.get(ss[i]) + 1;
            cnts.put(ss[i], value);
        }
        for (Map.Entry<Character, Integer> entry : cnts.entrySet()) {
            int v = entry.getValue();
            if (v < this.k) {
                return -1;
            }
        }
        int minute = 0;
        int[] path = new int[3];
        dfs(0, ss.length - 1, minute, path);
        return this.cnt;
    }

    void dfs(int start, int end, int minute, int[] path) {
        // 边界, abc均至少>=k
        if (path[0] >= k && path[1] >= k && path[2] >= k) {
            cnt = Math.min(cnt, minute);
            return;
        }
        if (start > end) {
            return;
        }
        path[this.s[start] - 'a']++;
        dfs(start + 1, end, minute + 1, path);
        path[this.s[start] - 'a']--;
        path[this.s[end] - 'a']++;
        dfs(start, end - 1, minute + 1, path);
        path[this.s[end] - 'a']--;
    }

    public static void main(String[] args) {
        _2516每种字符至少取K个 o = new _2516每种字符至少取K个();
        String s = "aabaaaacaabc";
        int k = 2;
        int res = o.takeCharacters(s, k);
        System.out.println(res);
    }
}
