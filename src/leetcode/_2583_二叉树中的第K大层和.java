package leetcode;
import utils.TreeNode;

import java.util.*;

public class _2583_二叉树中的第K大层和 {
    public int kthLargestLevelSum(TreeNode root, int k) {
        // bfs遍历树，获取每一层元素和，将其加入列表，对列表排序
        // k > l.length，返回-1，否则返回index为k-1的元素值
        List<Integer> l = bfs(root);
        int length = l.size();
        if (k > length) {
            return -1;
        }
        return l.get(length - k);
    }
    List<Integer> bfs(TreeNode root) {
        List<Integer> l = new ArrayList<Integer>();
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        while (!q.isEmpty()) {
            int len = q.size();
            int sum = 0;
            for (int i = 0; i < len; i++) {
                TreeNode top = q.poll();
                sum += top.val;
                if (top.left != null) {
                    q.offer(top.left);
                }
                if (top.right != null) {
                    q.offer(top.right);
                }
            }
            l.add(sum);
        }
//        Collections.reverse(l);  // 反转而不是逆序
        Collections.sort(l);
        return l;
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(226750);
        TreeNode node2 = new TreeNode(87336);
        TreeNode root = new TreeNode(605481);
        root.right=node2;
        node2.right=node1;

        _2583_二叉树中的第K大层和 obj = new _2583_二叉树中的第K大层和();
        int res = obj.kthLargestLevelSum(root, 1);
        System.out.println(res);
    }
}
