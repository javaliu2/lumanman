package algorithm.slidingwindow;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class _2958最多K个重复元素的最长子数组 {
    static final int M = 1_000_000_001;
    // FATAL ERROR
    // java.lang.OutOfMemoryError: Java heap space
    static int[] has = new int[M]; // 4 * M = 4_000_000_004 bytes 约等于 3.73 GB
    public int maxSubarrayLength(int[] nums, int k) {
        int left = 0, n = nums.length, max = 0;
        for (int right = 0; right < n; right++) {
            has[nums[right]]++;
            while (has[nums[right]] > k) {
                has[nums[left]]--;
                left++;
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    /**
     * My Solution(success)
     * @param nums
     * @param k
     * @return
     */
    public int maxSubarrayLength2(int[] nums, int k) {
        Map<Integer, Integer> cnt = new HashMap<>();
        int left = 0, n = nums.length, max = 0;
        for (int right = 0; right < n; right++) {
            int key = nums[right];
            cnt.put(key, cnt.getOrDefault(key, 0) + 1);
            int t = cnt.get(key);
            while (t > k) {
                cnt.put(nums[left], cnt.get(nums[left]) - 1);
                left++;
                t = cnt.get(key);
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }
    @Test
    public void test() {
        int[] nums = {1,2,3,1,2,3,1,2};
        int k=2;
        maxSubarrayLength(nums, k);
    }
}
