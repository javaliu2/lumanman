package algorithm.dp;

import java.util.Arrays;

public class _198打家劫舍 {
        int[] nums;
        int[] cache;
        public int rob_dfs(int[] nums) {
            this.nums = nums;
            int n = nums.length;
            cache = new int[n];
            Arrays.fill(cache, -1);
            return dfs(n - 1);
        }

    /**
     * 记忆化搜索版本, 递归, 自顶向下<br>
     * 形成一棵搜索树, 因此可以使用记忆化的方式对子问题的答案进行记录, 避免重复计算
     * @param i 递归参数，表示子问题，前i个房舍所能偷窃到的最大值
     * @return
     */
    int dfs(int i) {
            if (i < 0) {
                return 0;
            }
            if (cache[i] != -1) {
                return cache[i];
            }
            // 1、递
            // 1) dfs(i-2)+nums[i]表示的子问题(方案1):
            // *选* 当前的房舍i进行偷窃, 这样的话, 房舍i-1就不能偷窃了，因为会触发警报.
            // 因此, 剩下的子问题就是前i-2个房舍的偷窃问题, 使用dfs(i-2)进行解决.
            // 2) dfs(i-1)表示的子问题(方案2):
            // *不对* 当前房舍i进行偷窃, 解决前i-1个房舍的偷窃问题.

            // 2、归
            // 选择两个方案中的较大值作为对房舍i偷窃问题的答案.
            int res = Math.max(dfs(i - 2) + nums[i], dfs(i - 1));
            cache[i] = res;
            return res;
        }

    /**
     * DP版本, 递推, 自底向上<br>
     * @param nums
     * @return
     */
    public int rob_dp(int[] nums) {
            int n = nums.length;
            int[] f = new int[n + 2];
            for (int i = 0; i < n; i++) {
                f[i + 2] = Math.max(f[i] + nums[i], f[i + 1]);
            }
            return f[n + 1];
        }

    /**
     * 最终版本<br>
     * 数学公式: f[i] = max(f[i-1], f[i-2]+nums[i])
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
            int n = nums.length;
            int f0 = 0, f1 = 0;
            for (int i = 0; i < n; i++) {
                int newF = Math.max(f1, f0 + nums[i]);
                f0 = f1;
                f1 = newF;
            }
            return f1;
        }
}
