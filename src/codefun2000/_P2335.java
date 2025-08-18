package codefun2000;

import java.util.*;

public class _P2335 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 枚举exposed[i]，计算每一个节点可以攻击得到的节点
        // 返回最大值的索引，如果有多个最大值，返回索引最小的
        int n = sc.nextInt();
        int[][] graph = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                graph[i][j] = sc.nextInt();
            }
        }
        sc.nextLine();
        String s = sc.nextLine();
        String[] ss = s.split(" ");
        int[] exposed = new int[ss.length];
        for (int i = 0; i < exposed.length; ++i) {
            exposed[i] = Integer.parseInt(ss[i]);
        }
        int res = fun(graph, exposed);
        System.out.println(res);
        sc.close();
    }

    /**
     * 单源bfs，计算每一个暴露节点可达的所有节点，返回可达节点数最大的节点索引
     * AC14，有一个AC不了。其实这个策略不完全对。
     * 下线哪个节点，剩下的其他节点均可以被攻击，所以是多源BFS
     * @param graph
     * @param exposed
     * @return
     */
    static int fun(int[][] graph, int[] exposed) {
        int n = graph.length;
        List<int[]> cnts = new ArrayList<>();
        for (int i = 0; i < exposed.length; ++i) {
            int cnt = 1;
            int exposed_node = exposed[i];
            // 没有访问过 且 不是自己
            boolean[] visited = new boolean[n];
            visited[exposed_node] = true;
            Deque<int[]> queue = new ArrayDeque<>();
            queue.offer(new int[]{exposed_node, 10});
            while (!queue.isEmpty()) {
                int[] tuple = queue.poll();
                int node = tuple[0], p = tuple[1];
                for (int j = 0; j < n; ++j) {
                    if (visited[j]) {
                        continue;
                    }
                    if (graph[node][j] > 0 && graph[node][j] < p) {
                        queue.offer(new int[]{j, graph[node][j]});
                        cnt++;
                        visited[j] = true;
                    }
                }
            }
            cnts.add(new int[]{cnt, exposed_node});
        }
        cnts.sort((cnt1, cnt2) -> {
            if (cnt1[0] == cnt2[0]) {
                return cnt1[1] - cnt2[1];
            }
            return cnt2[0] - cnt1[0];
        });
        for (int[] cnt : cnts) {
            System.out.println(Arrays.toString(cnt));
        }
        return cnts.get(0)[1];
    }

    static int fun2(int[][] graph, int[] exposed) {
        int n = graph.length;
        List<int[]> cnts = new ArrayList<>();
        // 计算exposed[i]下线时，剩下暴露节点可达的所有节点数cnt
        for (int i = 0; i < exposed.length; ++i) {
            int cnt = exposed.length - 1;
            Deque<int[]> queue = new ArrayDeque<>();
            boolean[] visited = new boolean[n];
            // 除了我自己，其他节点均加入队列
//            for (int j = 0; j < n; ++j) {
//                if (i == j) {
//                    continue;
//                }
//                queue.offer(new int[]{exposed[j], 10});
//                visited[j] = true;
//            }
            for (int j = 0; j < exposed.length; ++j) {
                if (exposed[i] == exposed[j]) {
                    continue;
                }
                queue.offer(new int[]{exposed[j], 10});
                visited[exposed[j]] = true;  // 应该是把当前节点exposed[j]置为true
            }
            while (!queue.isEmpty()) {
                int[] tuple = queue.poll();
                int node = tuple[0], p = tuple[1];
                for (int j = 0; j < n; ++j) {
                    if (visited[j]) {
                        continue;
                    }
                    if (graph[node][j] > 0 && graph[node][j] < p) {
                        queue.offer(new int[]{j, graph[node][j]});
                        cnt++;
                        visited[j] = true;
                    }
                }
            }
            cnts.add(new int[]{cnt, exposed[i]});
        }
        cnts.sort((cnt1, cnt2) -> {
            if (cnt1[0] == cnt2[0]) {
                return cnt1[1] - cnt2[1];
            }
            return cnt2[0] - cnt1[0];
        });
        for (int[] cnt : cnts) {
            System.out.println(Arrays.toString(cnt));
        }
        return cnts.get(0)[1];
    }
}

class SolutionOfficial {
    static int[][] a = new int[40][40];
    static int n, m = 1;      // m 从 1 开始计数
    static int[] e = new int[40];
    static int[] vis = new int[40]; // 记录到达每个节点的最高权限，初始为 -1

    // bfs 模拟攻击，返回安全节点数
    static int bfs(int x) {
        Queue<int[]> q = new ArrayDeque<>();
        // 初始化
        for (int i = 0; i < n; i++) vis[i] = -1;
        // 多源入队
        for (int i = 1; i <= m; i++) {
            if (i == x) continue;
            vis[e[i]] = 10;
            q.offer(new int[]{e[i], 10});
        }
        // 带状态 BFS
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int u = cur[0], p = cur[1];
            if (p < vis[u]) continue;
            for (int v = 0; v < n; v++) {
                int req = a[u][v];
                if (req > 0 && p >= req && vis[v] < req) {
                    vis[v] = req;
                    q.offer(new int[]{v, req});
                }
            }
        }
        // 统计未被攻击节点数
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (vis[i] < 0) res++;
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = sc.nextInt();
        // 读取暴露节点
        while (sc.hasNextInt()) {
            e[m++] = sc.nextInt();
        }
        m--;
        Arrays.sort(e, 1, m + 1);

        int mx = -1, id = 1;
        for (int i = 1; i <= m; i++) {
            int now = bfs(i);
            if (now > mx) {
                mx = now;
                id = i;
            }
        }
        System.out.println(e[id]);
    }
}