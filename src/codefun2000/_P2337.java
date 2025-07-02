package codefun2000;

import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

public class _P2337 {
    public static void fun() {
        Scanner scanner = new Scanner(System.in);
        Deque<Long> stack = new ArrayDeque<>();
        long sum = 0;  // 1、定义到while里面了，简单的错误
        while (scanner.hasNext()) {  // 2、会阻塞等待输入造成死循环
            long i = scanner.nextInt();
//            System.out.println(i);
            if (stack.isEmpty()) {
                stack.push(i);
                sum += i;
            } else {
                // 1、栈顶元素等于当前元素，pop栈顶元素，然后push当前元素*2
                if (stack.peek() == i) {
                    stack.pop();
                    stack.push(i * 2);
                    sum += i;
                } else if (sum == i) {  // 2、栈中所有元素之和等于当前元素，pop所有元素，push当前元素*2
                    while (!stack.isEmpty()) {
                        stack.pop();
                    }
                    stack.push(i * 2);
                    sum *= 2;
                } else {  // 3、非以上两种情况，直接入栈
                    stack.push(i);
                    sum += i;
                }
            }
        }
        while (!stack.isEmpty()) {
            System.out.printf("%d", stack.pop());
            Long top = stack.peek();
            if (top != null) {
                System.out.printf(" ");
            }
        }
    }

    public static void fun2() {
        Scanner scanner = new Scanner(System.in);
        List<Long> stack = new LinkedList<>();
        long[] s = Arrays.stream(scanner.nextLine().split(" ")).mapToLong(Long::valueOf).toArray();
        for (int ii = 0; ii < s.length; ++ii) {
            long currentNum = s[ii];
            if (stack.isEmpty()) {
                stack.add(currentNum);
            } else {
                while (true) {
                    // 单独判断栈顶一个元素的情况不是必须的，因为他是若干个元素的特例，即若干个元素个数为1
//                    if (!stack.isEmpty() && stack.get(stack.size() - 1).equals(currentNum)) {
//                        currentNum *= 2;
//                        stack.remove(stack.size() - 1); // pop
//                        continue; // 继续尝试合并这个新值
//                    }
                    // 1、当前元素等于栈中若干个元素之和
                    // 1.1 是否存在 若干个元素之和 等于当前元素
                    int j = stack.size() - 1;
                    long sum = 0;  // 计算栈中若干个元素之和
                    while (j >= 0 && sum + stack.get(j) <= currentNum) {
                        sum += stack.get(j);
                        j--;
                    }
                    // 1.2 存在若干个元素之和等于当前元素的值
                    if (sum == currentNum) {
                        // (j, size)元素出栈，
                        stack.subList(j + 1, stack.size()).clear();  // pop这若干个元素
                        currentNum *= 2;  // 需要循环判断，所以不直接入栈
                    } else {  // 2、不存在相等的情况，入栈
                        stack.add(currentNum);
                        break;
                    }
                }
            }
        }
        for (int i = stack.size() - 1; i >= 0; i--) {
            System.out.print(stack.get(i));
            if (i > 0) System.out.print(" ");
        }
        System.out.println();
    }

    /**
     * 还得是G哥啊，代码清晰明了。虽然说，大佬写的牛逼，但是我看不懂啊。
     * 分开判断逻辑更加清晰，栈顶元素和若干个元素和两种情况
     * @param args
     */
    public static void main_gbro(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long[] arr = Arrays.stream(scanner.nextLine().split(" ")).mapToLong(Long::parseLong).toArray();
        List<Long> stack = new LinkedList<>();

        for (long x : arr) {
            boolean merged = true;
            while (merged) {
                // 规则 1：与栈顶相同
                if (!stack.isEmpty() && stack.get(stack.size() - 1) == x) {
                    stack.remove(stack.size() - 1);  // pop
                    x = x * 2;  // 合并为新块
                    merged = true;
                    continue;  // 继续尝试合并新值
                }

                // 规则 2：与下方若干块之和相等
                long sum = 0;
                int j = stack.size() - 1;
                while (j >= 0 && sum + stack.get(j) <= x) {
                    sum += stack.get(j);
                    j--;
                }
                if (sum == x) {
                    // 清除从 j+1 到末尾的积木
                    stack.subList(j + 1, stack.size()).clear();
                    x = x * 2;
                    merged = true;
                    continue;  // 继续尝试合并新值
                }

                // 无法合并，入栈
                stack.add(x);
                merged = false;
            }
        }

        // 从顶到底输出
        for (int i = stack.size() - 1; i >= 0; i--) {
            System.out.print(stack.get(i));
            if (i > 0) System.out.print(" ");
        }
        System.out.println();
    }

    /**
     * 官方给出来的代码，好牛逼。不愧是ACM银牌
     */
    public static void main2() {
        // 创建一个 Scanner 对象用于读取输入
        Scanner s = new Scanner(System.in);

        // 读取一行输入，并将其拆分成字符串数组，然后转换为长整型数组
        long[] arr = Arrays.stream(s.nextLine().split(" ")).mapToLong(Long::valueOf).toArray();

        // 初始化一个栈，用于存储当前的积木块
        long[] stack = new long[arr.length];

        // 当前栈的有效元素数量
        int cur = 1;

        // 将第一个输入元素放入栈中
        stack[0] = arr[0];

        // 从第二个元素开始处理
        for (int i = 1; i < arr.length; ) {
            // 从栈顶开始向下遍历
            int j = cur - 1;
            long sum = 0; // 初始化当前和

            // 检查栈中元素的和是否小于等于当前元素
            while (j >= 0 && sum + stack[j] <= arr[i]) {
                sum += stack[j]; // 累加栈中的元素
                j--; // 向下移动到下一个元素
            }

            // 如果累加和等于当前元素
            if (sum == arr[i]) {
                // 将当前元素更新为两倍的累加和
                arr[i] = sum << 1; // 左移一位相当于乘以 2
                // cur = j + 2; // 不再使用的行
                cur = j + 1; // 更新当前有效元素的数量
            } else {
                // 如果不满足条件，则将当前元素入栈
                stack[cur++] = arr[i]; // 将当前元素放入栈中并更新 cur
                i++; // 处理下一个输入元素
            }
        }

        // 定义字符串数组，用于格式化输出
        String[] str = {"%d ", "%d"};

        // 从栈顶到栈底输出最终的结果
        for (int i = cur - 1; i >= 0; --i) {
            // 输出格式化字符串，最后一个元素输出后换行
            System.out.printf(i != 0 ? str[0] : str[1], stack[i]);
        }
    }
    @Test
    public void testRemove() {
        List<Integer> list = new ArrayList<>(Arrays.asList(0, 1, 2));
        list.remove(1);       // 删除下标1 → list = [0, 2]
        System.out.println(list);
        list = new ArrayList<>(Arrays.asList(0, 1, 2));
        list.remove((Integer)1); // 删除对象1 → list = [0]
        System.out.println(list);
    }

    public static void main(String[] args) {
        int[] arr = {55, 66, 121, 5, 5};
//        fun2();
        main2();
//        Scanner sc = new Scanner(System.in);
//        String[] s = sc.nextLine().split(" ");
//        System.out.println(s);
//        Stream<String> stream = Arrays.stream(s);
//        System.out.println(stream);
    }
}
