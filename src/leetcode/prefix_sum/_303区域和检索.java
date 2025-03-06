package leetcode.prefix_sum;

public class _303区域和检索 {
}

class NumArray {
    int[] prefix_sum;

    public NumArray(int[] nums) {
        int n = nums.length;
        prefix_sum = new int[n];
        prefix_sum[0] = nums[0];
        for (int i = 1; i < n; ++i) {
            prefix_sum[i] = prefix_sum[i - 1] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        // prefix_sum[right] - prefix_sum[left-1]
        int left_sum;
        if (left - 1 < 0) {
            left_sum = 0;
        } else {
            left_sum = prefix_sum[left - 1];
        }
        return prefix_sum[right] - left_sum;
    }
}

/**
 * @author 灵神
 */
class NumArray_ling {
    private int[] s;
    // s[n] = num[0] + num[1] + ... + num[n-1]
    public NumArray_ling(int[] nums) {
        s = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            s[i + 1] = s[i] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return s[right + 1] - s[left];
    }
}
