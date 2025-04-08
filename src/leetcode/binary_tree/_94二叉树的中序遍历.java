package leetcode.binary_tree;

import utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class _94二叉树的中序遍历 {

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        func(ans, root);
        return ans;
    }
    void func(List<Integer> ans, TreeNode root) {
        if (root == null) {
            return;
        }
        func(ans, root.left);
        ans.add(root.val);
        func(ans, root.right);
    }

}
