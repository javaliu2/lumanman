package newcoder.exam;

import java.util.Scanner;

/**
 * 多多在纸上写下了一个整数序列，他可以对任意一个数进行+1操作，每个数的操作次数不限。
 * 他想知道，他至少要操作多少次，可以使这个序列变成一个单峰序列。
 * 单峰序列定义：假设序列为 a1,a2,...,an. 存在一个数 i(1<i<n)，
 * 满足 a1<a2<a3<...<ai>a(i+1)>a(i+2)>...>an，即这个序列先严格递增再严格递减.
 * 思路：
 * 1）枚举每一个顶峰，计算其开销
 * 2）预处理：
 * 设b[n]为满足前缀严格递增条件时，每一个位置上最小的数（即至少为多少满足严格递增）
 * f[n]为达到这样条件所需的开销（即进行多少次+1操作）；
 * 同理，c[n]为满足后缀严格递增条件时，数组各个位置的值（等价于前缀严格递减）
 * g[n]为其开销
 * 3）举例说明：
 * a: 3 1 4 2 5
 * b: 3 4 5 6 7
 * f: 0 3 4 8 10
 * c: 9 8 7 6 5
 * g: 20 14 7 4 0
 * 当i=1时，f[1]+g[1]-min(b[1]-a[1], c[1]-a[1])=3+14-3=14
 * 因为b和c是满足条件时，对应数字至少为多少，所以当顶峰索引设置为1时，
 * 峰值为max(b[1], c[1])，在i==1时，max(4, 8)=8，这样才能保证1为顶峰，
 * 如果取4的话，序列为3 4 7 6 5，满足单峰序列的定义，但是顶峰索引不是1，违反题目预设
 * 所以为什么要减去min(b[i]-a[i], c[i]-a[i])就是减去较小值作为顶峰值的开销
 * 4）bug记录: 数组类型使用int，导致数据溢出，改为long解决
 */
public class _pdd0803_3_单峰序列 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        long res = fun(nums);
        System.out.println(res);
        sc.close();
    }

    private static long fun(int[] nums) {
        // 1、预处理
        int n = nums.length;
        long[] b = new long[n];
        long[] f = new long[n];
        long[] c = new long[n];
        long[] g = new long[n];
        b[0] = nums[0];
        c[n - 1] = nums[n - 1];
        // 1.1 计算前缀严格递增
        for (int i = 1; i < n; i++) {
            b[i] = Math.max(b[i - 1] + 1, nums[i]);
            f[i] = f[i - 1] + b[i] - nums[i];
        }
        // 1.2 计算后缀严格递减
        for (int i = n - 2; i >= 0; i--) {
            c[i] = Math.max(c[i + 1] + 1, nums[i]);
            g[i] = g[i + 1] + c[i] - nums[i];
        }
        // 2、枚举顶峰，计算开销
        long ans = Long.MAX_VALUE;
        for (int i = 1; i < n - 1; i++) {
            long cost = f[i] + g[i] - Math.min(b[i] - nums[i], c[i] - nums[i]);
            ans = Math.min(ans, cost);
        }
        return ans;
    }
}
