package leetcode.fancy_feature;

import java.util.HashMap;

public class _2364统计坏数对的数目 {
    public long countBadPairs_my(int[] nums) {
        int n = nums.length;
        int all_pair_num = n * (n-1) / 2;
        int cnt = 0;
        for (int i = 0; i < n; ++i) {
            if (i == nums[i]) {  // 这里的判断不对
                // 针对测试用例[1,2,3,4,5]他就失败，因为i和[i]之间有一个相同的偏移
                cnt++;
            }
        }
        int good_pair_num = cnt * (cnt-1) / 2;
        return all_pair_num - good_pair_num;
    }
    public long countBadPairs(int[] nums) {
        HashMap<Integer, Integer> mp = new HashMap<>();
        long res = 0;
        for (int i = 0; i < nums.length; i++) {
            int key = nums[i] - i;
            res += i - mp.getOrDefault(key, 0);  // i表示当前元素和前面元素配对的总个数，mp[key]表示i之前的元素nums[i]-i值的数量，减去这个值就是坏数对的数量
            mp.put(key, mp.getOrDefault(key, 0) + 1);
        }
        return res;
    }
}
