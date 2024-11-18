package algorithm.slidingwindow;

/**
 * 【题目描述】：https://leetcode.cn/problems/count-subarrays-where-max-element-appears-at-least-k-times/description/
 * 【题解】：https://leetcode.cn/problems/count-subarrays-where-max-element-appears-at-least-k-times/solutions/2560940/hua-dong-chuang-kou-fu-ti-dan-pythonjava-xvwg/
 */
public class _2962统计最大元素出现至少K次的子数组 {
    /**
     * My Solution(brute force)
     * 625 / 633 个通过的测试用例
     * @param nums
     * @param k
     * @return
     */
    public long countSubarrays_bf(int[] nums, int k) {
        int max = -1;
        for (int e : nums) {
            if (e > max) {
                max = e;
            }
        }
        int n = nums.length;
        int res = 0;
        // brute force
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int j = i; j < n; j++) {
                if (nums[j] == max) {
                    cnt++;
                }
                if (cnt == k) {
                    res += n - j;
                    break;
                }
            }
        }
        return res;
    }

    /**
     * @author 灵茶山艾府
     * 算法流程：
     * 1、寻找数组最大值 max
     * 2、枚举右端点，统计 max 出现的次数 cnt
     *   当cnt==k时，增大左端点left使得次数 cnt < k
     *   这时，以右端点为数组最后一个元素的所有子数组
     *   均满足最大元素 max 出现的次数 >= k的要求，其个数为 left (刚好是left索引的值)
     *   将 left 累加到 ans 中
     * 3、返回ans
     */
    long countSubarrays_ling(int[] nums, int k){
        int max = -1;
        for (int e : nums) {
            if (e > max) {
                max = e;
            }
        }
        int left = 0, cnt = 0;
        long ans = 0;  // int 会越界
        for (int num : nums) {
            if (num == max) {
                cnt++;
            }
            while (cnt >= k) {
                if (nums[left] == max) {
                    cnt--;
                }
                left++;
            }
            ans += left;
        }
        return ans;
    }
}
