package leetcode.enumeration;

public class _2255统计是给定字符串前缀的字符串数目 {
    public int countPrefixes(String[] words, String s) {
        /**
         * 逐一判断
         */
        int cnt = 0;
        for (String word : words) {
            int len = word.length();
            if (len > s.length()) {
                continue;
            }
            int i = 0;
            while (i < len) {
                if (word.charAt(i) == s.charAt(i)) {
                    i++;
                } else {
                    break;
                }
            }
            if (i >= len) {
                cnt++;
            }
        }
        return cnt;
    }
}
