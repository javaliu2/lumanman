package codefun2000;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class _M8D27_冬季暖系统温度稳定性分析 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = sc.nextInt();
        }
        List<int[]> res = fun(nums);
        if (res.size() > 0) {
            int zero_len = res.get(0)[1] - res.get(0)[0] + 1;
            int i = 0;
            while (i < res.size()) {
                int len = res.get(i)[1] - res.get(i)[0] + 1;
                if (zero_len == len) {
                    System.out.println(res.get(i)[0] + " " + res.get(i)[1]);
                    ++i;
                } else {
                    break;
                }
            }
        }
        sc.close();
    }

    static List<int[]> fun(int[] nums) {
        List<int[]> l = new ArrayList<>();
        // 判断以i为起点的满足条件的最长连续序列
        int i = 0, n = nums.length, j;
        while (i < n) {
            j = i;
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            while (j < n && nums[j] >= 18 && nums[j] <= 24) {
                min = Math.min(min, nums[j]);
                max = Math.max(max, nums[j]);
                if (max - min > 4) {
                    break;
                }
                j++;
            }
            if (i == j) {
                i++;
            } else {
                // [i,j)是满足条件的最长序列的两端
                l.add(new int[]{i, j - 1});
                i = j;
            }
        }
        // 对l排序
        l.sort((a, b) -> {
            if (b[1] - b[0] + 1 == a[1] - a[0] + 1) {
                return a[0] - b[0];  // 长度一致，索引升序
            }
            return b[1] - b[0] + 1 - (a[1] - a[0] + 1);  // 序列长度降序
        });
        return l;
    }
}
class MainOfficial {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        Integer nObj = fs.nextInt();
        if (nObj == null) return;
        int N = nObj;
        List<Integer> arrList = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            Integer val = fs.nextInt();
            if (val == null) break;
            arrList.add(val);
        }
        int Nreal = arrList.size();
        int[] a = new int[Nreal];
        for (int i = 0; i < Nreal; i++) a[i] = arrList.get(i);

        Deque<Integer> minq = new ArrayDeque<>(); // 存下标，值单调递增
        Deque<Integer> maxq = new ArrayDeque<>(); // 存下标，值单调递减
        int l = 0, bestLen = 0;
        List<int[]> ans = new ArrayList<>();

        for (int r = 0; r < Nreal; r++) {
            int v = a[r];
            // 不在 [18,24]，重置窗口
            if (v < 18 || v > 24) {
                minq.clear();
                maxq.clear();
                l = r + 1;
                continue;
            }
            // 插入 r，维护单调性
            while (!minq.isEmpty() && a[minq.peekLast()] >= v) minq.pollLast();
            minq.addLast(r);
            while (!maxq.isEmpty() && a[maxq.peekLast()] <= v) maxq.pollLast();
            maxq.addLast(r);

            // 收缩直到满足 max - min <= 4
            while (!minq.isEmpty() && !maxq.isEmpty() && (a[maxq.peekFirst()] - a[minq.peekFirst()] > 4)) {
                if (minq.peekFirst() == l) minq.pollFirst();
                if (maxq.peekFirst() == l) maxq.pollFirst();
                l++;
            }

            int len = r - l + 1;
            if (len > 0) {
                if (len > bestLen) {
                    bestLen = len;
                    ans.clear();
                    ans.add(new int[]{l, r});
                } else if (len == bestLen) {
                    ans.add(new int[]{l, r});
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int[] p : ans) {
            sb.append(p[0]).append(' ').append(p[1]).append('\n');
        }
        System.out.print(sb.toString());
    }

    // 简单快速输入
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        Integer nextInt() throws IOException {
            int c, sgn = 1, x = 0;
            do {
                c = read();
                if (c == -1) return null;
            } while (c <= ' ');

            if (c == '-') { sgn = -1; c = read(); }
            for (; c > ' '; c = read()) {
                if (c < '0' || c > '9') break;
                x = x * 10 + (c - '0');
            }
            return x * sgn;
        }
    }
}
