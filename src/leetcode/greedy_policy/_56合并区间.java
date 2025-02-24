package leetcode.greedy_policy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * LeetCode Official思路
 * 首先按照左端点对区间排序，然后将第一个区间加入合并数组，
 * 然后比较新区间左端点和合并数组最后一个区间的右端点，
 * 左端点小于右端点，更新右端点为两者右端点较大值;
 * 左端点大于右端点，将该区间添加至合并数组作为新的不重叠区间
 * 证明的话，见官网 https://leetcode.cn/problems/merge-intervals/solutions/203562/he-bing-qu-jian-by-leetcode-solution/?envType=study-plan-v2&envId=top-100-liked
 */
public class _56合并区间 {
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][2];
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] interval1, int[] interval2) {
                return interval1[0] - interval2[0];
            }
        });
        List<int[]> merged = new ArrayList<int[]>();
        for (int i = 0; i < intervals.length; ++i) {
            int L = intervals[i][0], R = intervals[i][1];
            int last_idx = merged.size() - 1;
            if (merged.size() == 0 || merged.get(last_idx)[1] < L) {
                merged.add(new int[]{L, R});
            } else {
                merged.get(last_idx)[1] = Math.max(merged.get(last_idx)[1], R);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }
}
