package algorithm.backtrack;

import java.util.*;

public class _22括号生成 {
    /**
     * 5ms, 43.8MB
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        int left_remain = n, right_remain = n;
        List<String> ans = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        dfs(0, sb, left_remain, right_remain, 2 * n, ans);
        return ans;
    }

    private void dfs(int current, StringBuilder sb, int left_remain, int right_remain, int n, List<String> ans) {
        if (current == n) {
            Deque<Character> stack = new ArrayDeque<>();
            int i;
            for (i = 0; i < sb.length(); i++) {
                char ch = sb.charAt(i);
                if (ch == '(') {
                    stack.push('(');
                } else {
                    if (stack.isEmpty() || stack.peek() != '(') {
                        break;
                    } else {
                        stack.pop();
                    }
                }
            }
            if (stack.isEmpty() && i >= sb.length()) {
                ans.add(sb.toString());
            }
            return;
        }
        // 加左括号
        if (left_remain - 1 >= 0) {
            sb.append('(');
            dfs(current + 1, sb, left_remain - 1, right_remain, n, ans);
            sb.deleteCharAt(sb.length() - 1);
        }
        // 加右括号
        if (right_remain - 1 >= 0) {
            sb.append(')');
            dfs(current + 1, sb, left_remain, right_remain - 1, n, ans);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /**
     * 生成所有解，最后判断括号序列是否有效，学习其判断是否有效的方法
     * 2ms, 41.9MB
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis_lo(int n) {
        List<String> combinations = new ArrayList<String>();
        generateAll(new char[2 * n], 0, combinations);
        return combinations;
    }

    public void generateAll(char[] current, int pos, List<String> result) {
        if (pos == current.length) {
            if (valid(current)) {
                result.add(new String(current));
            }
        } else {
            current[pos] = '(';
            generateAll(current, pos + 1, result);
            current[pos] = ')';
            generateAll(current, pos + 1, result);
        }
    }

    public boolean valid(char[] current) {
        int balance = 0;
        for (char c : current) {
            if (c == '(') {
                ++balance;
            } else {
                --balance;
            }
            if (balance < 0) {
                return false;
            }
        }
        return balance == 0;
    }

    /**
     * 回溯法+剪枝，在生成序列过程中，只生成有效的括号序列
     * 1ms, 42.2MB
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis_lo2(int n) {
        List<String> ans = new ArrayList<String>();
        backtrack(ans, new StringBuilder(), 0, 0, n);
        return ans;
    }

    public void backtrack(List<String> ans, StringBuilder cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            ans.add(cur.toString());
            return;
        }
        if (open < max) {
            cur.append('(');
            backtrack(ans, cur, open + 1, close, max);
            cur.deleteCharAt(cur.length() - 1);
        }
        // 右括号的数量必须小于等于左括号的数量（由于open和close从0开始计数，所以是'<'）
        if (close < open) {
            cur.append(')');
            backtrack(ans, cur, open, close + 1, max);
            cur.deleteCharAt(cur.length() - 1);
        }
    }
}

class Solution_22 {
    Map<Integer, List<String>> memo = new HashMap<>();

    public List<String> generate(int n) {
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        List<String> res = new ArrayList<>();
        if (n == 0) {
            res.add("");
        } else {
            // i表示括号的对数（最多n-1对，因为剩下的一对是写死的，即style: (a)b ）
            for (int i = 0; i < n; i++) {
                List<String> lefts = generate(i);           // a 的所有可能
                List<String> rights = generate(n - 1 - i);   // b 的所有可能

                for (String left : lefts) {
                    for (String right : rights) {
                        res.add("(" + left + ")" + right);   // 组成 (a)b
                    }
                }
            }
        }
        memo.put(n, res);
        return res;
    }

    public List<String> generateParenthesis(int n) {
        return generate(n);
    }
}

