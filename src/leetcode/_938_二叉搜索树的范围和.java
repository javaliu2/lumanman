package leetcode;

import utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class _938_二叉搜索树的范围和 {
    class Solution {
        public int rangeSumBST(TreeNode root, int low, int high) {
            // 1、中序遍历，得到树中所有元素升序的列表
            List<Integer> l = new ArrayList<Integer>();
            dfs(root, l);
            // 2、遍历列表，找到第一个>=low的元素，从该元素开始累加，直到元素值>high
            int i = 0;
            while (l.get(i) < low) {
                i++;
            }
            int res = 0;
            while (i < l.size() && l.get(i) <= high) {
                res += l.get(i);
                i++;
            }
            return res;
        }
        void dfs(TreeNode node, List<Integer> l) {
            if (node == null) {
                return;
            }
            dfs(node.left, l);
            l.add(node.val);
            dfs(node.right, l);
        }
    }
    // 0m niubility
    public int rangeSumBST(TreeNode node, int low, int high) {
        if (node == null) {
            return 0;
        }
        if (node.val < low) {
            return rangeSumBST(node.right, low, high);
        }
        if (node.val > high) {
            return rangeSumBST(node.left, low, high);
        }
        return node.val + rangeSumBST(node.left, low, high) + rangeSumBST(node.right, low, high);
    }
}
