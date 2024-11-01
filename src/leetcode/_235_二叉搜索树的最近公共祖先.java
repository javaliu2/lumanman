package leetcode;

import utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class _235_二叉搜索树的最近公共祖先 {
    class Solution {
        List<TreeNode> l;
        List<TreeNode> l2;
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            //  1、遍历树，保存路径节点
            List<TreeNode> path = new ArrayList<TreeNode>();
            dfs(root, path, p, q);
            //  2、寻找两个列表相同值最大的索引
            int i = 0, len = Math.min(l.size(), l2.size());
            while (i < len) {
                if (l.get(i).val == l2.get(i).val) {
                    i++;
                } else {
                    break;
                }
            }
            return l.get(i - 1);
        }
        void dfs(TreeNode node, List<TreeNode> path, TreeNode p, TreeNode q) {
            if (node == null) {
                return;
            }
            path.add(node);
            if (p.val == node.val) {
                l = new ArrayList<>(path);
            }
            if (q.val == node.val) {
                l2 = new ArrayList<>(path);
            }
            dfs(node.left,  path, p, q);
            dfs(node.right, path, p, q);
            path.remove(path.size() - 1);
        }
    }
    class Solution2 {
        /*
        我们从根节点开始遍历；
        如果当前节点的值大于p和q的值，说明p和q应该在当前节点的左子树，因此将当前节点移动到它的左子节点；
        如果当前节点的值小于p和q的值，说明p和q 应该在当前节点的右子树，因此将当前节点移动到它的右子节点；
        如果当前节点的值不满足上述两条要求，那么说明当前节点就是「分岔点」。
        此时，p和q要么在当前节点的不同的子树中，要么其中一个就是当前节点。
        */
        // 这个方法巧妙
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            TreeNode ancestor = root;
            while (true) {
                if (p.val < ancestor.val && q.val < ancestor.val) {
                    ancestor = ancestor.left;
                } else if (p.val > ancestor.val && q.val > ancestor.val) {
                    ancestor = ancestor.right;
                } else {
                    break;
                }
            }
            return ancestor;
        }
    }
}
