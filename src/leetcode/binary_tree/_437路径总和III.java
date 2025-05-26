package leetcode.binary_tree;

import utils.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class _437路径总和III {
    void fun() {
        int a = 0;  // 局部变量必须显式初始化
        System.out.println(a);
    }

    public int pathSum(TreeNode root, int targetSum) {
        //int cnt = rootSum(root, targetSum);
        //System.out.println(cnt);
        return dfs(root, targetSum);
    }

    /**
     * 遍历树上每个节点，计算以该节点为树的路径和等于targetSum的个数
     */
    private int dfs(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }
        int res = rootSum(root, targetSum);
        res += dfs(root.left, targetSum) + dfs(root.right, targetSum);
        return res;
    }

    /**
     * 统计以节点p为根节点的所有路径中，节点值和等于targetSum的路径个数
     * 需要考虑上溢，
     * 针对测试用例：[1000000000,1000000000,null,294967296,null,1000000000,null,1000000000,null,1000000000]
     */
    private int rootSum(TreeNode p, long targetSum) {
        if (p == null) {
            return 0;
        }
        int val = p.val;
        int res = 0;
        if (val == targetSum) {
            res += 1;
        }
        res += rootSum(p.left, targetSum - val);
        res += rootSum(p.right, targetSum - val);
        return res;
    }

    /**
     * @author LC Official
     */
    class Solution {
        public int pathSum(TreeNode root, int targetSum) {
            Map<Long, Integer> prefix = new HashMap<Long, Integer>();
            prefix.put(0L, 1);
            return dfs(root, prefix, 0, targetSum);
        }

        public int dfs(TreeNode root, Map<Long, Integer> prefix, long curr, int targetSum) {
            if (root == null) {
                return 0;
            }

            int ret = 0;
            curr += root.val;

            ret = prefix.getOrDefault(curr - targetSum, 0);
            prefix.put(curr, prefix.getOrDefault(curr, 0) + 1);
            ret += dfs(root.left, prefix, curr, targetSum);
            ret += dfs(root.right, prefix, curr, targetSum);
            prefix.put(curr, prefix.getOrDefault(curr, 0) - 1);

            return ret;
        }
    }

}
