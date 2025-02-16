package leetcode.enumeration;

public class _3456找出长度为K的特殊子字符串 {
    public boolean hasSpecialSubstring(String s, int k) {
        /**
         * 暴力枚举所有的长度为k的子字符串，判断是否满足条件
         */
        char[] ch = s.toCharArray();
        int n = ch.length;
        for (int i = 0; i + k <= n; i++) {
            // 以i为起点，长度为k的子字符串
            int j;
            boolean flag = false;
            for (j = i + 1; j < i + k; j++) {
                // 判断是否由唯一字符构成
                if (ch[j] != ch[j - 1]) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                continue;
            }
            // 判断该子字符串前后字符是否与构成该子字符串的字符相同
            // 前或者后有字符，需要判断，否则不用判断
            if (i - 1 >= 0 && ch[i - 1] == ch[j - 1]) {
                continue;
            }
            if (j < n && ch[j - 1] == ch[j]) {
                continue;
            }
            return true;
        }
        return false;
    }
}