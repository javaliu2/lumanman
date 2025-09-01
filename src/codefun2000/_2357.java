package codefun2000;

import java.util.Scanner;

public class _2357 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int[] s1 = solve_input(s);
        s = sc.nextLine();
        int[] s2 = solve_input(s);
        String operator = sc.nextLine();
        String res = null;
        if (operator.equals("*")) {
            res = fun2(s1, s2);
        } else {
            res = fun(s1, s2, operator);
        }
        System.out.println(res);
        sc.close();
    }

    static int[] solve_input(String s1) {
        String s11 = s1.substring(1, s1.length() - 1);
        String[] s11_arr = s11.split(" ");
        int[] s1_num = new int[s11_arr.length];
        for (int i = 0; i < s11_arr.length; ++i) {
            s1_num[i] = Integer.parseInt(s11_arr[i]);
        }
        return s1_num;
    }

    static String fun2(int[] s1, int[] s2) {
        int n1 = s1.length - 1, n2 = s2.length - 1;
        int[] res = new int[n1 * n2 + 1];
        int n = res.length - 1;
        for (int i = 0; i <= n2; ++i) {
            int xishu = s2[i], zhishu = n2 - i;
            for (int j = 0; j <= n1; ++j) {
                int t = xishu * s1[j];
                int res_zhishu = (n1 - j) + zhishu;  // 指数相加，系数相乘
                res[n - res_zhishu] += t;
            }
        }

        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i <= n && res[i] == 0) {
            i++;
        }
        while (i <= n) {
            sb.append(res[i]);
            if (i + 1 <= n) {
                sb.append(" ");
            }
            i++;
        }
        return "[" + sb.toString() + "]";
    }

    static String fun(int[] s1, int[] s2, String operator) {
        int n1 = s1.length - 1, n2 = s2.length - 1;
        // 假设 s2 更短
        if (n1 < n2) {
            int[] t = s1;
            s1 = s2;
            s2 = t;
        }
        // 遍历 s2, 将结果保存在 s1
        for (int i = 0; i <= n2; ++i) {
            // 指数：n2-i == n1-j
            // 对应的s1中的j为 n1-n2+i
            int j = n1 - n2 + i;
            if (operator.equals("+")) {
                s1[j] += s2[i];
            } else if (operator.equals("-")) {
                s1[j] -= s2[i];
            }
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i <= n1 && s1[i] == 0) {
            i++;
        }
        while (i <= n1) {
            sb.append(s1[i]);
            if (i + 1 <= n1) {
                sb.append(" ");
            }
            i++;
        }
        return "[" + sb.toString() + "]";
    }
}
