package leetcode.binary_tree;

import utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class _114二叉树展开为链表 {
    public void flatten(TreeNode root) {
        // 先序遍历树，将节点存储于数组，然后遍历数组，修改节点的指针
        List<TreeNode> list = new ArrayList<>();
        preOrder(root, list);
        for (int i = 0; i < list.size() - 1; ++i) {
            list.get(i).left = null;
            list.get(i).right = list.get(i + 1);
        }
    }
    private void preOrder(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }
        list.add(root);
        preOrder(root.left, list);
        preOrder(root.right, list);
    }
}
