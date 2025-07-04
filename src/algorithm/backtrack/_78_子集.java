package algorithm.backtrack;

import java.util.ArrayList;
import java.util.List;

public class _78_子集 {
    /**
     * 枚举
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets_enumeration(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        // 枚举二进制数，其位上为1表示子集包括该元素
        int n = nums.length;
        int start = 0, end = (1 << n) - 1;
        // 每一个i就是一个集合的二进制表达，对应位置上为1，表明包括该元素，否则不包括
        for (int i = start; i <= end; ++i) {
            int j = 0, t = i;
            List<Integer> one = new ArrayList<>();
            while (t != 0) {
                if ((t & 1) == 1) {
                    one.add(nums[j]);
                }
                j++;
                t >>= 1;
            }
            ans.add(one);
        }
        return ans;
    }


    List<List<Integer>> ans;

    /**
     * 回溯，子集问题可以抽象为"找到长度为n的序列a的所有子序列"
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets_backtrack(int[] nums) {
        List<Integer> path = new ArrayList<>();
        ans = new ArrayList<>();
        dfs(0, nums, path);
        return ans;
    }

    void dfs(int current, int[] nums, List<Integer> path){
        // 当前位置的元素[current]有两种情况，选或者不选
        // 1）选的话，将当前元素加入path，递归处理子问题
        // 回溯：处理完毕之后，需要将加入的元素删除
        // 2）不选当前元素的话，直接递归子问题
        if (current == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }
        path.add(nums[current]);
        dfs(current + 1, nums, path);
        path.remove(path.size() - 1);
        dfs(current + 1, nums, path);
    }
    public static void main(String[] args) {
        _78_子集 obj = new _78_子集();
        int[] nums = {1,2,3};
        List<List<Integer>> subsets = obj.subsets_enumeration(nums);
        System.out.println(subsets);
    }
}
