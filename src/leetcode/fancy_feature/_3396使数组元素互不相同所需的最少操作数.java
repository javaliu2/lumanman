package leetcode.fancy_feature;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class _3396使数组元素互不相同所需的最少操作数 {
    public int minimumOperations(int[] nums) {
        /**
         * 逆向思维
         */
        int ans = 0, i;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (i = nums.length - 1; i >= 0; --i) {
            Integer exist = cnt.get(nums[i]);
            if (exist != null) {
                break;
            }
            cnt.put(nums[i], 1);
        }
        if (i >= 0) {
            ans = (int) Math.ceil((i + 1) / 3.0);
//            ans = i / 3 + 1;  ceil((i+1)/3) <==> floor(i/3)+1, comes from ling_shen
        }
        return ans;
    }

    @Test
    public void Test() {
        int[] nums= {4,5,6,4,4};
        int res = minimumOperations(nums);
        System.out.println(res);
    }
}
