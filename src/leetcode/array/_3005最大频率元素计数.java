package leetcode.array;

import java.util.HashMap;
import java.util.Map;

public class _3005最大频率元素计数 {
    public int maxFrequencyElements(int[] nums) {
        int max_frequency = 0;
        Map<Integer, Integer> cnts = new HashMap<>();
        for (int num : nums) {
            int cnt = cnts.getOrDefault(num, 0);
            max_frequency = Math.max(max_frequency, cnt + 1);
            cnts.put(num, cnt + 1);
        }
        int ans = 0;
        for (Map.Entry<Integer, Integer> entry : cnts.entrySet()) {
            if (entry.getValue() == max_frequency) {
                ans += max_frequency;
            }
        }
        return ans;
    }
}
