package leetcode.bfs;


import java.util.*;

/**
 * 针对测试用例：
 * s1: leetcode
 * s2: programs
 * baseStr: sourcecode
 * output:
 * map: {a=[a, o, e, r, s, c], b=[b], d=[d, m], f=[f], g=[g, t], h=[h], i=[i],
 * j=[j], k=[k], l=[l, p], n=[n], q=[q], u=[u], v=[v], w=[w], x=[x], y=[y], z=[z]}
 */
public class _1061按字典序排列最小的等效字符串 {
    class Solution {
        public String smallestEquivalentString(String s1, String s2, String baseStr) {
            // 1、使用图表示连通关系
            int[][] graph = new int[26][26];
            int n = s1.length();
            for (int i = 0; i < n; ++i) {
                char ch = s1.charAt(i);
                char ch2 = s2.charAt(i);
                int idx = ch - 'a', idx2 = ch2 - 'a';
                graph[idx][idx2] = 1;
                graph[idx2][idx] = 1;
            }
            // 2、查找可以连通的字符集
            boolean[] visited = new boolean[26];
            Map<Character, List<Character>> map = new HashMap<>();
            for (int i = 0; i < 26; ++i) {
                if (visited[i]) {
                    continue;
                }
                // 这里是不需要的，否则针对以上测试用例，u=[u]的关系没有，导致结果字符串少一位
                // boolean flag = false;
                // for (int j = 0; j < 26; ++j) {
                //     if (graph[i][j] == 1) {
                //         flag = true;
                //         break;
                //     }
                // }
                // if (!flag) {
                //     continue;
                // }
                List<Character> chars = new ArrayList<>();
                Queue<Integer> q = new ArrayDeque<>();
                visited[i] = true;
                chars.add((char) ('a' + i));
                q.offer(i);
                while (!q.isEmpty()) {
                    int char_row = q.poll();
                    for (int j = 0; j < 26; ++j) {
                        if (!visited[j] && graph[char_row][j] == 1) {
                            visited[j] = true;
                            chars.add((char) ('a' + j));
                            q.offer(j);
                        }
                    }
                }
                map.put(Collections.min(chars), chars);
            }
            System.out.println(map);
            // 3、replace
            StringBuffer sb = new StringBuffer();
            for (char ch : baseStr.toCharArray()) {
                for (Map.Entry<Character, List<Character>> entry : map.entrySet()) {
                    char key = entry.getKey();
                    List<Character> value = entry.getValue();
                    if (value.contains(ch)) {
                        sb.append(key);
                    }
                }
            }
            return sb.toString();
        }
    }
}

/**
 * @author: LeetCode Official
 * template code, using mind to learn.
 */
class UnionFind {
    int[] parent;

    UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    void unite(int x, int y) {
        x = find(x);
        y = find(y);
        if (x == y) return;
        if (x > y) {
            int temp = x;
            x = y;
            y = temp;
        }
        // 总是让字典序更小的作为集合代表字符
        parent[y] = x;
    }
}

class Solution {
    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        UnionFind uf = new UnionFind(26);
        for (int i = 0; i < s1.length(); i++) {
            uf.unite(s1.charAt(i) - 'a', s2.charAt(i) - 'a');
        }

        StringBuilder sb = new StringBuilder();
        for (char c : baseStr.toCharArray()) {
            sb.append((char) ('a' + uf.find(c - 'a')));
        }
        return sb.toString();
    }
}

