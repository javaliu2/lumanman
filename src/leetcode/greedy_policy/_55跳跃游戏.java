package leetcode.greedy_policy;

public class _55跳跃游戏 {
    public boolean canJump_my(int[] nums) {
        // 枚举所有方案，判断是否可以到达end
        if (nums.length == 1) {
            return true;
        }
        return dfs(0, nums);
    }

    /**
     * timeout
     */
    boolean dfs(int current, int[] nums) {
        if (current >= nums.length - 1) {
            return true;
        }
        for (int step = nums[current]; step > 0; --step) {
            boolean isSuccessful = dfs(current + step, nums);
            if (isSuccessful) {
                return true;
            }
        }
        return false;
    }

    /**
     * 遍历数组维护每一个索引i可以到达的最大下标 i+[i]
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        int maxPosition = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (i <= maxPosition) {
                maxPosition = Math.max(maxPosition, i + nums[i]);
                if (maxPosition >= nums.length - 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
