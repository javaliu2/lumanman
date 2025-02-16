package leetcode.greedy_policy;

public class _3458选择K个互不重叠的特殊子字符串 {
    public boolean maxSubstringLength(String s, int k) {
        /**
         * 符合要求的子字符串中的字符在s中只出现一次
         * k个子字符串彼此之间字符互不相同
         * 策略：判断s中是否有k个计数为1的字符
         * 通过978/986个测试用例
         */
        int[] cnts = new int[26];
        for (char ch : s.toCharArray()) {
            cnts[ch - 'a']++;
        }
        for (int cnt : cnts) {
            if (cnt == 1) {
                k--;
            }
        }
        return k <= 0;
    }
}
