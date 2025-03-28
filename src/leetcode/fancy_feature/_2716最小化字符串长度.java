package leetcode.fancy_feature;

public class _2716最小化字符串长度 {
    public int minimizedStringLength(String s) {
        // 脑筋急转弯，结果就是字符串s中不同字符的种类数
        int[] cnt = new int[26];
        int ans = 0;
        for (char ch : s.toCharArray()) {
            if (cnt[ch - 'a'] == 0) {
                cnt[ch - 'a'] = 1;
                ans++;
            }
        }
        return ans;
    }
    public int minimizedStringLength_bitOperation(String s) {
        int mask = 0;
        for (char c : s.toCharArray()) {
            mask |= 1 << (c - 'a'); // 把 c-'a' 加到集合中
        }
        return Integer.bitCount(mask); // 集合的大小
    }
}
