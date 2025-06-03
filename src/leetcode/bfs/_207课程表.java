package leetcode.bfs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Idea from Leetcode Official
 */
public class _207课程表 {
    public boolean canFinish(int n, int[][] prerequisites) {

        // 1、建立有向图，节点表示课程，箭头的指向表示以该课程为前置课程的其他课程
        // 2、使用bfs，当课程的入度为0，表示该课程完成了前置课程的学习，或者没有前置课程
        // 将其加入队列，同时将以该课程为前置课程的节点（即课程）入度减一
        // 判断是否有入度为0的课程，如果有，将其加入队列

        int[][] edge = new int[n][n];
        for (int[] pre : prerequisites) {
            edge[pre[0]][pre[1]] = 1;
        }

        int[] in_degree = new int[n];
        for (int j = 0; j < n; ++j) {
            for (int i = 0; i < n; ++i) {
                in_degree[j] += edge[i][j];
            }
        }

        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[n];
        // 将入度为0的课程节点加入队列
        for (int i = 0; i < n; ++i) {
            if (in_degree[i] == 0) {
                visited[i] = true;
                q.offer(i);
            }
        }

        if (q.isEmpty()) {
            return false;
        }

        while (!q.isEmpty()) {
            int node = q.poll();
            // node节点能到达的节点，即以node为前置课程的课程节点入度减1
            for (int j = 0; j < n; ++j) {
                if (edge[node][j] == 1) {
                    in_degree[j]--;
                }
            }
            // 判断是否有入度为0的节点
            for (int i = 0; i < n; ++i) {
                if (!visited[i] && in_degree[i] == 0) {
                    visited[i] = true;
                    q.offer(i);
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            if (in_degree[i] > 0) {
                return false;
            }
        }
        return true;
    }
}
