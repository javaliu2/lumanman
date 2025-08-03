package newcoder.exam;

import java.util.*;

public class _pdd0803_2_多多的能源站 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        while (t-- > 0) {
            int n = in.nextInt();
            List<int[]> sites = new ArrayList<>();
            for (int i = 0; i < n; ++i) {
                int x = in.nextInt();
                int y = in.nextInt();
                int r = in.nextInt();
                sites.add(new int[]{x, y, r});
            }
            int res = fun(sites);
            System.out.println(res);
        }
    }
    static int fun(List<int[]> sites) {
        int n = sites.size();
        int ans = -1;
        // calate each site
        for (int i = 0; i < n; ++i) {
            Deque<Integer> queue = new ArrayDeque<>();
            boolean[] visited = new boolean[n];
            visited[i] = true;
            queue.offer(i);
            int cnt = 1;
            while(!queue.isEmpty()) {
                int site_idx = queue.poll();
                int[] site = sites.get(site_idx);
                for (int j = 0; j < n; ++j) {
                    if (visited[j]) {
                        continue;
                    }
                    int[] other_site = sites.get(j);
                    double distance =
                            Math.sqrt(
                                    Math.pow(site[0] - other_site[0], 2) +
                                            Math.pow(site[1] - other_site[1], 2)
                            );
                    // if ((1.0 * site[2] - distance) <= Math.pow(10, -6)) {
                    if (distance <= site[2]) {
                        cnt++;
                        queue.offer(j);
                        visited[j] = true;
                    }
                }
            }
            ans = Math.max(cnt, ans);
        }
        return ans;
    }
}