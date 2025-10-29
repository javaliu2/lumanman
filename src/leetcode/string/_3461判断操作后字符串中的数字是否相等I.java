package leetcode.string;

import java.util.ArrayList;
import java.util.List;

public class _3461判断操作后字符串中的数字是否相等I {
    public boolean hasSameDigits(String s) {
        char[] chs = s.toCharArray();
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i + 1 < chs.length; ++i) {
            int num1 = chs[i] - '0';
            int num2 = chs[i + 1] - '0';
            int num = (num1 + num2) % 10;
            nums.add(num);
        }
        while (nums.size() > 2) {
            List<Integer> new_nums = new ArrayList<>();
            for (int i = 0; i + 1 < nums.size(); ++i) {
                int num1 = nums.get(i);
                int num2 = nums.get(i + 1);
                int num = (num1 + num2) % 10;
                new_nums.add(num);
            }
            nums = new_nums;
        }
        if (nums.size() == 2) {
            return nums.get(0) == nums.get(1);
        }
        return false;
    }
}
