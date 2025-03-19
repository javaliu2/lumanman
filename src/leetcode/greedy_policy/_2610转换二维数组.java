package leetcode.greedy_policy;

import java.util.*;

public class _2610转换二维数组 {
    public List<List<Integer>> findMatrix(int[] nums) {
        /**
         * 贪心，在处理每一行的时候，尽可能多的添加不同的元素
         */
        Map<Integer, Integer> cnts = new HashMap<>();
        for (int num : nums) {
            int cnt = cnts.getOrDefault(num, 0) + 1;
            cnts.put(num, cnt);
        }
        // 遍历cnts，将每一个key加入list，并且将其计数减一
        List<List<Integer>> ans = new ArrayList<>();
        boolean flag = true;
        while (flag) {
            flag = false;
            List<Integer> arr = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : cnts.entrySet()) {
                int key = entry.getKey(), cnt = entry.getValue();
                if (cnt > 0) {
                    arr.add(key);
                    cnts.put(key, cnt - 1);
                }
            }
            if (arr.size() > 0) {
                flag = true;
                ans.add(arr);
            }
        }
        return ans;
    }

    /**
     * @author Leetcode Official
     * 思路与我一致，只不过写法更优雅
     */
    public List<List<Integer>> findMatrix_lo(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            cnt.put(x, cnt.getOrDefault(x, 0) + 1);
        }

        List<List<Integer>> ans = new ArrayList<>();
        while (!cnt.isEmpty()) {
            List<Integer> arr = new ArrayList<>();
            Iterator<Map.Entry<Integer, Integer>> it = cnt.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, Integer> entry = it.next();
                entry.setValue(entry.getValue() - 1);
                arr.add(entry.getKey());
                if (entry.getValue() == 0) {
                    it.remove();
                }
            }
            ans.add(arr);
        }
        return ans;
    }
}
