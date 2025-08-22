package codefun2000;

import java.util.*;

public class _2331 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt(), n = sc.nextInt();
        sc.nextLine();
        Map<String, Node> memory = new HashMap<>();
        Map<String, Node> roots = new HashMap<>();
        while (n-- > 0) {
            String[] record = sc.nextLine().split(" ");
            String nodeName = record[0];
            String fatherName = record[1];
            int level = Integer.parseInt(record[2]);
            int cnt = Integer.parseInt(record[3]);
            Node node = memory.getOrDefault(nodeName, new Node());
            node.setCnt(level, cnt);
            memory.put(nodeName, node);
            if (!fatherName.equals("*")) {
                Node fatherNode = memory.getOrDefault(fatherName, new Node());
                if (!fatherNode.child.contains(node)) {
                    fatherNode.child.add(node);
                }
            } else {
                roots.put(nodeName, node);
            }
        }
        int cnt = fun(roots, m);
        System.out.println(cnt);
        sc.close();
    }

    static int fun(Map<String, Node> roots, int m) {
        // >m，统计为风险云服务
        int cnt = 0;
        for (Map.Entry<String, Node> entry : roots.entrySet()) {
            Node root = entry.getValue();
            Deque<Node> queue = new ArrayDeque<>();
            queue.offer(root);
            int mm = 0;
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; ++i) {
                    Node first = queue.poll();
                    mm += 5 * first.zero_cnt + 2 * first.one_cnt;
                    for (int j = 0; j < first.child.size(); ++j) {
                        queue.offer(first.child.get(j));
                    }
                }
            }
            if (mm > m) {
                cnt++;
            }
        }
        return cnt;
    }
}

class Node {
    int zero_cnt;
    int one_cnt;
    List<Node> child;

    Node() {
        this.zero_cnt = 0;
        this.one_cnt = 0;
        child = new ArrayList<>();
    }

    Node(int zero_cnt, int one_cnt) {
        this.zero_cnt = zero_cnt;
        this.one_cnt = one_cnt;
        child = new ArrayList<>();
    }

    void setCnt(int level, int cnt) {
        if (level == 0) {
            this.zero_cnt = cnt;
        } else {
            this.one_cnt = cnt;
        }
    }
}

class OfficialSolution2331 {

    // 使用Map来存储每个节点的子节点
    private static Map<String, List<String>> edg = new HashMap<>();
    // 用于存储每个节点的严重问题数
    private static Map<String, Integer> v1 = new HashMap<>();
    // 用于存储每个节点的一般问题数
    private static Map<String, Integer> v2 = new HashMap<>();
    // 统计超过阈值的树的数量
    private static int res = 0;
    // 阈值
    private static int m;

    // 深度优先搜索函数
    private static void dfs(String u) {
        // 遍历当前节点u的所有子节点
        for (String v : edg.getOrDefault(u, new ArrayList<>())) {
            dfs(v);  // 递归遍历子节点
            v1.put(u, v1.getOrDefault(u, 0) + v1.getOrDefault(v, 0)); // 累加子节点的严重问题数
            v2.put(u, v2.getOrDefault(u, 0) + v2.getOrDefault(v, 0)); // 累加子节点的一般问题数
            // 如果u为根节点且其计算值超过阈值，则统计该树
            if (u.equals("*") && (v1.getOrDefault(v, 0) * 5 + v2.getOrDefault(v, 0) * 2) > m) {
                res++;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        m = scanner.nextInt();  // 输入阈值
        int n = scanner.nextInt();  // 输入节点数量
        scanner.nextLine();  // 读取行结束符

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            String a = parts[0];  // 节点名
            String b = parts[1];  // 父节点
            int c = Integer.parseInt(parts[2]);  // 问题级别
            int d = Integer.parseInt(parts[3]);  // 问题数量

            // 根据问题级别更新相应的数量
            if (c == 0) {
                v1.put(a, v1.getOrDefault(a, 0) + d);  // 严重问题
            } else {
                v2.put(a, v2.getOrDefault(a, 0) + d);  // 一般问题
            }

            // 构建树的结构
            edg.putIfAbsent(b, new ArrayList<>());
            if (!edg.get(b).contains(a)) {
                edg.get(b).add(a);  // 将节点a添加为b的子节点
            }
        }

        // 以“*”节点为根开始深度优先搜索
        dfs("*");
        System.out.println(res);  // 输出风险树的数量
    }
}
