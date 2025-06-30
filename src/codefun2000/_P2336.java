package codefun2000;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class _P2336 {

    static class Pair {
        int x, y;  // x为上车站点，y为下车站点

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void solve() {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();  // 座位数
        int n = sc.nextInt();  // 站点数
        int x = sc.nextInt();  // 乘客数

        List<Pair> a = new ArrayList<>();
        // 读取x个乘客的上下站点
        for (int i = 0; i < x; ++i) {
            int first = sc.nextInt();
            int second = sc.nextInt();
            a.add(new Pair(first, second));
        }
        int ans = 0;
        // 枚举所有的乘客组合
        for (int i = 0; i < (1 << x); ++i) {
            int[] cnt = new int[n];  // 记录每个站点的乘客数量
            boolean ok = true;  // 用于标记当前组合是否合法
            int res = 0;  // 当前组合的座位利用数
            for (int j = 0; ok && j < x; ++j) {
                if ((i >> j & 1) == 1) {  // 第j个乘客乘车
                    // 记录该乘客在上下车站之间的乘客数量
                    for (int k = a.get(j).x; k < a.get(j).y; ++k) {
                        ++cnt[k];  // 增加对应站点的乘客数量
                    }
                    // 计算当前乘客的座位利用数
                    res += a.get(j).y - a.get(j).x;
                }
                // 检查是否有站点的乘客数量超过座位数
                for (int k = 0; k < n; k++) {
                    if (cnt[k] > m) {
                        ok = false;
                        break;
                    }
                }
            }
            if (ok) {
                ans = Math.max(ans, res);
            }
        }
        System.out.println(ans);
    }

    public static void main(String[] args) {
        solve();
    }
}
