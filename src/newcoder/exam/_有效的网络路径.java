package newcoder.exam;

import java.util.ArrayList;
import java.util.List;

public class _有效的网络路径 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param n int整型 节点数（节点编号从 `0` 开始）
     * @param s int整型 源节点编号
     * @param t int整型 目标节点编号
     * @param node_bandwidths int整型一维数组 每个节点的可用带宽数组，索引i对应节点i的带宽
     * @param edges int整型二维数组 边数信息，从左到右分别为：起点节点编号、终点节点编号、链路剩余带宽、传输延迟
     * @param d int整型 需传输的数据量
     * @return int整型
     */
    int[] node_bandwidths;
    int node_num;
    int[][] link_bandwidths;
    int[][] delays;

    public int find_min_delay_path(int node_num, int source, int target, int[] node_bandwidths, int[][] edges, int data_num) {
        // dfs all path
        this.node_bandwidths = node_bandwidths;
        this.node_num = node_num;
        link_bandwidths = new int[node_num][node_num];
        delays = new int[node_num][node_num];
        List<List<Integer>> ll = new ArrayList<>();
        for (int i = 0; i < node_num; ++i) {
            ll.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int bandwidth = edge[2];
            int delay = edge[3];
            ll.get(u).add(v);
            ll.get(v).add(u);
            link_bandwidths[u][v] = bandwidth;
            delays[u][v] = delay;
        }
        boolean[] visited = new boolean[node_num];
        dfs(ll, source, target, data_num, visited, 0);
        if (ans == Integer.MAX_VALUE) {
            return -1;
        }
        return ans;
    }

    int ans = Integer.MAX_VALUE;

    void dfs(List<List<Integer>> ll, int father, int target, int data_num, boolean[] visited, int total_delay) {
        if (father == target) {
            ans = Math.min(ans, total_delay);
            return;
        }
        List<Integer> childs = ll.get(father);
        visited[father] = true;
        for (int child : childs) {
            if (visited[child] || node_bandwidths[child] < data_num || link_bandwidths[father][child] < data_num) {
                continue;
            }
            visited[child] = true;
            dfs(ll, child, target, data_num, visited, total_delay + delays[father][child]);
            visited[child] = false;
        }
        visited[father] = false;
    }
}
