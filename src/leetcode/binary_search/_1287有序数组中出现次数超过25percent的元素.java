package leetcode.binary_search;

import java.util.HashMap;
import java.util.Map;

public class _1287有序数组中出现次数超过25percent的元素 {
    /**
     * 偷懒，22/29
     */
    public int findSpecialInteger_fail(int[] arr) {
        int n = arr.length;
        if (n == 1) {
            return arr[0];
        }
        int idx = (n + 1) / 2;
        return arr[idx];
    }

    /**
     * 笨方法
     *
     * @param arr
     * @return
     */
    public int findSpecialInteger_bad(int[] arr) {
        int n = arr.length;
        Map<Integer, Integer> cnts = new HashMap<>();
        for (int ele : arr) {
            int value = cnts.getOrDefault(ele, 0);
            cnts.put(ele, value + 1);
        }
        int bound = n / 4, target = -1;
        for (Map.Entry<Integer, Integer> entry : cnts.entrySet()) {
            int value = entry.getValue();
            if (value > bound) {
                target = entry.getKey();
            }
        }
        return target;
    }

    /**
     * @author LeetCode Official
     * @param arr
     * @return
     */
    public int findSpecialInteger(int[] arr) {
        int n = arr.length;
        int span = n / 4 + 1;
        for (int i = 0; i < n; i += span) {
            int start = binarySearch(arr, arr[i]);
            int end = binarySearch(arr, arr[i] + 1);
            if (end - start >= span) {
                return arr[i];
            }
        }
        return -1;
    }

    private int binarySearch(int[] arr, int target) {
        int lo = 0, hi = arr.length - 1;
        int res = arr.length;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] >= target) {
                res = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return res;
    }
}
