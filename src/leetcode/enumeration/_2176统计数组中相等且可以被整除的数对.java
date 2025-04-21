package leetcode.enumeration;

public class _2176统计数组中相等且可以被整除的数对 {
    public int countPairs(int[] nums, int k) {
        /**
         * 枚举
         */
        int n = nums.length, cnt = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (nums[i] == nums[j]) {
                    if (i * j % k == 0) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }
}
