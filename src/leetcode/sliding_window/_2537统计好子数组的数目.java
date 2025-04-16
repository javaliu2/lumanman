package leetcode.sliding_window;

import java.util.HashMap;

public class _2537统计好子数组的数目 {
    public long countGood(int[] nums, int k) {
        int n = nums.length;
        int same = 0, right = -1;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        long ans = 0;
        for (int left = 0; left < n; ++left) {
            while (same < k && right + 1 < n) {
                ++right;
                same += cnt.getOrDefault(nums[right], 0);  // 新元素如果和已有元素值相等，那么它可以和之前相等的元素一一配对，配对数就是之前重复元素的个数
                cnt.put(nums[right], cnt.getOrDefault(nums[right], 0) + 1);
            }
            if (same >= k) {
                ans += n - right;
            }
            cnt.put(nums[left], cnt.get(nums[left]) - 1);  // [left]元素个数减一
            same -= cnt.get(nums[left]);  // [left]可以和后面的重复元素一一配对，数量为后面重复元素的数量，将[left]移除区间，那么它可以贡献的配对数减去这么多
        }
        // 举例说明：
        // 1，1，1，1，假设遍历到第三个元素，那么这时，same==6
        // 第一个1对后面元素的配对贡献是3，因此移动left指针的时候，需要将该贡献移除，即same-=3
        return ans;
    }
}
