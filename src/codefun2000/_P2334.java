package codefun2000;
import java.util.*;

/**
 * 针对测试用例:
 * 5
 * 1] 0 0 43 0 82
 * 2] 0 0 0 0 37
 * 3] 43 0 0 36 87
 * 4] 0 0 36 0 0
 * 5] 82 37 87 0 0
 * <p>
 * iteration 队列     ans      visited
 * 0         {1}      0       {}    // 初始化
 * 1         {3，5}   43+82    {1}  // 执行完这一次循环之后元素的值
 * 2         {5，4，5} 43+82+36+87 {1，3}
 * 3         {4，5，2} 43+82+36+87+37 {1，3，5}
 * 4         {5，2}  43+82+36+87+37 {1，3，4，5}
 * 5         {2，2} 43+82+36+87+37+37 {1，3，4，5}
 * 6         {2}    43+82+36+87+37+37 {1，2，3，4，5}
 * <p>
 * 遍历1]的时候，将{3,5}加入队列，poll队头为3，遍历3的时候，将{4，5}加入队列，5被添加两次
 * 当前队列元素为{5，4，5}，poll队头为5，将2加入队列，队列变为{4，5，2}，
 * poll队头为4，无任何元素加入队列，队列变为{5，2}，poll队头为5，此时visited[2]为false，故将37又加一次，将2加入队列，{2，2}
 */
public class _P2334 {
    /**
     * fa[i]=i表明该节点为集合根节点，即代表节点
     */
    class UnionFind {
        private int[] fa;

        UnionFind(int n) {
            fa = new int[n];
            for (int i = 0; i < n; i++) {
                fa[i] = i;  // 每一个元素自成一类
            }
        }

        void union(int x, int y) {
            int fax = find(x);
            int fay = find(y);
            if (fax != fay) {
                fa[fax] = fay;  // 合并
            }
        }

        int find(int x) {
            if (fa[x] != x) {
                fa[x] = find(fa[x]);  // 路径压缩
            }
            return fa[x];
        }

        @Override
        public String toString() {
            return Arrays.toString(fa);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] M = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                M[i][j] = sc.nextInt();
            }
        }
        // bfs
        Deque<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[n];
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }
            int sum = 0;
            queue.offer(i);
            while (!queue.isEmpty()) {
                int father = queue.poll();
                if (visited[father]) {
                    continue;
                }
                for (int child = 0; child < n; child++) {
                    if (!visited[child] && M[father][child] > 0) {
                        sum += M[father][child];
                        queue.offer(child);
                    }
                }
                visited[father] = true;
            }
            ans.add(sum);
        }
        for (int i = 0; i < ans.size(); i++) {
            if (i + 1 < ans.size()) {
                System.out.print(ans.get(i) + " ");
            } else {
                System.out.print(ans.get(i));
            }
        }
        sc.close();
    }

    void fun_official() {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine()); // 读取图片数量
        int[][] a = new int[n][n]; // 相似度矩阵
        for (int i = 0; i < n; i++) {
            String[] line = scanner.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                a[i][j] = Integer.parseInt(line[j]);
            }
        }

        UnionFind uf = new UnionFind(n);
        int[] ans = new int[n]; // 每个相似类的相似度之和
        // 构建并查集
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] > 0) {
                    uf.union(i, j);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] > 0 && uf.find(i) == uf.find(j)) {
                    ans[uf.find(i)] += a[i][j];
                    a[j][i] = 0; // 防止重复计算
                }
            }
        }
        // 根节点（代表节点）保存了该分类的相似度和
        // 因为存在相似度为0的分类，所以不能使用0来判断
        for (int i = 0; i < n; ++i) {
            if (uf.find(i) != i) {
                ans[i] = -1;
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (ans[i] == -1) {
                continue;
            }
            list.add(ans[i]);
        }
        list.sort(Collections.reverseOrder());
        for (int i = 0; i < list.size(); i++) {
            if (i + 1 < list.size()) {
                System.out.print(list.get(i) + " ");
            } else {
                System.out.print(list.get(i));
            }
        }
    }
}