package newcoder.exam;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class _5_符合要求的最长01字符串 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        int n = s.length();
        int max = -1;
        for (int i = 0; i < n; ++i) {
            if (s.charAt(i) == '0') {
                Deque<Character> stack = new ArrayDeque<>();
                stack.push('0');
                int j = i + 1;
                while (j < n) {
                    if (s.charAt(j) == '1') {
                        // 1）栈为空 或 栈顶元素为'1'，子串结束
                        if (stack.isEmpty() || stack.peek() == '1') {
                            break;
                        } else {
                            // 2) 栈非空，栈顶元素为'0'，pop，继续判断下一个字符
                            stack.pop();
                            ++j;
                        }
                    } else {
                        // 入栈
                        stack.push('0');
                        ++j;
                    }
                }
                // [i, j)为符合要求的子串
                if (stack.isEmpty()) {
                    max = Math.max(j - i, max);
                    i = j;
                }
            }
        }
        if (max == -1) {
            System.out.println(0);
        } else {
            System.out.println(max);
        }
    }
}
