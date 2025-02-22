package leetcode.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _2506统计相似字符串对的数目 {
    public int similarPairs(String[] words) {
        // 预处理，将words中每一个word处理为每个字母只出现一次且按照字母序排列
        List<String> l = new ArrayList<>();
        for (String word : words) {
            int[] cnt = new int[26];
            for (int i = 0; i < word.length(); ++i) {
                cnt[word.charAt(i) - 'a'] = 1;
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 26; ++i) {
                if (cnt[i] == 1) {
                    sb.append((char) (i + 'a'));
                }
            }
            l.add(sb.toString());
        }
        for (String s : l) {
            System.out.println(s);
        }
        // 两两匹配check是否相等
        int ans = 0;
        for (int i = 0; i < l.size(); ++i) {
            for (int j = i + 1; j < l.size(); ++j) {
                if (l.get(i).equals(l.get(j))) {
                    ans++;
                }
            }
        }
        return ans;
    }

    /**
     * @author LeetCode Official
     * @param words
     * @return
     */
    public int similarPairs_lo(String[] words) {
        int res = 0;
        Map<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        for (String word : words) {
            int state = 0;
            int length = word.length();
            for (int i = 0; i < length; i++) {
                char c = word.charAt(i);
                state |= 1 << (c - 'a');
            }
            res += cnt.getOrDefault(state, 0);
            cnt.put(state, cnt.getOrDefault(state, 0) + 1);
        }
        return res;
    }
}
