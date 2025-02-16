package leetcode.priority_queue;

import java.util.Collections;
import java.util.PriorityQueue;

public class _1299将每个元素替换为右侧最大元素 {
    /**
     * overtime
     * @param arr
     * @return
     */
    public int[] replaceElements_ot(int[] arr) {
        /**
         * 借助优先级队列
         * 超时: 89/90个通过的测试用例
         */
        // 创建大跟堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        int n = arr.length, i;
        for (i = 0; i < n; ++i) {
            maxHeap.offer(arr[i]);
        }
        int[] ans = new int[n];
        for (i = 0; i < n - 1; ++i) {
            maxHeap.remove(arr[i]);
            ans[i] = maxHeap.peek();
        }
        ans[i] = -1;
        return ans;
    }

    /**
     * @author me
     * @idea G-bro
     * @param arr
     * @return
     */
    public int[] replaceElements(int[] arr) {
        /**
         * 从后往前计算, 使用变量max维护[i,...,n-1]的最大值
         */
        int n = arr.length, max = -1;
        int[] ans = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            ans[i] = max;
            max = Math.max(max, arr[i]);
        }
        return ans;
    }
}
