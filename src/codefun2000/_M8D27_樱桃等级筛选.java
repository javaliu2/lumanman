package codefun2000;

import java.util.*;

public class _M8D27_樱桃等级筛选 {
    static int[] nums;
    static int m, n;
    static int[] prefix_sum;
    static double ans = Double.MAX_VALUE;
    static List<int[]> ans_seg;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        nums = new int[n];
        prefix_sum = new int[n + 1];
        for (int i = 0; i < n; ++i) {
            nums[i] = sc.nextInt();
            prefix_sum[i + 1] = prefix_sum[i] + nums[i];
        }
        // prefix_sum中的下标表示的是第几个元素，而不是该元素在nums中的下标
        List<int[]> segments = new ArrayList();
        dfs(0, segments);
        for (int i = 0; i < m; ++i) {
            int[] seg = ans_seg.get(i);
            int len = seg[1] - seg[0] + 1;
            if (i + 1 < m) {
                System.out.print(len + " ");
            } else {
                System.out.println(len);
            }
        }
        sc.close();
    }

    // 枚举所有方案，判断记录标准差最小的方案
    static void dfs(int start, List<int[]> segments) {
        if (segments.size() > m) {
            return;
        }
        if (start == n && segments.size() == m) {
            int[] mm = new int[m];
            for (int i = 0; i < m; ++i) {
                int[] segment = segments.get(i);
                mm[i] = prefix_sum[segment[1] + 1] - prefix_sum[segment[0]];
            }
            double res = standardDeviation(mm);
            if (res < ans) {
                ans = res;
                ans_seg = new ArrayList<>(segments);
            }
        }
        for (int end = start; end < n; ++end) {
            int[] segment = new int[]{start, end};
            segments.add(segment);
            dfs(end + 1, segments);
            segments.remove(segments.size() - 1);
        }
    }

    static double standardDeviation(int[] nums) {
        int sum = 0, n = nums.length;
        for (int num : nums) {
            sum += num;
        }
        double average = sum * 1.0 / n, sum_d = 0.0;
        for (int num : nums) {
            sum_d += Math.pow(num - average, 2);
        }
        return Math.sqrt(sum_d / n);
    }
}
