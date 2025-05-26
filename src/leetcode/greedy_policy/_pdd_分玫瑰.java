package leetcode.greedy_policy;

import java.util.Arrays;

/**
 * 【问题描述】：多多有n堆玫瑰，每堆玫瑰的支数用a[i]表示（1<=i<=n），现在多多想把
 * 这些玫瑰分给k个朋友。多多可以将某堆中拆出几只或者整堆送给朋友，每堆玫瑰可以被多次拆分
 * 赠送，但是不会把不同堆的玫瑰赠送给同一位朋友。为了保证公平，多多想让每位朋友分到的玫瑰数量
 * 是一样的。问，每位朋友能分到的玫瑰支数最大是多少？
 * 【G哥】：采用二分+贪心求解。二分查找最大值x，当最大值为x时，每堆玫瑰能贡献的人头数等于(a[i]/x)
 * sum_{1<=i<=n}(a[i]/n)，即n堆玫瑰的总贡献contribution_total，记为ct，判断ct ?> k
 * if ct > k:
 *  增大x
 * else:
 *  减少x
 */
public class _pdd_分玫瑰 {
    static boolean check(int[] a, int k, int x) {
        int cnt = 0;
        for (int ai : a) {
            cnt += ai / x;
        }
        return cnt >= k;
    }
    static int binarySearch(int[] a, int k) {
        int left = 1, right = Arrays.stream(a).max().getAsInt();
        int ans = 0;
        while (left <= right) {
//            int mid = left + (right-left) >> 1;  // bug, operator(+/-) 优先级 高于 >>, 因此这个式子等价于 right/2,会陷入死循环
            int mid = (left + right) / 2;
            if (check(a, k, mid)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }
    public static void main(String[] args) {
//        int[] a = {3, 3, 3};
        int[] a = {5, 8, 4};
        int k = 4;
        int res = binarySearch(a, k);
        System.out.println(res);
    }
}
