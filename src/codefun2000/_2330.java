package codefun2000;

import org.junit.Test;

import java.util.*;

public class _2330 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 使用栈
        int n = sc.nextInt();
        List<Character> chs = new ArrayList<>();
        sc.nextLine();
        String s = sc.nextLine();
        String[] ss = s.split(" ");
        for (int i = 0; i < n; ++i) {
            chs.add(ss[i].charAt(0));
        }
        List<Character> res = fun(chs, n);
        for (int i = 0; i < res.size(); ++i) {
            if (i + 1 >= res.size()) {
                System.out.println(res.get(i));
            } else {
                System.out.print(res.get(i) + " ");
            }
        }
        sc.close();
    }

    static List<Character> fun(List<Character> chs, int n) {
        List<Character> res = new ArrayList<>();
        Deque<Character> stack = new ArrayDeque<>();
        int count = 0;  // 记录栈顶字符连续出现的次数
        for (char ch : chs) {
            if (!stack.isEmpty()) {
                if (stack.peek() == ch) {
                    count++;
                } else {
                    count = 1;
                }
            } else {
                count = 1;
            }
            stack.push(ch);
            if (count == 3) {
                while (count-- > 0) {
                    stack.pop();
                }
                // 统计栈顶字符最大连续数，x = 1 or 2
                count = countTopConsecutive(stack);
            }
        }

        Iterator<Character> it = stack.descendingIterator(); // 从栈底向上遍历
        while (it.hasNext()) {
            res.add(it.next());
        }

        if (res.size() == 0) {
            res.add('0');
        }
        return res;
    }

    static int countTopConsecutive(Deque<Character> stack) {
        if (stack.isEmpty()) return 0;
        char top = stack.peek();
        int count = 0;
        for (char ch : stack) {
            if (ch == top) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    @Test
    public void test() {
        Scanner sc = new Scanner(System.in);
        Deque<Character> s = new ArrayDeque<>();
        s.push('a');  // addFirst
        s.push('b');
        s.push('c');
        for (char ch : s) {  // 栈顶到栈底，合理
            System.out.println(ch);
        }
        System.out.println("---");
        Iterator<Character> iter = s.descendingIterator();  // 从栈底到栈顶
        while (iter.hasNext()) {
            char ch = iter.next();
            System.out.println(ch);
        }
    }
}

class OfficialSolution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine());  // 读取牌的数量
        String[] a = scanner.nextLine().split(" ");  // 读取牌号，并使用空格分隔成一个字符串数组
        List<String> st = new ArrayList<>();  // 创建一个动态数组，用于存储当前的牌序列

        // 遍历每一张牌
        for (String x : a) {
            st.add(x);  // 将当前牌加入列表
            // 检查列表中最后三张牌是否相同
            while (st.size() >= 3 && st.get(st.size() - 1).equals(st.get(st.size() - 2)) && st.get(st.size() - 2).equals(st.get(st.size() - 3))) {
                // 如果存在连续的三张相同牌，则进行消除
                for (int j = 0; j < 3; j++) {
                    st.remove(st.size() - 1);  // 消除最后三张相同的牌
                }
            }
        }

        // 检查最终的牌序列
        if (st.isEmpty()) {
            System.out.println(0);  // 如果列表为空，表示所有牌都被消除，输出 0
        } else {
            System.out.println(String.join(" ", st));  // 否则，将剩余的牌以空格分隔并输出
        }

        scanner.close();  // 关闭扫描器
    }
}
