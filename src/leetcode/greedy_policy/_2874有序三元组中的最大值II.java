package leetcode.greedy_policy;

public class _2874有序三元组中的最大值II {
    public long maximumTripletValue_bf(int[] nums) {
        /**
         * 朴素的做法：枚举所有三元组，计算其值，找出最大的
         * 不出所料，超时。大力出奇迹，failed。
         */
        int n = nums.length;
        long ans = 0;
        for (int i = 0; i + 2 < n; ++i) {
            for (int j = i + 1; j + 1 < n; ++j) {
                for (int k = j + 1; k < n; ++k) {
                    long v = ((long) nums[i] - (long) nums[j]) * (long) nums[k];
                    ans = Math.max(v, ans);
                }
            }
        }
        return ans;
    }

    public long maximumTripletValue(int[] nums) {
        /**
         * LC官网方法1，通过贪心和前后缀数组求解
         */
        int n = nums.length;
        int[] leftMax = new int[n], rightMax = new int[n];
        // 枚举j的位置，leftMax记录的是[0,j)区间数组的最大值
        int max = nums[0];
        for (int j = 1; j + 1 < n; ++j) {
            max = Math.max(max, nums[j - 1]);
            leftMax[j] = max;
        }
        max = nums[n - 1];
        // rightMax记录的是[j+1,n)区间数组的最大值
        for (int j = n - 2; j > 0; --j) {
            max = Math.max(max, nums[j + 1]);
            rightMax[j] = max;
        }
        // System.out.println(Arrays.toString(leftMax));
        // System.out.println(Arrays.toString(rightMax));
        long ans = 0;
        for (int j = 1; j + 1 < n; ++j) {
            ans = Math.max(ans, ((long) leftMax[j] - nums[j]) * rightMax[j]);
        }
        return ans;
    }
}
