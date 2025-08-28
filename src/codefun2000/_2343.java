package codefun2000;

import java.util.Scanner;

public class _2343 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), t = sc.nextInt();
        int[] tasks = new int[n];
        for (int i = 0; i < n; ++i) {
            tasks[i] = sc.nextInt();
        }
        int res = fun(t, tasks);
        System.out.println(res);
        sc.close();
    }

    /**
     * bug record:
     * 1）当前任务能否被消费（计算）
     * 2）l=mid+1, r=mid-1; 而不能是l=mid，r=mid，后者会造成死循环
     * 3）
     */
    static int fun(int t, int[] tasks) {
        // 在t时刻内完成tasks中所有任务的计算，任务的调度是顺序的，从0至n-1
        // 二分找答案
        int sum = 0;
        for (int i = 0; i < tasks.length; ++i) {
            sum += tasks[i];
        }
        int l = 1, r = sum;
        int ans = 0;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            // 计算算力为mid时。花费的时间
            // 贪心：尽可能最多地消费任务
            int time = 0, i = 0;
            boolean fail = false;
            while (i < tasks.length) {
                int add_task_sum = 0;
                if (tasks[i] > mid) {  // 当前任务不能被消费（计算）
                    fail = true;
                    break;
                }
                while (i < tasks.length && add_task_sum + tasks[i] <= mid) {
                    add_task_sum += tasks[i];
                    i++;
                }
                time++;
                // i是下一批次要消费任务的起点
            }
            if (fail) {
                l = mid + 1;  // 算力变大
            } else {
                if (time <= t) {
                    ans = mid;
                    r = mid - 1;  // 算力变小
                } else {
                    l = mid + 1;  // 算力变大
                }
            }
        }
        return ans;
    }

    /**
     * G哥的答案
     */
    static int fun_gbro(int t, int[] tasks) {
        int sum = 0;
        for (int x : tasks) sum += x;

        int l = 1, r = sum;
        int ans = -1;

        while (l <= r) {
            int mid = l + ((r - l) >> 1);

            // 检查需要多少时间
            int time = 0, cur = 0;
            boolean fail = false;
            for (int x : tasks) {
                if (x > mid) {  // 单个任务都放不下
                    fail = true;
                    break;
                }
                if (cur + x > mid) {
                    time++;
                    cur = 0;
                }
                cur += x;
            }
            if (!fail) time++;  // 统计最后一批

            if (fail || time > t) {
                l = mid + 1;   // 算力不足，需要变大
            } else {
                ans = mid;     // 记录答案
                r = mid - 1;   // 尝试更小的算力
            }
        }
        return ans;
    }
}
