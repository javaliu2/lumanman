package leetcode.bfs;

import java.util.ArrayDeque;
import java.util.Queue;

public class _1298你能从盒子里获取的最大糖果数 {
    public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
        // 一个盒子只能被打开一次，所以如果有重复出现的key，也没关系
        // 通过visited判断盒子是否被访问过，如果访问过，第二次key出现也没关系
        int n = status.length;
        boolean[] can_open = new boolean[n];
        boolean[] has_box = new boolean[n];  // 是否有这个盒子
        boolean[] visited = new boolean[n];  // 是否打开过

        for (int i = 0; i < n; ++i) {
            can_open[i] = (status[i] == 1);
        }

        int ans = 0;
        Queue<Integer> q = new ArrayDeque<>();
        for (int box : initialBoxes) {
            has_box[box] = true;  // 这个很关键，因为盒子可能打不开，需要钥匙
            if (can_open[box]) {
                q.offer(box);
                visited[box] = true;
                ans += candies[box];
            }
        }

        while (!q.isEmpty()) {
            int top_box = q.poll();
            // 先处理key，或者先处理box，都一样。
            for (int key : keys[top_box]) {
                can_open[key] = true;
                if (!visited[key] && has_box[key]) {
                    q.offer(key);
                    visited[key] = true;
                    ans += candies[key];
                }
            }
            for (int other_box : containedBoxes[top_box]) {
                has_box[other_box] = true;
                if (!visited[other_box] && can_open[other_box]) {
                    q.offer(other_box);
                    visited[other_box] = true;
                    ans += candies[other_box];
                }
            }
        }

        return ans;
    }
}
