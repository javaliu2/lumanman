package leetcode.string;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class _2785将字符串中的元音字母排序 {
    public String sortVowels(String s) {
        List<Character> l = new ArrayList<>();
        List<Integer> idx = new ArrayList<>();
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (ch == 'a' || ch == 'A') {
                l.add(ch);
                idx.add(i);
            } else if (ch == 'e' || ch == 'E') {
                l.add(ch);
                idx.add(i);
            } else if (ch == 'i' || ch == 'I') {
                l.add(ch);
                idx.add(i);
            } else if (ch == 'o' || ch == 'O') {
                l.add(ch);
                idx.add(i);
            } else if (ch == 'u' || ch == 'U') {
                l.add(ch);
                idx.add(i);
            }
        }
        char[] chs = s.toCharArray();
        l.sort(Comparator.comparingInt(a -> a));
        int j = 0;
        for (int i : idx) {
            chs[i] = l.get(j++);
        }
        return new String(chs);
    }
}
