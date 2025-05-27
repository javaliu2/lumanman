package leetcode.graph;

import java.util.ArrayDeque;
import java.util.Queue;

public class _994腐烂的橘子 {
    private int m, n;

    public int orangesRotting(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> q = new ArrayDeque<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 2) {
                    q.offer(new int[]{i, j});
                }
            }
        }
        int[][] direction = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int minute = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            boolean flag = false;
            while (size-- > 0) {
                int[] idx = q.poll();
                visited[idx[0]][idx[1]] = true;
                for (int ii = 0; ii < 4; ++ii) {
                    int[] d = direction[ii];
                    int new_i = idx[0] + d[0];
                    int new_j = idx[1] + d[1];
                    if (new_i < 0 || new_i >= m || new_j < 0 || new_j >= n) {
                        continue;
                    }
                    if (!visited[new_i][new_j] && grid[new_i][new_j] == 1) {
                        flag = true;
                        grid[new_i][new_j] = 2;
                        q.offer(new int[]{new_i, new_j});
                    }
                }
            }
            if (flag) {
                minute++;
            }
        }

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (!visited[i][j] && grid[i][j] == 1) {
                    return -1;
                }
            }
        }
        return minute;
    }
}

/**
 * G-bro优化
 */
class Solution {
    public int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> q = new ArrayDeque<>();
        int fresh = 0;

        // 初始化队列并计数新鲜橘子
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 2) {
                    q.offer(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    fresh++;
                }
            }
        }

        // 如果没有新鲜橘子，直接返回0
        if (fresh == 0) return 0;

        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        int minutes = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            boolean changed = false;
            while (size-- > 0) {
                int[] cur = q.poll();
                for (int[] d : dirs) {
                    int ni = cur[0] + d[0];
                    int nj = cur[1] + d[1];
                    if (ni < 0 || ni >= m || nj < 0 || nj >= n) continue;
                    if (grid[ni][nj] == 1) {
                        grid[ni][nj] = 2;
                        fresh--;
                        q.offer(new int[]{ni, nj});
                        changed = true;
                    }
                }
            }
            if (changed) minutes++;
        }

        return fresh == 0 ? minutes : -1;
    }
}
