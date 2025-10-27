package codefun2000;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PDD幸运字符串 {
    /**
     * @author 塔子哥
     * @param str
     * @return
     */
    static int maxLuckyLength(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        // 从大到小枚举半长 k
        for (int k = n / 2; k >= 1; k--) {
            int m = n - k;
            int[] bad = new int[m]; // bad[p]=1 表示 s[p] 与 s[p+k] 已知且不同
            for (int p = 0; p < m; p++) {
                char a = s[p], b = s[p + k];
                if (a != '?' && b != '?' && a != b) bad[p] = 1;
            }
            // 长度为 k 的滑动窗口检查是否存在和为 0 的区间
            int curr = 0;  // 记录长度为k的窗口内元素之和
            for (int i = 0; i < k; i++) curr += bad[i];
            if (curr == 0) return 2 * k;
            for (int i = 1; i <= m - k; i++) {  // 枚举剩下窗口的左边界
                curr += bad[i + k - 1] - bad[i - 1];  // 移动窗口，添加最右侧的新元素，移除最左侧的旧元素
                if (curr == 0) return 2 * k;
            }
        }
        return 0; // 空串
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        System.out.println(maxLuckyLength(s));
    }
}
