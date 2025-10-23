package leetcode.diff_array;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class _3347执行操作后元素的最高频率II {
    public int maxFrequency(int[] nums, int k, int numOperations) {
        Map<Integer, Integer> cnt = new HashMap<>();
        Map<Integer, Integer> diff = new TreeMap<>();

        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum);
            diff.putIfAbsent(x, 0);  // 这里不是很懂。
            diff.merge(x - k, 1, Integer::sum);
            diff.merge(x + k + 1, -1, Integer::sum);
        }
        int ans = 0;
        int sumD = 0;
        for (Map.Entry<Integer, Integer> e : diff.entrySet()) {
            sumD += e.getValue();
            ans = Math.max(ans, Math.min(sumD, cnt.getOrDefault(e.getKey(), 0) + numOperations));
        }
        return ans;
    }

    @Test
    public void testMaxFrequency() {
        int[] nums = new int[]{1, 4, 5};
        int k = 1;
        int numOperations = 2;
        maxFrequency(nums, k, numOperations);
    }
}
