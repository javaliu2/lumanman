package algorithm.backtrack;

import java.util.ArrayList;
import java.util.List;

public class _17_电话号码的字母组合 {
    class Solution {
        String[] mapping;
        List<String> ans;
        public List<String> letterCombinations(String digits) {
            mapping = new String[10];
            mapping[2] = "abc";
            mapping[3] = "def";
            mapping[4] = "ghi";
            mapping[5] = "jkl";
            mapping[6] = "mno";
            mapping[7] = "pqrs";
            mapping[8] = "tuv";
            mapping[9] = "wxyz";
            ans = new ArrayList<>();
            if (digits == null || digits.length() == 0) {
                return ans;
            }
            StringBuffer sb = new StringBuffer();
            dfs(0, sb, digits);
            return ans;
        }
        void dfs(int current, StringBuffer sb, String digits) {
            if (current == digits.length()) {
                ans.add(new String(sb));
                return;
            }
            int idx = digits.charAt(current) - '0';
            for (char ch : mapping[idx].toCharArray()) {
                sb.append(ch);
                dfs(current + 1, sb, digits);
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }
}
