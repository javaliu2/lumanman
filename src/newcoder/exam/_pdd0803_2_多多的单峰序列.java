package newcoder.exam;

import java.util.Arrays;
import java.util.Scanner;

public class _pdd0803_2_多多的单峰序列 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = in.nextInt();
        }
        int res = fun2(nums);
        System.out.println(res);
    }

    /**
     * 32 percent passed
     * @param numss
     * @return
     */
    static int fun2(int[] numss) {
        // 枚举每一个顶峰
        int n = numss.length;
        int ans = Integer.MAX_VALUE;
        for (int peak = 1; peak < n-1; ++peak) {
            // 计算以peak为顶峰，变为单峰序列需要的操作数
            int cost = 0;
            int[] nums = Arrays.copyOf(numss, n);
            for (int i = 1; i <= peak; ++i) {
                if (nums[i] <= nums[i-1]) {
                    int num = nums[i-1] + 1;
                    cost += num - nums[i];
                    nums[i] = num;
                }
            }
            for (int j = n-2; j >= peak; --j) {
                if (nums[j+1] >= nums[j]) {
                    int num = nums[j+1] + 1;
                    cost += num - nums[j];
                    nums[j] = num;
                }
            }
            ans = Math.min(ans, cost);
        }
        return ans;
    }
    static int fun(int[] nums) {
        int i = 1, j = nums.length - 2;
        int ans = 0, current = 1;
        while (i <= j) {
            if (current % 2 == 1) {
                if (nums[i] <= nums[i-1]) {
                    int num = nums[i-1] + 1;
                    ans += num - nums[i];
                    nums[i] = num;
                }
                ++i;
            } else {
                if (nums[j] <= nums[j+1]) {
                    int num = nums[j+1] + 1;
                    ans += num - nums[j];
                    nums[j] = num;
                }
                --j;
            }
            current++;
        }
        return ans;
    }
}
