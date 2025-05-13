package leetcode.dynamic_programming;

import java.util.ArrayList;
import java.util.List;

public class _3335字符串转换后的长度I {

    /**
     * 超时，passed 502/824
     */
    public int lengthAfterTransformations_simulation(String s, int t) {
        List<List<Character>> chss = new ArrayList<>();
        for (char ch : s.toCharArray()) {
            List<Character> chs = new ArrayList<>();
            chs.add(ch);
            chss.add(chs);
        }
        // 模拟t次
        while (t-- > 0) {
            // 针对chss中每一个字符序列chs进行转换操作
            for (int i = 0; i < chss.size(); i++) {
                List<Character> chs = chss.get(i);
                List<Character> new_chs = new ArrayList<>();
                for (char ch : chs) {
                    if (ch != 'z') {
                        new_chs.add(++ch);
                    } else {
                        new_chs.add('a');
                        new_chs.add('b');
                    }
                }
                chss.set(i, new_chs);
            }
        }
        // 遍历chss，计数
        int ans = 0, MOD = (int) (Math.pow(10, 9) + 7);
        for (List<Character> chs : chss) {
            ans = (ans + chs.size()) % MOD;
        }
        return ans;
    }

    public int lengthAfterTransformations(String s, int t) {
        int[] cnt = new int[26];
//        int[] nxt = new int[26];  // 错误，这样的话，line 62赋值之后，cnt和nxt引用同一个对象，在计算新一轮值的时候会有问题
        // 下标idx 0-25分别映射到字符a-z，[idx]表示对应字符出现次数
        for (char ch : s.toCharArray()) {
            cnt[ch - 'a']++;
        }
        int MOD = (int) (Math.pow(10, 9) + 7);
        for (int i = 1; i <= t; ++i) {
            int[] nxt = new int[26];
            for (int idx = 0; idx < 26; ++idx) {
                if (idx == 0) {
                    nxt[idx] = cnt[25];
                } else if (idx == 1) {
                    nxt[idx] = (cnt[25] + cnt[0]) % MOD;  // attention 2，else 上溢
                } else {
                    nxt[idx] = cnt[idx - 1];
                }
            }
            cnt = nxt;
        }
        int ans = 0;
        for (int idx = 0; idx < 26; ++idx) {
            ans = (cnt[idx] + ans) % MOD;
        }
        return ans;
    }

    public int lengthAfterTransformations_oneArray(String s, int t) {
        int[] cnt = new int[26];
        // 下标idx 0-25分别映射到字符a-z，[idx]表示对应字符出现次数
        for (char ch : s.toCharArray()) {
            cnt[ch - 'a']++;
        }
        int MOD = (int) (Math.pow(10, 9) + 7);
        for (int i = 1; i <= t; ++i) {
            int temp_z = cnt[25];
            for (int idx = 25; idx >= 2; --idx) {  // 从后往前算
                cnt[idx] = cnt[idx - 1];
            }
            cnt[1] = (temp_z + cnt[0]) % MOD;
            cnt[0] = temp_z;
        }
        int ans = 0;
        for (int idx = 0; idx < 26; ++idx) {
            ans = (cnt[idx] + ans) % MOD;
        }
        return ans;
    }
}
