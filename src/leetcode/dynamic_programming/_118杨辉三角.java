package leetcode.dynamic_programming;

import java.util.ArrayList;
import java.util.List;

public class _118杨辉三角 {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> oldRow = new ArrayList<>();
        oldRow.add(1);
        ans.add(oldRow);
        if (numRows == 1) {
            return ans;
        }
        for (int i = 2; i <= numRows; ++i) {
            List<Integer> newRow = new ArrayList<>();
            // 算两个数, num = oldRow(j-1) + oldRow(j)
            for (int j = 0; j < i; ++j) {
                int left = 0, right = 0;
                if (j - 1 >= 0) {
                    left = oldRow.get(j - 1);
                }
                if (j < oldRow.size()) {
                    right = oldRow.get(j);
                }
                int num = left + right;
                newRow.add(num);
            }
            ans.add(newRow);
            oldRow = newRow;
        }
        return ans;
    }
}
