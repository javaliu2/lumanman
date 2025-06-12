package leetcode.stack;

import java.util.*;

public class _20有效的括号 {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else {
                if (!stack.isEmpty()) {
                    char top_ch = stack.pop();
                    if (ch == ')' && top_ch != '(') {
                        return false;
                    }
                    if (ch == ']' && top_ch != '[') {
                        return false;
                    }
                    if (ch == '}' && top_ch != '{') {
                        return false;
                    }
                } else {
                    return false;  // 没有左符号匹配，返回false
                }
            }
        }
        return stack.isEmpty();
    }

    /**
     * @param s
     * @return
     * @author LCO
     */
    public boolean isValid_lo(String s) {
        int n = s.length();
        if (n % 2 == 1) {
            return false;
        }

        Map<Character, Character> pairs = new HashMap<Character, Character>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};
        Deque<Character> stack = new LinkedList<Character>();
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (pairs.containsKey(ch)) {
                if (stack.isEmpty() || stack.peek() != pairs.get(ch)) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }

}
