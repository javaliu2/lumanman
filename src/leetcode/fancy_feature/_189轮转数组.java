package leetcode.fancy_feature;

public class _189轮转数组 {
    /**
     * @author xs
     * My Implementation
     */
    public void rotate_v1(int[] nums, int k) {
        // 时空复杂度均为O(n)
        int n = nums.length;
        k %= n;
        if (k == 0) {
            return;
        }
        int start_idx = n - k, idx = 0;
        int[] ans = new int[n];
        for (int i = start_idx; i < n; ++i) {
            ans[idx] = nums[i];
            idx++;
        }
        for (int i = 0; i < start_idx; ++i) {
            ans[idx] = nums[i];
            idx++;
        }
        // nums = ans;  复制引用不好使
        for (int i = 0; i < n; ++i) {
            nums[i] = ans[i];
        }
    }

    /**
     * LeetCode Official answer two
     *
     * @param nums
     * @param k
     */
    public void rotate_lo2(int[] nums, int k) {
        int n = nums.length, cnt = 0;
        k %= n;
        if (k == 0) {
            return;
        }
        // 处理start_idx起始的元素序列，结束条件索引回到start_idx
        int temp, temp2, i, start_idx = 0, new_idx;
        while (cnt < n) {
            i = start_idx;
            temp = nums[i];
            new_idx = (i + k) % n;
            while (start_idx != new_idx) {
                new_idx = (i + k) % n;
                temp2 = nums[new_idx];
                nums[new_idx] = temp;
                temp = temp2;
                i = new_idx;
                cnt++;
            }
            start_idx++;
        }
    }

    /**
     * LeetCode Official answer three
     * 两次翻转变回原序列
     * @param nums
     * @param k
     */
    public void rotate_lo3(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start += 1;
            end -= 1;
        }
    }
}
