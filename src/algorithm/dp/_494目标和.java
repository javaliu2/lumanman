package algorithm.dp;


/**
 * 【原题链接】：https://leetcode.cn/problems/target-sum/description/
 * 【题解链接】：https://www.bilibili.com/video/BV16Y411v7Y6?vd_source=f5fd365e0b1873b7d68462bb85723e95
 * 【思路】：
 * 假设nums元素和为s，nums中要添加'+'的元素和为p，
 * 那么要添加'-'的元素和为s-p. 上面这三个值均为正数
 * 这样一来，'表达式'可以表示为：p-(s-p)=t （t是target）
 * 化简得p=(s+t)/2，该式子的含义是从nums中挑选数字，这些数字和p为(s+t)/2的方案数
 * 【细节】：1）(s+t)不能为奇数；2）(s+t)不能为负数（和p为正数的假设违背？）
 */

/**
 * 【分类】：01背包常见变形：选择物品，使其重量等于capacity的方案数（不考虑价值）
 * 【实现】：dfs(i, c)：从前i个数字中选择数字{num_i}，使得sum{num_i}等于c的方案数
 */
public class _494目标和 {
    int[] nums;

    int dfs(int i, int c) {
        if (i < 0) {
            if (c == 0) {
                return 1;
            }
            return 0;
        }
        if (nums[i] > c) {
            return dfs(i - 1, c);
        }
        return dfs(i - 1, c - nums[i]) + dfs(i - 1, c);
    }

    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int e : nums) {
            sum += e;
        }
        sum += target;
        if (sum % 2 == 1 || sum < 0) {
            return 0;
        }
        target = sum / 2;
        int n = nums.length;
        return dfs(n - 1, target);
    }

    int dp(int[] nums, int target) {
        int sum = 0;
        for (int e : nums) {
            sum += e;
        }
        sum += target;
        if (sum % 2 == 1 || sum < 0) {
            return 0;
        }
        target = sum / 2;
        System.out.println("target: " + target);
        int n = nums.length;
        int[][] dp = new int[n + 1][target + 1];
        dp[0][0] = 1;  // 针对dfs中的边界，c==0时为1
        for (int i = 0; i < n; i++) {
            for (int c = 0; c <= target; c++) {
                if (c < nums[i]) {  // 物品重量大于背包容量
                    dp[i + 1][c] = dp[i][c];
                } else {
                    dp[i + 1][c] = dp[i][c] + dp[i][c - nums[i]];
                }
            }
        }
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= target; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        return dp[n][target];
    }

    int dp_rollarray(int[] nums, int target) {
        int sum = 0;
        for (int e : nums) {
            sum += e;
        }
        sum += target;
        if (sum % 2 == 1 || sum < 0) {
            return 0;
        }
        target = sum / 2;
        int n = nums.length;
        int[][] dp = new int[2][target + 1];
        dp[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int c = 0; c <= target; c++) {
                if (c < nums[i]) {
                    dp[(i + 1) % 2][c] = dp[i % 2][c];
                } else {
                    dp[(i + 1) % 2][c] = dp[i % 2][c] + dp[i % 2][c - nums[i]];
                }
            }
        }
        return dp[n % 2][target];
    }

    int dp_final(int[] nums, int target) {
        int sum = 0;
        for (int e : nums) {
            sum += e;
        }
        sum += target;
        if (sum % 2 == 1 || sum < 0) {
            return 0;
        }
        target = sum / 2;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        // 根本的计算逻辑，剖开现象看本质，wonderful，excellent
        for (int num : nums) {
            // 从后往前算，防止变量被覆写
            for (int c = target; c >= num; c--) {
                dp[c] = dp[c] + dp[c - num];
            }
        }
        return dp[target];
    }


    public static void main(String[] args) {
        _494目标和 obj = new _494目标和();
        int[] nums = {1, 1, 1, 1, 1};
        obj.nums = nums;
        int target = 3;
//        int res = obj.dp_rollarray(nums, target);
        int res = obj.dp_final(nums, target);
        System.out.println(res);
//        int[] nums = {1};
//        obj.nums = nums;
//        int target = 1;
//        int res = obj.findTargetSumWays(nums, target);
//        System.out.println(res);
    }
}
