package leetcode.array;

public class _2012数组美丽值求和 {
    /**
     * overtime, 53/62 passed
     */
    public int sumOfBeauties_ot(int[] nums) {
        /**
         * 模拟。枚举判断
         */
        int ans = 0;
        int n = nums.length, j;
        boolean flag;
        for (int i = 1; i < n - 1; ++i) {
            // 判断[0,...,i-1]是否都严格小于[i]
            //   [i+1,...,n-1]是否都严格大于[i]
            j = i - 1;
            flag = true;
            while (j >= 0) {
                if (nums[j] >= nums[i]) {
                    flag = false;
                    break;
                }
                j--;
            }
            if (flag) {
                j = i + 1;
                while (j < n) {
                    if (nums[j] <= nums[i]) {
                        flag = false;
                        break;
                    }
                    j++;
                }
            }
            if (flag) {
                ans += 2;
                continue;
            }
            // 上述条件有一个元素不满足，那么检查[i-1]?<[i]?<[i+1]
            // 如果[i-1]和[i+1]存在的话
            flag = true;
            if (i - 1 >= 0 && nums[i - 1] >= nums[i]) {
                flag = false;
            }
            if (i + 1 < n && nums[i + 1] <= nums[i]) {
                flag = false;
            }
            if (flag) {
                ans += 1;
                continue;
            }
            // 上述条件不满足，[i]的美丽值为0
        }
        return ans;
    }

    /**
     * @author LeetCode Official
     */
    public int sumOfBeauties(int[] nums) {
        int n = nums.length;
        int[] state = new int[n];
        int pre_max = nums[0];
        for (int i = 1; i < n - 1; i++) {
            if (nums[i] > pre_max) {
                state[i] = 1;
                pre_max = nums[i];
            }
        }
        int suf_min = nums[n - 1];
        int res = 0;
        // 逐一判断[1,...,n-2]
        for (int i = n - 2; i > 0; i--) {
            // 当前元素是前缀最大，即[i]严格大于前面所有元素，否则的话，前面有一个元素比[i]大，第一个条件就不成立
            if (state[i] == 1 && nums[i] < suf_min) {
                res += 2;
            } else if (nums[i - 1] < nums[i] && nums[i] < nums[i + 1]) {
                res += 1;
            }
            suf_min = Math.min(suf_min, nums[i]);
        }
        return res;
    }
}
