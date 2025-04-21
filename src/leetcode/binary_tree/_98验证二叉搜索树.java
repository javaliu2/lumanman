package leetcode.binary_tree;

import utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class _98验证二叉搜索树 {
    List<Integer> nums = new ArrayList<>();

    public boolean isValidBST_my(TreeNode root) {
        return inorder_traversal(root);
    }

    boolean inorder_traversal(TreeNode root) {
        if (root == null) {
            return true;
        }
        boolean vaild = inorder_traversal(root.left);
        if (!vaild) {
            return vaild;
        }
        if (nums.size() > 0) {
            if (nums.get(nums.size() - 1) >= root.val) {
                return false;
            }
        }
        nums.add(root.val);
        vaild = inorder_traversal(root.right);
        return vaild;
    }

    /**
     * @author Leetcode Official
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode node, long lower, long upper) {
        if (node == null) {
            return true;
        }
        if (node.val <= lower || node.val >= upper) {
            return false;
        }
        return isValidBST(node.left, lower, node.val) && isValidBST(node.right, node.val, upper);
    }
}
