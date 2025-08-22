package codefun2000;

import java.util.*;

public class _服务逃生 {
    static final int INF = (int) 1e9;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();  // 节点个数
        int[][] graph = new int[n][n];  // 节点关系图（邻接矩阵）
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                graph[i][j] = sc.nextInt();
                if (graph[i][j] == -1) {
                    graph[i][j] = INF;  // 无连接设置为无穷大
                }
            }
        }
        int[] capacity = new int[n];
        for (int i = 0; i < n; ++i) {
            capacity[i] = sc.nextInt();
        }
        int faultyNode = sc.nextInt();
        int faultyCapacity = sc.nextInt();
        // dijkstra算法
        int[] d = new int[n]; // 最短路径数组
        boolean[] st = new boolean[n];  // 标记数组，是否已经确定最短路径
        Arrays.fill(d, INF); // 初始距离为无穷大

        d[faultyNode] = 0;  // 计算故障节点到其他所有节点的最短路径
        // 计算剩下的n-1个节点到故障节点的距离
        for (int i = 0; i < n - 1; ++i) {
            int t = -1;
            // 查找未确定最短路径的节点t
            for (int j = 0; j < n; ++j) {
                if (!st[j] && (t == -1 || d[j] < d[t])) {
                    t = j;
                }
            }
            if (t == -1 || d[t] == INF) {
                break;  // 所有节点均已确定最短路 或者 原点到当前节点没有边
            }
            st[t] = true;
            for (int j = 0; j < n; ++j) {
                d[j] = Math.min(d[j], d[t] + graph[t][j]);  // 更新最短路径
            }
        }
        d[faultyNode] = INF;  // 恢复故障节点的距离

        List<int[]> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ans.add(new int[]{d[i], i, capacity[i]});
        }

        ans.sort(Comparator.comparingInt(a -> a[0]));  // 按照距离排序
        int cur = 0; // 当前已选节点的总容量
        List<Integer> res = new ArrayList<>();

        // 选择满足需求的节点
        for (int[] t : ans) {
            if (t[0] >= INF) break; // 距离无穷大时停止

            cur += t[2]; // 累加当前节点的剩余容量
            res.add(t[1]); // 记录当前节点编号
            if (cur >= faultyCapacity) break; // 如果达到需求，停止
        }

        // 输出结果
        for (int i = 0; i < res.size(); i++) {
            System.out.print(res.get(i));
            if (i != res.size() - 1) System.out.print(" ");
        }
        System.out.println();
        sc.close();
    }
}
