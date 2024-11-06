package leetcode;

import java.util.Arrays;

public class _3254长度为K的子数组的能量值I {

    /**
     * my solution
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] resultsArray(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        for (int i = 0; i + k <= n; i++) {
            boolean flag = true;
            for (int j = i; j < i + k - 1; j++) {
                // [i]和[i+1]需要递增，不是简单的大于关系
                if (nums[j] + 1 != nums[j + 1]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                res[i] = nums[i + k - 1];
            } else {
                res[i] = -1;
            }
        }
        return res;
    }

    /**
     * @Author 灵茶山艾府
     * @param nums
     * @param k
     * @return
     */
    public int[] resultsArray2(int[] nums, int k) {
        int[] ans = new int[nums.length - k + 1];
        Arrays.fill(ans, -1);
        int cnt = 0;
        for (int i = 0; i < nums.length; i++) {
            // i==0 针对的是 nums={1}, k=1 等极端样例
            cnt = i == 0 || nums[i] == nums[i - 1] + 1 ? cnt + 1 : 1;
            if (cnt >= k) {
                ans[i - k + 1] = nums[i];
            }
        }
        return ans;
    }

}
