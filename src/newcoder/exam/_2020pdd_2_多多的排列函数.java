package newcoder.exam;

import java.util.*;

public class _2020pdd_2_多多的排列函数 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        while (t-- > 0) {
            int n = in.nextInt();
            int[] res = fun(n);
            System.out.printf("%d %d\n", res[0], res[1]);
        }
    }

    static List<List<Integer>> ans;

    /**
     * brute force失败，当n为10的时候，超时
     * @param n
     * @return
     */
    static int[] fun(int n) {
        // 1、生成所有排列
        boolean[] visited = new boolean[n + 1];
        ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        path.add(-1);
        dfs(visited, path);
        // System.out.println(ans);
        // 2、计算每一个排列的值
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (List<Integer> nums : ans) {
            int res = calculate(nums, n);
            min = Math.min(min, res);
            max = Math.max(max, res);
        }
        return new int[]{min, max};
    }

    static void dfs(boolean[] visited, List<Integer> path) {
        if (path.size() == visited.length) {
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int i = 1; i < visited.length; ++i) {
            if (!visited[i]) {
                path.add(i);
                visited[i] = true;
                dfs(visited, path);
                visited[i] = false;
                path.remove(path.size() - 1);
            }
        }
    }

    /**
     * top to down
     */
    static int calculate(List<Integer> nums, int x) {
        if (x == 1) {
            return nums.get(1);
        }
        return Math.abs(calculate(nums, x-1) - nums.get(x));
    }
    /**
     * down to top
     */
    static int calculate_down2top(List<Integer> nums, int x) {
        // i minus 1
        int f_im1 = nums.get(1), f_i;
        for (int i = 2; i <= x; ++i) {
            f_i = Math.abs(f_im1 - nums.get(i));
            f_im1 = f_i;
        }
        return f_im1;
    }
}
