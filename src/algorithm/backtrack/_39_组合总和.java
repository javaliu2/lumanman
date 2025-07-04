package algorithm.backtrack;

import java.util.ArrayList;
import java.util.List;

public class _39_组合总和 {
        List<List<Integer>> ans = new ArrayList<>();
        public List<List<Integer>> combinationSum(int[] candidates, int target) {
            List<Integer> path = new ArrayList<>();
            dfs(0, candidates, target, path);
            return ans;
        }

        void dfs(int current, int[] candidates, int target, List<Integer> path){
            if (target == 0) {
                if (!ans.contains(path)) {  // 去重
                    ans.add(new ArrayList<>(path));
                }
            }
            if (current == candidates.length) {
                return;
            }
            // 1）选当前元素
            int num = candidates[current], cnt = 1;
            while (target - cnt * num >= 0) {
                for (int i = 0; i < cnt; ++i) {
                    path.add(num);
                }
                dfs(current + 1, candidates, target - cnt * num, path);
                path.subList(path.size()-cnt, path.size()).clear();
                cnt++;
            }
            // 2）不选当前元素
            dfs(current + 1, candidates, target, path);
        }
}
