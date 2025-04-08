package leetcode.binary_tree;

import utils.TreeNode;

public class _104二叉树的最大深度 {
    /**
     * 递归 + 回溯
     * 遍历整棵树，记录最大深度
     */
    int ans = 0;

    /**
     * My Solution
     * @param root
     * @return
     */
    public int maxDepth_ms(TreeNode root) {
        dfs(root, 0);
        return ans;
    }

    void dfs(TreeNode root, int k) {
        if (root == null) {
            ans = Math.max(k, ans);
            return;
        }
        dfs(root.left, k + 1);
        dfs(root.right, k + 1);
    }

    /**
     * @author Leetcode Official
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int leftHeight = maxDepth(root.left);  // 左子树高度
            int rightHeight = maxDepth(root.right);  // 右子树高度
            return Math.max(leftHeight, rightHeight) + 1;  // 两者高度较大值+1, 即以root为根节点的子树的高度
        }
    }
}
