package leetcode.graph;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class _3372连接两棵树后最大目标节点数目I {
    /**
     * tiemout, 不出所料，passed 812/817，wonderful了属于是
     */
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2, int k) {
        // 统计第一课树中每一个节点路径边数<=k所能到达的所有节点数
        // 统计第二课树中，每一个节点路径边数<=k-1所能到达的所有节点数，记录最大值max_value
        // 第一课树中每一个节点的结果为本树所能达到的节点数+第二课树中k-1边数最大值max_value
        Map<Integer, Integer> res = cnt(edges1, k);
        // System.out.println(res);
        Map<Integer, Integer> res2 = cnt(edges2, k - 1);
        int n = edges1.length, max = res2.get(-1);
        int[] ans = new int[n + 1];
        for (Map.Entry<Integer, Integer> entry : res.entrySet()) {
            int node = entry.getKey();
            if (node == -1) {
                continue;
            }
            int cnt = entry.getValue();
            ans[node] = cnt + max;
        }
        return ans;
    }

    private Map<Integer, Integer> cnt(int[][] edge, int k) {
        int n = edge.length;
        // 1. build tree
        int[][] tree = new int[n + 1][n + 1];
        for (int[] e : edge) {
            int i = e[0], j = e[1];
            tree[i][j] = 1;
            tree[j][i] = 1;
        }
        // 2. bfs
        Map<Integer, Integer> res = new HashMap<>();
        int max = -1;
        for (int node = 0; node <= n; ++node) {
            int cnt = 1;  // 自身
            int kk = k;
            if (kk < 0) {
                cnt = 0;
            }
            Queue<Integer> q = new ArrayDeque<>();
            boolean[] visited = new boolean[n + 1];
            q.offer(node);
            visited[node] = true;
            while (!q.isEmpty() && kk-- > 0) {
                int size = q.size();
                while (size-- > 0) {
                    int curr_node = q.poll();
                    for (int j = 0; j <= n; ++j) {
                        if (!visited[j] && tree[curr_node][j] == 1) {
                            cnt++;
                            visited[j] = true;
                            q.offer(j);
                        }
                    }
                }
            }
            if (cnt > max) {
                max = cnt;
            }
            res.put(node, cnt);
        }
        // 3. return
        res.put(-1, max);
        return res;
    }
}
