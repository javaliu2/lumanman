package leetcode.binary_tree;

import utils.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class _230二叉搜索树中第K小的元素 {
    public int kthSmallest(TreeNode root, int k) {
        /**
         * 中序遍历，拿到所有数，然后返回第k个数
         */
        List<Integer> nums = new ArrayList<>();
        inorder_traversal(root, nums);
        return nums.get(k - 1);
    }

    private void inorder_traversal(TreeNode root, List<Integer> nums) {
        if (root == null) {
            return;
        }
        inorder_traversal(root.left, nums);
        nums.add(root.val);
        inorder_traversal(root.right, nums);
    }

    /**
     * LeetCode Official
     * 采取迭代遍历的方式不需要遍历完整棵树
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest_lo(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            --k;
            if (k == 0) {
                break;
            }
            root = root.right;
        }
        return root.val;
    }
}
