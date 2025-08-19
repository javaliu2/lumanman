package newcoder.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class M8D19_3选择最少月份存钱 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int target = in.nextInt();
        int[] money = new int[n];
        for (int i = 0; i < n; ++i) {
            money[i] = in.nextInt();
        }
        List<Integer> path = new ArrayList<>();
        int res = dfs(money, target, 0, path);
        int res2 = dfs2(money, target, 0, 0);
        System.out.println(res);
        System.out.println(res2);
    }

    private static int dfs2(int[] money, int target, int month, int count) {
        if (month >= money.length) {
            if (target <= 0) {
                return count;
            }
            return -1;
        }
        if (target <= 0) {
            return count;
        }
        // 选择当前月份存储，到达目标所需最少月份
        int res_yes = dfs2(money, target - money[month], month + 2, count + 1);
        // 不选择当前月份存储
        int res_no = dfs2(money, target, month + 1, count);
        if (res_yes == -1) {
            return res_no;
        }
        if (res_no == -1) {
            return res_yes;
        }
        return Math.min(res_yes, res_no);
    }

    static int dfs(int[] money, int target, int month, List<Integer> path) {
        if (month >= money.length) {
            if (target <= 0) {
                return path.size();
            }
            return -1;
        }
        if (target <= 0) {
            return path.size();
        }
        // 选择当前月份存储，到达目标所需最少月份
        path.add(month);
        int res_yes = dfs(money, target - money[month], month + 2, path);
        path.remove(path.size() - 1);
        // 不选择当前月份存储
        int res_no = dfs(money, target, month + 1, path);
        if (res_yes == -1) {
            return res_no;
        }
        if (res_no == -1) {
            return res_yes;
        }
        return Math.min(res_yes, res_no);
    }
}
