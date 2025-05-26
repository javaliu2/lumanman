package leetcode.greedy_policy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 【问题描述】：给定一个长度为n的数组p=[p1, p2, ..., p_n]，将其划分为连续的k个非空子段
 *  1）划分方案的价值定义为每个子段最大值之和
 *  2）输出：所有划分方案中的最大价值；有多少种划分方式可以达到这个最大值（对998244353取模）
 */
public class _pdd_区间划分 {
    static final long MOD = 998244353;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] p =new int[n];
        for (int i = 0; i < n; i++) {
            p[i] = sc.nextInt();
        }
        // 找出前k最大的值
        int[] sorted = Arrays.copyOf(p, n);
        Arrays.sort(sorted);
        int threshold = sorted[n - k];

        long maxValue = 0;
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (p[i] >= threshold) {
                if (positions.size() < k) {
                    maxValue += p[i];
                    positions.add(i);
                }
            }
        }
        long ways = 1;
        for (int i = 1; i < positions.size(); ++i) {
            ways = (ways * (positions.get(i) - positions.get(i-1))) % MOD;
        }
        System.out.println(maxValue);
        System.out.println(ways);
    }
}
