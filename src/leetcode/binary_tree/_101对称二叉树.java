package leetcode.binary_tree;

import utils.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class _101对称二叉树 {
    public boolean isSymmetric_recursive(TreeNode root) {
        if (root == null) {
            return true;
        }
        return check(root.left, root.right);
    }
    boolean check(TreeNode l, TreeNode r) {
        if (l == null && r == null) {
            return true;
        }
        if (l == null) {
            return false;
        }
        if (r == null) {
            return false;
        }
        if (l.val != r.val) {
            return false;
        }
        // 递归检查，左子树的左子树，右子树的右子树（要求镜像）
        return check(l.left, r.right) && check(l.right, r.left);
    }

    public boolean isSymmetric_iteration(TreeNode root) {
        return check_iter(root, root);
    }

    public boolean check_iter(TreeNode u, TreeNode v) {
        Queue<TreeNode> q = new LinkedList<>();  // 左边是API接口，右边是功能实现
        Deque<TreeNode> s = new LinkedList<>();
        q.offer(u);
        q.offer(v);
        while (!q.isEmpty()) {
            u = q.poll();
            v = q.poll();
            if (u == null && v == null) {
                continue;
            }
            // 以上条件判断不成立的话，存在两种条件
            // 1) u != null 等价于 v == null
            // 2) v != null 等价于 u == null
            if (u == null || v == null || u.val != v.val) {
                return false;
            }
            q.offer(u.left);
            q.offer(v.right);
            q.offer(u.right);
            q.offer(v.left);
        }
        return true;
    }
}
