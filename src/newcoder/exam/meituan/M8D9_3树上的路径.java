package newcoder.exam.meituan;

import java.util.*;

/**
 * 1、为了不使用Visited数组，将edges设置为单向指向，edges[node1][node2]=1
 * 其中node1是值较小的节点，node2是值较大的节点
 * 这其实是有问题的。针对树，
 * 4
 * 4 2
 * 3 2
 * 1 3
 * abba
 * edges[2][4]=1
 * edges[2][3]=1
 * edges[1][3]=1
 * 那么遍历的时候，1找到3，3再往下没有了，断了。所以这种无向图设置单指向的方式不对
 * 2、当n=10^5时，内存溢出，这是因为使用邻接矩阵存储边关系时，申请的数组的大小为
 * 10^5*10^5 * 4(单位:byte) ~= 40GB
 * 所以说要使用邻接表来存储表关系，另外使用dfs的话，有很大的栈内存使用，如果先用bfs将父子节点关系存储起来就不会这样
 */
public class M8D9_3树上的路径 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] edges = new int[n + 1][n + 1];
        for (int i = 0; i < n - 1; ++i) {  // n-1条边
            int node1 = sc.nextInt();
            int node2 = sc.nextInt();
            edges[node1][node2] = 1;
            edges[node2][node1] = 1;
        }
        sc.nextLine();
        String colors_str = sc.nextLine();
        char[] colors = new char[n + 1];
        for (int i = 0; i < colors_str.length(); ++i) {
            colors[i + 1] = colors_str.charAt(i);
        }
        ans = n;
        fun(edges, colors);
        System.out.println(ans);
        sc.close();
    }

    static void fun(int[][] edges, char[] colors) {
        int n = colors.length;
        int[] pcnt = new int[n];  // 每一个节点的合法路径数，默认为1
        Arrays.fill(pcnt, 1);
        boolean[] visited = new boolean[n];
        dfs(1, visited, pcnt, edges, colors);
    }

    /**
     * 计算节点node的合法路径数，将结果记录于pcnt中
     */
    static int ans;

    static void dfs(int node, boolean[] visited, int[] pcnt, int[][] edges, char[] colors) {
        visited[node] = true;
        for (int j = 1; j < colors.length; ++j) {
            if (!visited[j] && edges[node][j] == 1) {
                dfs(j, visited, pcnt, edges, colors);
                if (colors[node] != colors[j]) {
                    // 为什么要乘，因为是任意两个节点之间的合法路径，而不仅仅是父子树之间合法的路径
                    ans += pcnt[node] * pcnt[j];
                    pcnt[node] += pcnt[j];
                }
            }
        }
    }
}

/**
 * 将塔子哥的python代码转化为java
 */
class Solution2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < n - 1; ++i) {  // n-1条边
            // 将节点下标映射到以based-0的
            int node1 = sc.nextInt() - 1;
            int node2 = sc.nextInt() - 1;
            edges.get(node1).add(node2);
            edges.get(node2).add(node1);
        }
        sc.nextLine();
        String colors_str = sc.nextLine();
        char[] colors = new char[n];
        for (int i = 0; i < colors_str.length(); ++i) {
            colors[i] = colors_str.charAt(i);
        }

//        System.out.println(back);
//        for (int[] ints : back) {
//            System.out.println(Arrays.toString(ints));
//        }
        int res = solve(n, edges, colors);
        System.out.println(res);
        sc.close();
    }

    private static int solve(int n, List<List<Integer>> edges, char[] colors) {
        List<int[]> back = build_back(edges);  // 处理完之后，edges是单向的
        int[] pcnt = new int[n];
        Arrays.fill(pcnt, 1);
        int ans = n;
        for (int i = back.size() - 1; i >= 0; --i) {
            int[] tuple = back.get(i);
            int node = tuple[1];
            for (int next : edges.get(node)) {
                if (colors[node] != colors[next]) {
                    ans += pcnt[node] * pcnt[next];
                    pcnt[node] += pcnt[next];
                }
            }
        }
        return ans;
    }

    /**
     * 使用bfs将树定向化（父->子），并记录父子遍历顺序，
     * 返回值 back 是一个列表，每个元素是（父节点，子节点）
     * bfs可以保证我们在反向遍历 back 时自底向上处理节点
     *
     * @param edges
     */
    private static List<int[]> build_back(List<List<Integer>> edges) {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        List<int[]> res = new ArrayList<>();
        res.add(new int[]{-1, 0});  // 根节点的父亲不存在，所以用-1表示
        while (!queue.isEmpty()) {
            int node = queue.poll();
            // 遍历当前节点的所有孩子节点
            for (int child : edges.get(node)) {
                res.add(new int[]{node, child});  // 记录（父节点，子节点）关系
                edges.get(child).remove(Integer.valueOf(node));  // 孩子节点边关系中删除父亲节点，防止死循环
                queue.offer(child);   // 孩子节点加入队列
            }
        }
        return res;
    }
}
