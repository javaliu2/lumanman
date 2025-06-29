package newcoder.exam;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class _4_查找所有符合要求的字符串中长度最大的 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < 26; ++i) {
            set.add((char) ('a' + i));
            set.add((char) ('A' + i));
        }
        for (int i = 0; i <= 9; ++i) {
            set.add((char) ('0' + i));
        }
        set.add('_');
        // System.out.println(set);
        // brute force
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((arr, arr2) -> {
            int len1 = arr[1] - arr[0], len2 = arr2[1] - arr2[0];
            if (len1 < len2) {
                return 1;  // arr2大于arr1，arr2排在后面
            } else if (len1 > len2) {
                return -1;  // arr小于arr2，arr排在前面
            } else {
                if (arr[0] < arr2[0]) {
                    return -1;  // 长度相同，索引小的排在前面
                } else {
                    return 1;
                }
            }
        });
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (!set.contains((ch))) {
                continue;
            }
            // ch是符合要求的字符，查询从ch为起始的满足要求的最长字符串
            int j = i + 1;
            while (j < s.length() && set.contains(s.charAt(j))) {
                j++;
            }
            // [i， j)是符合要求的子串，将其加入结果集合中
            pq.offer(new int[]{i, j});
            i = j;
        }
        int[] a = pq.peek();
        String res = s.substring(a[0], a[1]);
        System.out.println(res);
    }
}
