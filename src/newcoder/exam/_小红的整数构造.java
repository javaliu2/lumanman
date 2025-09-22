package newcoder.exam;

import java.util.Scanner;

public class _小红的整数构造 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        int b = in.nextInt();
        int c = in.nextInt();
        int[] res = fun(a, b, c);
        if (res == null) {
            System.out.println(-1);
        } else {
            System.out.println(res[0] + " " + res[1]);
        }
    }

    static int[] fun(int a, int b, int c) {
        if (a * b < c) {
            return null;
        }
        int num = 1, max = 9;
        while (--a > 0) {
            max = max * 10 + 9;
            num *= 10;
        }
        int num2 = num;
        for (; num <= max; ++num) {
            for (; num2 <= max; ++num2) {
                int r = num * num2;
                int t = r, bits = 0;
                while (t != 0) {
                    t /= 10;
                    bits++;
                }
                if (bits == c) {
                    return new int[]{num, num2};
                }
            }
        }
        return null;
    }
}
