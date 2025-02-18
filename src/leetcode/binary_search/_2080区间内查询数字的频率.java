package leetcode.binary_search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class _2080区间内查询数字的频率 {

}

class RangeFreqQuery {
    private int[] arr;
    Map<Integer, List<Integer>> cnts;

    public RangeFreqQuery(int[] arr) {
        this.arr = arr;
        cnts = new HashMap<>();
        for (int i = 0; i < arr.length; ++i) {
            int ele = arr[i];
            List<Integer> indexs = cnts.getOrDefault(ele, new ArrayList<Integer>());
            indexs.add(i);
            cnts.put(ele, indexs);
        }
    }

    /**
     * 朴素的做法，O(n)遍历
     * 16/20 超时
     */
    public int query_ot(int left, int right, int value) {
        int cnt = 0;
        for (int i = left; i <= right; ++i) {
            if (arr[i] == value) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * 统计每一个元素出现的所有下标，返回[left, right]内该元素的个数
     * 具体来说，判断下标i是否>=left且<=right
     * 还是不行，还是超时
     */
    public int query_fail(int left, int right, int value) {
        int cnt = 0;
        List<Integer> indexs = cnts.get(value);
        if (indexs == null) {
            return 0;
        }
        for (int index : indexs) {
            if (index >= left && index <= right) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * 统计每一个元素出现的所有下标，返回[left, right]内该元素的个数
     * 具体来说，判断下标i是否>=left且<=right
     * leetcode官方思路，做两次二分，分别在indexs中寻找 >=left 的位置l
     * 和 >right 的位置r，r-l即为答案
     */
    public int query(int left, int right, int value) {
        int cnt = 0;
        List<Integer> indexs = cnts.get(value);
        if (indexs == null) {
            return 0;
        }
        int l = lowerBound(indexs, left);
        int r = upperBound(indexs, right);
        return r - l;
    }

    private int lowerBound(List<Integer> indexs, int target) {
        int low = 0, high = indexs.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (indexs.get(mid) >= target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    private int upperBound(List<Integer> indexs, int target) {
        int low = 0, high = indexs.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (indexs.get(mid) > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
}

/**
 * Your RangeFreqQuery object will be instantiated and called as such:
 * RangeFreqQuery obj = new RangeFreqQuery(arr);
 * int param_1 = obj.query(left,right,value);
 */
