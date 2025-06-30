package newcoder.exam;

import java.util.*;

/**
 * 有n个任务，每个任务有开始时间，和处理时间。任务线上可以选择处理时间最短的任务执行，
 * 如果处理时间相等，选择索引小的，就是排在前面的任务进行执行。
 * 用例：4
 * 1 2
 * 2 4
 * 3 2
 * 4 3
 * 有四个任务，执行顺序为 1 3 4 2，首先在时刻1选择任务一，任务1结束时刻为3，
 * 这时有任务2，3到达，其中任务3用时短，选择任务3执行，任务3执行完时刻为5，
 * 这时任务4到达，其用时比任务2小，所以选择任务4进行执行。
 */
public class _6_确定任务的执行顺序 {
    public static List<Integer> getTaskOrder(int[][] tasks) {
        int n = tasks.length;
        List<Integer> result = new ArrayList<>();
        int[][] newTasks = new int[n][3];
        for (int i = 0; i < n; i++) {
            newTasks[i][0] = tasks[i][0];
            newTasks[i][1] = tasks[i][1];
            newTasks[i][2] = i + 1;  // 任务序号从1开始
        }
        // sort是稳定的排序实现
        Arrays.sort(newTasks, Comparator.comparingInt(a -> a[0]));
//        for (int[] arr: newTasks) {
//            System.out.println(Arrays.toString(arr));
//        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (a[1] != b[1]) return a[1] - b[1];  // 按照process时间升序排列
            return a[2] - b[2];  // process时间一样，按照索引升序排列
        });
        long time = 0;
        int i = 0;
        while (i < n || !pq.isEmpty()) {
            // 加入所有到达任务
            while (i < n && newTasks[i][0] <= time) {
                pq.offer(newTasks[i]);
                i++;
            }
            if (!pq.isEmpty()) {
                int[] task = pq.poll();
                time += task[1];  // 执行任务
                result.add(task[2]);
            } else {
                // 没有任务可执行，跳到下一个任务的开始时间
                time = newTasks[i][0];
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        int[][] tasks = {{7, 2}, {7, 4}, {7, 3}};
        int[][] tasks = {{1, 2}, {2, 4}, {3, 2}, {4, 3}};
        List<Integer> order = getTaskOrder(tasks);
        System.out.println(order);
    }
}
