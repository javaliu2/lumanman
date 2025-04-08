package leetcode.binary_tree;

import utils.TreeNode;

public class _226翻转二叉树 {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode _right = invertTree(root.left);
        root.left = invertTree(root.right);
        root.right = _right;
        return root;
    }
}
