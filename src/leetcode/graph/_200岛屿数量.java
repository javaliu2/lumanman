package leetcode.graph;

public class _200岛屿数量 {
    private int m, n;

    public int numIslands(char[][] grid) {
        m = grid.length;
        n = grid[0].length;
        int cnt = 0;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (!visited[i][j] && grid[i][j] == '1') {
                    // 寻找以[i][j]为起点的岛屿，将visited全置为true
                    find(grid, i, j, visited);
                    cnt += 1;
                }
            }
        }
        return cnt;
    }

    private void find(char[][] grid, int i, int j, boolean[][] visited) {
        visited[i][j] = true;
        if (i - 1 >= 0) {
            if (!visited[i - 1][j] && grid[i - 1][j] == '1') {
                find(grid, i - 1, j, visited);
            }
        }
        if (i + 1 < m) {
            if (!visited[i + 1][j] && grid[i + 1][j] == '1') {
                find(grid, i + 1, j, visited);
            }
        }
        if (j - 1 >= 0) {
            if (!visited[i][j - 1] && grid[i][j - 1] == '1') {
                find(grid, i, j - 1, visited);
            }
        }
        if (j + 1 < n) {
            if (!visited[i][j + 1] && grid[i][j + 1] == '1') {
                find(grid, i, j + 1, visited);
            }
        }
    }
}
