package newcoder.exam;

import org.junit.Test;

import java.util.*;


public class _满足回文条件的索引集合 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param nums int整型一维数组
     * @param k int整型
     * @return int整型一维数组
     */
    public int[] find_palindrome_subarrays (int[] nums, int k) {
        int n = nums.length;
        List<Integer> idx = new ArrayList<>();
        for (int i = 0; i + k <= n; ++i) {
            int j = i + k - 1, t = i;
            // [i, j)
            while (t < j) {
                if (nums[t] != nums[j]) {
                    break;
                }
                t++;
                j--;
            }
            if (t >= j) {
                idx.add(i);
            }
        }
        int[] ans = new int[idx.size()];
        for (int i = 0; i < idx.size(); ++i) {
            ans[i] = idx.get(i);
        }
        return ans;
    }

    @Test
    public void test() {
        int[] nums = {1,2,3,4,5};
        int k = 3;
        int[] a = find_palindrome_subarrays(nums, k);
        System.out.println(Arrays.toString(a));
    }
}
