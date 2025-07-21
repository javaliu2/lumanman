package algorithm.backtrack;

import org.junit.Test;

public class _79单词搜索 {
    private int m, n;
    private String word;
    private char[][] board;

    public boolean exist(char[][] board, String word) {
        m = board.length;
        n = board[0].length;
        this.word = word;
        this.board = board;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    boolean find = dfs(i, j, visited, 0);
                    if (find) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean dfs(int x, int y, boolean[][] visited, int idx) {
        // 判断board[x][y] ?= word[idx]
        // 1) 相等的话，上下左右四个方向去查找word中剩余的字符
        // 2) 不相等的话，返回false
        if (board[x][y] != word.charAt(idx)) {
            return false;
        }
        if (idx + 1 == word.length()) {
            return true;
        }
        visited[x][y] = true;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] direction : directions) {
            int new_x = x + direction[0], new_y = y + direction[1];
            if (new_x < 0 || new_x >= m || new_y < 0 || new_y >= n || visited[new_x][new_y]) {
                continue;
            }
            boolean find = dfs(new_x, new_y, visited, idx + 1);
            if (find) {
                return true;
            }
        }
        visited[x][y] = false;
        return false;
    }

    @Test
    public void test() {
        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        String word = "ABCCED";
        boolean exist = exist(board, word);
        System.out.println(exist);
    }
}
