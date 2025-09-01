package codefun2000;

import java.util.Scanner;

public class _P2356 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = sc.nextInt();
        }
        int[] beautiful = new int[n];
        for (int i = 0; i < n; ++i) {
            for (int j = i - 1; j >= 0; --j) {
                if (nums[j] <= nums[i]) {
                    beautiful[i] = nums[j];
                    break;
                }
            }
        }
        long sum = 0;
        // int上限:2^31-1 约等于 2*10^9
        // 输入 1 <= n <= 10^5
        // 1 <= a[i] <= 10^5
        // 10^5 * 10^5 = 10^10 > int上限，故存在上溢的风险，使用长整型
        for (int i = 0; i < n; ++i) {
            sum += beautiful[i];
        }
        System.out.println(sum);
        sc.close();
    }
}
