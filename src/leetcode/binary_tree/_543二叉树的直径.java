package leetcode.binary_tree;

import utils.TreeNode;

public class _543二叉树的直径 {
    int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        /**
         * func(root): 计算以root为根节点的树，它所有路径中的最大长度
         * func(root) = func(root.left) + func(root.right) # 左子树的最大长度 + 右子树的最大长度
         * 一定包含根节点，因为只有这样，才能保证最终长度最大
         * 【纠正】：不一定一定经过根节点，假设root.left为空子树，right为很多节点的子树，那么右子树的最大长度就是最大的了，
         * 右子树叶子节点到左子树（假设只有一个节点）的距离小于右子树自己两个叶子节点之间的距离。
         * 所以上面的断论有失偏颇，不完全是这样。
         */
        maxLength(root);
        return max;
    }

    int maxLength(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left_length = maxLength(root.left);
        int right_length = maxLength(root.right);
        // left_length + right_length: 以root为根节点子树的“直径”
        max = Math.max(max, left_length + right_length);  // 维护整棵树的最大值
        return Math.max(left_length, right_length) + 1;
    }
}
