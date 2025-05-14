package leetcode.binary_tree;

import utils.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class _105通过前序与中序遍历序列构造二叉树 {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 记录inorder中元素与索引的映射
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; ++i) {
            map.put(inorder[i], i);
        }
        return dfs(preorder, inorder, map, 0, preorder.length - 1, 0, preorder.length - 1);
    }

    private TreeNode dfs(int[] preorder, int[] inorder, Map<Integer, Integer> map, int preLeft, int preRight, int inLeft, int inRight) {
        // 边界
        if (preLeft > preRight) {
            return null;
        }
        // 创建根节点
        int root_val = preorder[preLeft];
        TreeNode root = new TreeNode(root_val);
        int root_idx = map.get(root_val);
        // 得到左子树中的节点数目
        int size_left_subtree = root_idx - inLeft;  // 通过中序遍历数组得到左子树节点的个数
        // 递归创建左子树和右子树
        root.left = dfs(preorder, inorder, map, preLeft + 1, preLeft + size_left_subtree, inLeft, root_idx - 1);
        root.right = dfs(preorder, inorder, map, preLeft + size_left_subtree + 1, preRight, root_idx + 1, inRight);
        return root;
    }
}
