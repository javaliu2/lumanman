package newcoder.exam;

import java.util.Scanner;

/**
 * 1.多多的魔术盒子
 * 多多鸡有N个魔术盒子（编号1～N），其中编号为i的盒子里有i个球。
 * 多多鸡让皮皮虾每次选择一个数字X（1 <= X <= N），多多鸡就会把球数量大于等于X个的盒子里的球减少X个。
 * 通过观察，皮皮虾已经掌握了其中的奥秘，并且发现只要通过一定的操作顺序，可以用最少的次数将所有盒子里的球变没。
 * 那么请问聪明的你，是否已经知道了应该如何操作呢？
 * 答：对1到N的每个数字，用其二进制表示进行处理。每次选一个X=2^k，把所有球数>=X的盒子都减去X，
 * 这样相当于“清掉”所有球数中包含第k位为1的部分。
 * 举例说明：
 * N = 5，对每个盒子编号 i：
 * 编号	球数(i)	二进制
 * 1	 1	   0001
 * 2	 2	   0010
 * 3	 3	   0011
 * 4	 4	   0100
 * 5	 5	   0101
 *  第一步：选择 X=1：所有球数 ≥1 的盒子都 -1，相当于处理了所有末位为1的球
 *  第二步：选择 X=2：所有球数 ≥2 的盒子都 -2，处理了第二位为1的部分
 *  第三步：选择 X=4：所有球数 ≥4 的盒子都 -4，处理了第三位为1的部分
 * 最终所有球清空
 */
public class _2020pdd_1_多多的魔法盒子 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 最少操作数等于N的二进制位数
        int t = in.nextInt();
        while (t-- > 0) {
            int n = in.nextInt();
            int res = fun(n);
            System.out.println(res);
        }
    }
    static int fun(int n) {
        int base = 1, cnt = 0;
        while (base <= n) {
            base <<= 1;
            cnt++;
        }
        return cnt;
    }
}
