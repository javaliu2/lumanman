package algorithm.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class _46_全排列 {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        dfs(nums, 0, ans);
        return ans;
    }

    void dfs(int[] nums, int start, List<List<Integer>> ans) {
        if (start == nums.length) {
            List<Integer> one = Arrays.stream(nums).boxed().collect(Collectors.toList());
            ans.add(one);
        } else {
            for (int i = start; i < nums.length; ++i) {
                swap(nums, start, i);
                dfs(nums, start + 1, ans);  // 注意这里是start+1，而不是i+1
                swap(nums, start, i);
            }
        }
    }

    void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        List<Integer> collect = Arrays.stream(arr).boxed().collect(Collectors.toList());
        System.out.println(collect);
    }
}
