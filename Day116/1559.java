/*
1559. Detect Cycles in 2D Grid
Solved
Medium
Topics
premium lock icon
Companies
Hint
Given a 2D array of characters grid of size m x n, you need to find if there exists any cycle consisting of the same value in grid.

A cycle is a path of length 4 or more in the grid that starts and ends at the same cell. From a given cell, you can move to one of the cells adjacent to it - in one of the four directions (up, down, left, or right), if it has the same value of the current cell.

Also, you cannot move to the cell that you visited in your last move. For example, the cycle (1, 1) -> (1, 2) -> (1, 1) is invalid because from (1, 2) we visited (1, 1) which was the last visited cell.

Return true if any cycle of the same value exists in grid, otherwise, return false. */


class Solution {
    int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    
    public boolean containsCycle(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] vis = new boolean[m][n];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!vis[i][j]) {
                    if (dfs(grid, vis, i, j, -1, -1)) return true;
                }
            }
        }
        return false;
    }
    
    private boolean dfs(char[][] grid, boolean[][] vis, int x, int y, int px, int py) {
        vis[x][y] = true;
        
        for (int[] d : dirs) {
            int nx = x + d[0];
            int ny = y + d[1];
            
            if (nx < 0 || ny < 0 || nx >= grid.length || ny >= grid[0].length)
                continue;
            
            if (grid[nx][ny] != grid[x][y]) continue;
            
            if (!vis[nx][ny]) {
                if (dfs(grid, vis, nx, ny, x, y)) return true;
            } else if (nx != px || ny != py) {
                return true;
            }
        }
        
        return false;
    }
}