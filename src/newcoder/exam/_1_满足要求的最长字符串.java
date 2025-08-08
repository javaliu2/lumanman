package newcoder.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class _1_满足要求的最长字符串 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> l = new ArrayList<>();
        int n = sc.nextInt();
        sc.nextLine();
        while (n-- > 0) {
            l.add(sc.nextLine());
        }
        l.sort((s1, s2) -> {
            if (s1.length() == s2.length()) {
                return s2.compareTo(s1);
            }
            return s2.length() - s1.length();
        });
        // System.out.println(l);
        // 枚举每一个字符串，查看其是否符合要求
        int i;
        for (i = 0; i < l.size(); ++i) {
            String s = l.get(i);
            if (check(l, s)) {
                System.out.println(s);
                break;
            }
        }
        if (i >= l.size()) {
            System.out.println("");
        }
    }

    static boolean check(List<String> l, String s) {
        // 枚举s的前缀字符串，检查其是否存在于l
        for (int i = 1; i < s.length() - 1; ++i) {
            String ss = s.substring(0, i);
            if (!l.contains(ss)) {
                return false;
            }
        }
        return true;
    }
}
