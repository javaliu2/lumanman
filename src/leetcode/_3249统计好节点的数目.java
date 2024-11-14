package leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 【题目描述】：https://leetcode.cn/problems/count-the-number-of-good-nodes/description/
 * 【思路】：无向树，用二维数组存储，
 * 计算每一个节点的 孩子节点构成的子树的节点数量，判断这些数量是否相等
 * 1）相等，该节点是 好节点
 * 2）不相等，不是 好节点
 * 【细节】：叶子节点一定是 好节点
 */

/**
 * 【提交记录】：
 * 1、用例：[[6,0],[1,0],[5,1],[2,5],[3,1],[4,3]]，failed，
 * 因为是无向图，不保证edge[0]一定是父节点，这他妈的
 * 可以确定的是，父节点value【一定】大于子节点的value
 * solution: buildTree时，判断edge[0]和edge[1]以确定父、子节点
 * 2、613 / 620 个通过的测试用例【哭了】【面向用例编程】
 */
public class _3249统计好节点的数目 {
    int cnt = 0;
    int[][] edges;

    void traversal(TreeNode node) {
        System.out.println(node.value);
        if (node.childs.size() == 0) {
            return;
        }
        for (TreeNode child : node.childs) {
            traversal(child);
        }
    }

    @Test
    public void testBuildTree() {
//        this.edges = new int[][]{{0,1},{0,2},{1,3},{1,4},{2,5},{2,6}};
//        this.edges = new int[][]{{0,1},{1,2},{2,3},{3,4},{0,5},{1,6},{2,7},{3,8}};
//        this.edges = new int[][]{{0,1},{1,2},{1,3},{1,4},{0,5},{5,6},{6,7},
//                {7,8},{0,9},{9,10},{9,12},{10,11}};
        this.edges = new int[][]{{6, 0}, {1, 0}, {5, 1}, {2, 5}, {3, 1}, {4, 3}};
        int n = edges.length;
        this.hasCreate = new boolean[n];
        TreeNode root = buildTree(0);
        traversal(root);
    }

    boolean hasCreate[];

    TreeNode buildTree(int node) {
        // 1、创建值为node的节点
        TreeNode root = new TreeNode(node);
        // 2、递归创建孩子节点为根节点的树
        for (int i = 0; i < edges.length; i++) {
            if (!hasCreate[i]) {
                if (edges[i][0] == node) {
                    hasCreate[i] = true;
                    TreeNode t = buildTree(edges[i][1]);
                    root.childs.add(t);
                } else if (edges[i][1] == node) {
                    hasCreate[i] = true;
                    TreeNode t = buildTree(edges[i][0]);
                    root.childs.add(t);
                }
            }
        }
        return root;
    }

    int dfs_my(TreeNode root) {
        // 边界
        if (root.childs.size() == 0) {
            cnt++;
            return 1;
        }
        // 统计孩子节点构成的树的节点数量
        int sum = 0, pre = -1;
        boolean flag = true;
        for (TreeNode child : root.childs) {
            int t = dfs_my(child);
            if (pre != -1) {
                if (pre != t) {
                    flag = false;
                }
            }
            pre = t;
            sum += t;
        }
        if (flag) {
            cnt++;
        }
        return sum + 1;
    }

    public int countGoodNodes_my(int[][] edges) {
        int n = edges.length;
        this.edges = edges;
        // 1、构建这棵树
        TreeNode root = buildTree(0);
        // 2、dfs
        dfs_my(root);
        return cnt;
    }

    /**
     * @author 灵茶山艾府
     * @param edges
     * @return
     */
    public int countGoodNodes(int[][] edges) {
        int n = edges.length + 1;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0];
            int y = e[1];
            g[x].add(y);
            g[y].add(x);
        }
        dfs(0, -1, g);
        return ans;
    }

    private int ans;

    private int dfs(int x, int fa, List<Integer>[] g) {
        int size = 1;
        int sz0 = 0;
        boolean ok = true;
        for (int y : g[x]) {
            if (y == fa) {
                continue; // 不能递归到父节点
            }
            int sz = dfs(y, x, g);
            if (sz0 == 0) {
                sz0 = sz; // 记录第一个儿子子树的大小
            } else if (sz != sz0) { // 存在大小不一样的儿子子树
                ok = false; // 注意不能 break，其他子树 y 仍然要递归
            }
            size += sz;
        }
        ans += ok ? 1 : 0;
        return size;
    }

}

class TreeNode {
    int value;
    List<TreeNode> childs;

    TreeNode() {
    }

    TreeNode(int value) {
        this.value = value;
        childs = new ArrayList<>();
    }
}
