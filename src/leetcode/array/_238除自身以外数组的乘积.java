package leetcode.array;

import java.util.Arrays;

public class _238除自身以外数组的乘积 {
    public int[] productExceptSelf_v1(int[] nums) {
        // 三次遍历
        // 计算每一个元素的前缀乘积、后缀乘积
        // 前缀乘积 * 后缀乘积
        int n = nums.length;
        int[] prefix_product = new int[n];
        int[] suffix_product = new int[n];
        prefix_product[0] = 1;
        suffix_product[n - 1] = 1;
        for (int i = 1; i < n; ++i) {
            prefix_product[i] = nums[i - 1] * prefix_product[i - 1];
        }
        System.out.println(Arrays.toString(prefix_product));
        for (int i = n - 2; i >= 0; --i) {
            suffix_product[i] = nums[i + 1] * suffix_product[i + 1];
        }
        System.out.println(Arrays.toString(suffix_product));
        int[] ans = new int[n];
        for (int i = 0; i < n; ++i) {
            ans[i] = prefix_product[i] * suffix_product[i];
        }
        return ans;
    }
    public int[] productExceptSelf(int[] nums) {
        // 两次遍历
        // 计算每一个元素的前缀乘积(用数组存储)、后缀乘积(用变量存储)
        int n = nums.length;
        int[] prefix_product = new int[n];
        int suffix_product = 1;
        prefix_product[0] = 1;
        for (int i = 1; i < n; ++i) {
            prefix_product[i] = nums[i - 1] * prefix_product[i - 1];
        }
        for (int i = n - 2; i >= 0; --i) {
            suffix_product *= nums[i + 1];
            prefix_product[i] *= suffix_product;
        }
        return prefix_product;
    }
}