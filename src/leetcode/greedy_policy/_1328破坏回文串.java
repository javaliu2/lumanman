package leetcode.greedy_policy;

public class _1328破坏回文串 {
        /**
         * @idea LeetCode Official
         */
        public String breakPalindrome(String palindrome) {
            int n = palindrome.length();
            if (n == 1) {
                return "";
            }
            int idx = -1;
            for (int i = 0; i < n / 2; ++i) {
                if (palindrome.charAt(i) != 'a') {
                    idx = i;
                    break;
                }
            }
            char[] chs = palindrome.toCharArray();
            if (idx != -1) {
                chs[idx] = 'a';
            } else {
                chs[n-1] = 'b';
            }
            return new String(chs);
        }
}
