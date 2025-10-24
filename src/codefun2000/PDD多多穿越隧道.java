package codefun2000;
import java.util.*;
public class PDD多多穿越隧道 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt(), d_position = -1;
            sc.nextLine();
            String s = sc.nextLine();
            char[] chs = new char[n + 1];
            for (int i = 1; i <= n; ++i) {
                chs[i] = s.charAt(i - 1);
                if (chs[i] == 'D') {
                    d_position = i;
                }
            }
            boolean ok = func(chs, n, d_position);
            if (ok) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
        sc.close();
    }

    private static boolean func(char[] chs, int n, int d_position) {
        if (d_position == 1 || d_position == n) {
            return true;
        }
        int right_positon_t = d_position;
        // 1、d的左边或者右边是否有.
        // 有的话，就能穿墙，并且可以左穿或者右穿；没有的话，寄，出不去
        while (d_position > 0 && (chs[d_position + 1] == '.' || chs[d_position - 1] == '.')) {
            // 1.1、尝试左穿，找到第一个#
            int t = d_position - 1;
            while (t > 0 && chs[t] == '.') {
                t--;
            }
            if (t == 0) {
                return true;
            }
            // t记录第一个#的位置，计算连续#构成的墙的左边界
            while (t > 0 && chs[t] == '#') {
                t--;
            }
            if (t == 0) {
                return true;
            }
            d_position = t;  // d穿越到t的位置
            if (d_position == 1) {
                return true;
            }
        }
        // 2、右穿
        d_position = right_positon_t;
        while (d_position <= n && (chs[d_position + 1] == '.' || chs[d_position - 1] == '.')) {
            // 2.1、尝试右穿，找到第一个#
            int t = d_position + 1;
            while (t <= n && chs[t] == '.') {
                t++;
            }
            if (t == n + 1) {
                return true;
            }
            // t记录第一个#的位置，计算连续#构成的墙的右边界
            while (t <= n && chs[t] == '#') {
                t++;
            }
            if (t == n + 1) {
                return true;
            }
            d_position = t;  // d穿越到t的位置
            if (d_position == n) {
                return true;
            }
        }
        return false;
    }
}
