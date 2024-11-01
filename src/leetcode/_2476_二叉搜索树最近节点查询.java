package leetcode;

import utils.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class _2476_二叉搜索树最近节点查询 {
    // 过32个样例，超时
    public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
        // 遍历树，将每一个值加入列表
        List<Integer> l = new ArrayList<Integer>();
        dfs(root, l);
        // 遍历queries，找到min和max
        // Collections.sort(l);
        int n = queries.size();
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int cur = queries.get(i);
            int le = 0, r = l.size() - 1;
            List<Integer> ll = new ArrayList<>();
            while (r >= 0 && l.get(r) > cur) {
                r--;
            }
            while (le < l.size() && l.get(le) < cur) {
                le++;
            }
            if (r >= 0) {
                ll.add(l.get(r));
            } else {
                ll.add(-1);
            }
            if (le < l.size()) {
                ll.add(l.get(le));
            } else {
                ll.add(-1);
            }
            res.add(ll);
        }
        return res;
    }

    void dfs(TreeNode node, List<Integer> l) {
        if (node == null) {
            return;
        }
        // 二叉搜索树，那么按照左根右的顺序遍历就是升序
        dfs(node.left, l);
        l.add(node.val);
        dfs(node.right, l);
    }

    // 采用二分查找，比顺序遍历效率要高
    // 放弃了，乱七八糟
    public List<List<Integer>> closestNodes2(TreeNode root, List<Integer> queries) {
        List<Integer> l = new ArrayList<Integer>();
        dfs(root, l);
        int n = queries.size();
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int cur = queries.get(i);
            int index = binarySearch(l, cur);
            List<Integer> ll = new ArrayList<>();
            if (l.get(index) == cur) {
                ll.add(cur);
                ll.add(cur);
            } else {
                // index为0，找到的是大于cur的最小值
                // index为size()-1，找到的是小于cur的最大值
                // index在（0, size()-1）范围内，就正常处理
                if (index == 0) {
                    ll.add(-1);
                    ll.add(l.get(index));
                } else if (index == l.size() - 1) {
                    ll.add(l.get(index));
                    ll.add(-1);
                } else {
                    ll.add(l.get(index));
                    ll.add(l.get(index + 1));
                }
            }
            res.add(ll);
        }
        return res;
    }

    // target存在，返回其索引；
    // 不存在，返回比target小的最大值的索引
    int binarySearch(List<Integer> l, int target) {
        int left = 0, right = l.size() - 1;
        int middle = (right + left) / 2;
        while (left < right) {
            int current = l.get(middle);
            if (current < target) {
                left = middle + 1;
            } else if (current > target) {
                right = middle - 1;
            } else {
                return middle;
            }
            middle = (right + left) / 2;
        }
        return middle;
    }
    class Solution {
        public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
            List<Integer> arr = new ArrayList<Integer>();
            dfs(root, arr);
            List<List<Integer>> res = new ArrayList<List<Integer>>();
            for (int val : queries) {
                int maxVal = -1, minVal = -1;
                int idx = binarySearch(arr, val);
                if (idx != arr.size()) {
                    maxVal = arr.get(idx);
                    if (arr.get(idx) == val) {
                        minVal = val;
                        List<Integer> list = new ArrayList<Integer>();
                        list.add(minVal);
                        list.add(maxVal);
                        res.add(list);
                        continue;
                    }
                }
                if (idx > 0) {
                    minVal = arr.get(idx - 1);
                }
                List<Integer> list2 = new ArrayList<Integer>();
                list2.add(minVal);
                list2.add(maxVal);
                res.add(list2);
            }
            return res;
        }

        public void dfs(TreeNode root, List<Integer> arr) {
            if (root == null) {
                return;
            }
            dfs(root.left, arr);
            arr.add(root.val);
            dfs(root.right, arr);
        }

        public int binarySearch(List<Integer> arr, int target) {
            int low = 0, high = arr.size();
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (arr.get(mid) >= target) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            return low;
        }
    }
}

