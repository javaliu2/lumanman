package leetcode.binary_tree;

import utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class _236二叉树的最近公共祖先 {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 1、遍历树，分别找到节点p和q的路径节点序列
        List<TreeNode> path_p = new ArrayList<>();
        getPath(root, p, path_p);
        List<TreeNode> path_q = new ArrayList<>();
        getPath(root, q, path_q);
        // 2、max{max_idx} s.t. seq_p[max_idx].val==seq_q[max_idx].val
        int max_idx = 0;
        while (true) {
            if (max_idx < path_p.size() && max_idx < path_q.size()) {
                if (path_p.get(max_idx).val == path_q.get(max_idx).val) {
                    ++max_idx;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        // 3、返回seq_p[max_idx]
        return path_p.get(max_idx - 1);
    }

    private boolean getPath(TreeNode root, TreeNode targetNode, List<TreeNode> path) {
        if (root == null) {
            return false;
        }
        path.add(root);
        if (root.val == targetNode.val) {
            return true;
        }

        boolean isFind = getPath(root.left, targetNode, path);
        if (isFind) {
            return true;
        }
        isFind = getPath(root.right, targetNode, path);
        if (isFind) {
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }
}
