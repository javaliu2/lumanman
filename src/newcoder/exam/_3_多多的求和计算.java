package newcoder.exam;

import org.junit.Test;

import java.util.*;

/**
 * 3.
 * 多多的求和计算
 * 多多路上从左到右有N棵树（编号1～N），其中第i个颗树有和谐值Ai。
 * 多多鸡认为，如果一段连续的树，它们的和谐值之和可以被M整除，那么这个区间整体看起来就是和谐的。
 * 现在多多鸡想请你帮忙计算一下，满足和谐条件的区间的数量。
 */
public class _3_多多的求和计算 {
    /**
     * passed 7/10，超时
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long m = in.nextLong();
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = in.nextInt();
        }
        long[] prefixSum = new long[n + 1];
        for (int i = 1; i <= n; ++i) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
        }
        // 枚举所有区间，计数满足条件的区间个数
        int cnt = 0;
        for (int i = 1; i <= n; ++i) {
            for (int j = i; j <= n; ++j) {
                long sum = prefixSum[j] - prefixSum[i - 1];
                if (sum % m == 0) {
                    cnt++;
                }
            }
        }
        System.out.println(cnt);
    }

    /**
     * G-bro优化
     * 目标区间满足: (prefixSum[r] - prefixSum[l-1]) % m == 0
     * 以上条件等价于 prefixSum[r] % m == prefixSum[l-1] % m
     * 所以只需要统计每个前缀和模m的余数的出现次数
     */
    public static void fun(int[] nums, int m) {
        int n = nums.length;;
        // key: prefixSum % m，value: 出现次数
        HashMap<Long, List<Integer>> modCount = new HashMap<>();
        List<Integer> base_zero = new ArrayList<>();
        base_zero.add(1);  // [0]表示(前缀和)模m余数为key的个数
        base_zero.add(-1);  // [1, ..] 表示在nums中的索引
        modCount.put(0L, base_zero);  // 初始化，前缀和为0的情况

        List<List<Integer>> ans = new ArrayList<>();
        long sum = 0;
        long cnt = 0;
        // 这个写法好巧妙
        for (int i = 0; i < n; ++i) {
            sum += nums[i];
            long mod = (sum % m + m) % m; // 防止负数取模
            List<Integer> v = modCount.get(mod);
            if (v != null) {
                cnt += v.get(0);
                for (int j = 1; j < v.size(); j++) {
                    List<Integer> sequence = new ArrayList<>();
                    for (int k = v.get(j) + 1; k <= i; k++) {
                        sequence.add(nums[k]);
                    }
                    ans.add(sequence);
                }
                v.add(i);
                v.set(0, v.get(0) + 1);
            } else {
                List<Integer> num = new ArrayList<>();
                num.add(1);
                num.add(i);
                modCount.put(mod, num);
            }
        }

        System.out.println(cnt);
        System.out.println(ans);
    }

    @Test
    public void testFun() {
        int[] nums = {1,2,3,6,4,1};
        int m = 3;
        fun(nums, m);
    }
}
