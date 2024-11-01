package leetcode;

import java.util.*;



public class _2101引爆最多的炸弹 {
    class Solution {
        public int maximumDetonation(int[][] bombs) {
            int n = bombs.length;
            // 维护引爆关系有向图
            Map<Integer, List<Integer>> edges = new HashMap<Integer, List<Integer>>();
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i != j && isConnected(bombs, i, j)) {
                        edges.putIfAbsent(i, new ArrayList<Integer>());
                        edges.get(i).add(j);
                    }
                }
            }
            int res = 0;   // 最多引爆数量
            for (int i = 0; i < n; ++i) {
                // 遍历每个炸弹，广度优先搜索计算该炸弹可引爆的数量，并维护最大值
                boolean[] visited = new boolean[n];
                int cnt = 1;
                Queue<Integer> queue = new ArrayDeque<Integer>();
                queue.offer(i);
                visited[i] = true;
                while (!queue.isEmpty()) {
                    int cidx = queue.poll();
                    for (int nidx : edges.getOrDefault(cidx, new ArrayList<Integer>())) {
                        if (visited[nidx]) {
                            continue;
                        }
                        ++cnt;
                        queue.offer(nidx);
                        visited[nidx] = true;
                    }
                }
                res = Math.max(res, cnt);
            }
            return res;
        }

        // 判断炸弹 u 能否引爆炸弹 v
        public boolean isConnected(int[][] bombs, int u, int v) {
            long dx = bombs[u][0] - bombs[v][0];
            long dy = bombs[u][1] - bombs[v][1];
            return (long) bombs[u][2] * bombs[u][2] >= dx * dx + dy * dy;
        }
    }
    public int maximumDetonation(int[][] bombs) {
        // bombs = [[1,2,3],[2,3,1],[3,4,2],[4,5,3],[5,6,4]]
        // [x, y, r]
        // 1) 构建炸弹之间的有向图
        int m = bombs.length;
        int[][] graph = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (i == j) {
                    continue;
                }
                // 炸弹i是否能引爆j
                int[] ii = bombs[i];
                int[] jj = bombs[j];
                // 下面这行有溢出，先转long，再乘
//                long distance = (long)((ii[0]-jj[0]) * (ii[0]-jj[0])) + (long)((ii[1]-jj[1])*(ii[1]-jj[1]));
                long distance = ((long) (ii[0] - jj[0]) * (ii[0] - jj[0])) + ((long) (ii[1] - jj[1]) * (ii[1] - jj[1]));
                long rr = ((long) ii[2] * ii[2]);
                if (rr >= distance) {
                    graph[i][j] = 1;
                }
            }
        }
        // 2) bfs 枚举每个炸弹可以引爆的个数，同时维护最大值
        int max = -1;
        for (int i = 0; i < m; i++) {
            LinkedList<Integer> q = new LinkedList<Integer>();
            boolean[] flags = new boolean[m];
            q.offer(i);
            flags[i] = true;
            int cnt = 1;
            while (!q.isEmpty()) {
                int head = q.poll();
                for (int j = 0; j < m; j++) {
                    if (graph[head][j] == 1 && !flags[j]) {
                        cnt++;
                        q.offer(j);
                        flags[j] = true;
                    }
                }
            }
            max = Math.max(cnt, max);
        }
        return max;
    }

    public static void main(String[] args) {
        _2101引爆最多的炸弹 o = new _2101引爆最多的炸弹();
//        int[][] bombs = {{1,2,3},{2,3,1},{3,4,2},{4,5,3},{5,6,4}};
//        int[][] bombs = {{1,1,5},{10,10,5}};
        int[][] bombs = {{1, 1, 100000}, {100000, 100000, 1}};
        int res = o.maximumDetonation(bombs);
        System.out.println(res);
    }
}
