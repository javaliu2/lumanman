package leetcode.sliding_window;

import java.util.HashMap;
import java.util.Map;

public class _2799统计完全子数组的数目 {
    public int countCompleteSubarrays(int[] nums) {
        /**
         * 满足要求的子数组其元素个数最少包含nums中不同的元素
         */
        // 首先遍历nums，找出所有不同的元素
        Map<Integer, Integer> cnts = new HashMap<>();
        for (int num : nums) {
            cnts.put(num, 1);
        }
        int diffNumberCnt = cnts.size();
        // 其次，采取滑动窗口的方式寻找满足要求的子数组，[left, right]区间的数满足所有不同的数至少出现一次
        int left = 0, right = 0, n = nums.length, ans = 0;
        Map<Integer, Integer> curr = new HashMap<>();
        while (right < n) {
            // 将[right]加入区间
            int cnt = curr.getOrDefault(nums[right], 0);
            curr.put(nums[right], cnt + 1);
            // 判断当前区间数组是否合法
            while (curr.size() >= diffNumberCnt) {
                ans += n - right;
                // 不断移除最左侧的元素直到区间数组不满足条件
                cnt = curr.get(nums[left]);
                if (cnt - 1 == 0) {
                    curr.remove(nums[left]);
                } else {
                    curr.put(nums[left], cnt - 1);
                }
                left++;
            }
            right++;
        }
        return ans;
    }
}
