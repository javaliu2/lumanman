package leetcode.priority_queue;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class _239滑动窗口最大值 {
    class Solution {
        /**
         * timeover, passed 37/51
         */
        public int[] maxSlidingWindow_pq(int[] nums, int k) {
            PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
            for (int i = 0; i < k - 1; ++i){
                pq.offer(nums[i]);
            }
            int n = nums.length, m = n - k + 1;
            int[] ans = new int[m];
            for (int i = 0; i < m; ++i) {
                pq.offer(nums[i + k - 1]);
                ans[i] = pq.peek();
                pq.remove(nums[i]);  // 超时是因为这里，O(n)的时间复杂度
            }
            return ans;
        }
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((pair1, pair2) -> pair1[0] != pair2[0] ? pair2[0] - pair1[0] : pair2[1] - pair1[1]);
        for (int i = 0; i < k; ++i) {
            pq.offer(new int[]{nums[i], i});
        }
        int[] ans = new int[n - k + 1];
        ans[0] = pq.peek()[0];
        for (int i = k; i < n; ++i) {
            pq.offer(new int[]{nums[i], i});
            while (pq.peek()[1] <= i - k) {  // 当前最大值在滑动区间左边界左侧，所以要移除，直至最大值在区间内
                pq.poll();
            }
            ans[i - k + 1] = pq.peek()[0];
        }
        return ans;
    }
}
