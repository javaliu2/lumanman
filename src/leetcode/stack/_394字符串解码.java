package leetcode.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class _394字符串解码 {
    public String decodeString(String s) {
        Deque<Integer> multis = new ArrayDeque<>();
        Deque<Character> chars = new ArrayDeque<>();
        int num = 0;
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                // int multi = ch - '0'; // error for case: 100[leetcode]
                num = num * 10 + ch - '0';
            } else if (ch == '[') {
                multis.push(num);
                num = 0;
                chars.push(ch);
            } else {
                if (ch == ']') {
                    // chars出栈直到遇到'['
                    StringBuilder sb = new StringBuilder();
                    char cht;
                    while ((cht = chars.pop()) != '[') {
                        sb.insert(0, cht);
                    }
                    // multis出栈
                    int multi = multis.pop();
                    String t = sb.toString().repeat(multi);
                    // t入栈
                    for (int j = 0; j < t.length(); ++j) {
                        chars.push(t.charAt(j));
                    }
                } else {
                    chars.push(ch);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        char ch;
        while (!chars.isEmpty()) {
            ch = chars.pop();
            sb.insert(0, ch);
        }
        return sb.toString();
    }
}
