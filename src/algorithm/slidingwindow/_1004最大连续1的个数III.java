package algorithm.slidingwindow;


/**
 * 【题目描述】：https://leetcode.cn/problems/max-consecutive-ones-iii/description/
 * 【题解】：https://leetcode.cn/problems/max-consecutive-ones-iii/solutions/2126631/hua-dong-chuang-kou-yi-ge-shi-pin-jiang-yowmi/
 */
public class _1004最大连续1的个数III {
    /**
     * My Implement
     */
    public int longestOnes(int[] nums, int k) {
        // 统计窗口内0的个数cnt，问题转化为在cnt<=k的前提下
        // 窗口大小的最大值
        int l = 0, cnt = 0, max = 0;
        for (int r = 0; r < nums.length; r++) {
            if (nums[r] == 0) {
                cnt++;
            }
            while (cnt > k) {
                if (nums[l] == 0) {
                    cnt--;
                }
                l++;
            }
            max = Math.max(max, r - l + 1);
        }
        return max;
    }

    /**
     * elegant
     *
     * @author
     */
    public int longestOnes_ling(int[] nums, int k) {
        int ans = 0;
        int cnt0 = 0;
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            cnt0 += 1 - nums[right]; // 0 变成 1，用来统计 cnt0
            while (cnt0 > k) {
                cnt0 -= 1 - nums[left++];
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}
