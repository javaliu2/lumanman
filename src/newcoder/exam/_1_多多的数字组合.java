package newcoder.exam;

import java.util.Scanner;

/**
 * 1.
 * 多多的数字组合
 * 多多君最近在研究某种数字组合：
 * 定义为：每个数字的十进制表示中(0~9)，每个数位各不相同且各个数位之和等于N。
 * 满足条件的数字可能很多，找到其中的最小值即可。
 * 多多君还有很多研究课题，于是多多君找到了你--未来的计算机科学家寻求帮助。
 */
public class _1_多多的数字组合 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        if (n <= 9) {
            System.out.println(n);
        } else if (n > 45) {
            System.out.println(-1);
        } else {
            boolean[] visited = new boolean[10];
            fun(n, visited);
            if (max == Integer.MAX_VALUE) {
                System.out.println(-1);
            } else {
                System.out.println(max);
            }
        }
    }

    static int max = Integer.MAX_VALUE;

    static void fun(int n, boolean[] visited) {
        if (n < 0) {
            return;
        } else if (n == 0) {
            int num = 0;
            for (int i = 1; i <= 9; ++i) {
                if (visited[i]) {
                    num *= 10;
                    num += i;
                }
            }
            max = Math.min(max, num);
        }
        for (int i = 1; i <= 9; ++i) {
            if (!visited[i]) {
                visited[i] = true;
                fun(n - i, visited);
                visited[i] = false;
            }
        }
    }
}
