package leetcode.string;

public class _1957删除字符使字符串变好 {
    public String makeFancyString(String s) {
        StringBuilder sb = new StringBuilder();
        int cnt = 0;  // 记录当前追加字符的个数
        for (int i = 0; i < s.length(); ++i) {
            char currentChar = s.charAt(i);
            if (i - 1 >= 0 && s.charAt(i - 1) == currentChar) {
                cnt++;
            } else {
                cnt = 1;
            }
            if (cnt < 3) {
                sb.append(currentChar);
            }
        }
        return sb.toString();
    }
}
