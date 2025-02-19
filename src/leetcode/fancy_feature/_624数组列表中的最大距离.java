package leetcode.fancy_feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class _624数组列表中的最大距离 {
    /**
     * 121/136 passed
     */
    public int maxDistance_fail(List<List<Integer>> arrays) {
        List<Integer> mins = new ArrayList<>();
        List<Integer> maxs = new ArrayList<>();
        for (List<Integer> array : arrays) {
            mins.add(array.get(0));
            maxs.add(array.get(array.size() - 1));
        }
        Collections.sort(mins);
        Collections.sort(maxs);
        return maxs.get(maxs.size() - 1) - mins.get(0);
        // 选取的min不能和自己同一个数组
    }

    /**
     * @author: LeetCode Official
     */
    public int maxDistance(List<List<Integer>> arrays) {
        int res = 0;
        int n = arrays.get(0).size();
        int min_val = arrays.get(0).get(0);
        int max_val = arrays.get(0).get(n - 1);
        for (int i = 1; i < arrays.size(); i++) {
            n = arrays.get(i).size();
            res = Math.max(res, Math.max(Math.abs(arrays.get(i).get(n - 1) - min_val),
                    Math.abs(max_val - arrays.get(i).get(0))));
            min_val = Math.min(min_val, arrays.get(i).get(0));
            max_val = Math.max(max_val, arrays.get(i).get(n - 1));
        }
        return res;
    }
}
