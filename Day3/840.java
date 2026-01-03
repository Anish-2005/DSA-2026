//840. Magic Squares In Grid
//A 3 x 3 magic square is a 3 x 3 grid filled 
// with distinct numbers from 1 to 9 such that each row, column, 
// and both diagonals all have the same sum.

//Given a row x col grid of integers, how many 3 x 3 magic square subgrids are there?

//Note: while a magic square can only contain numbers from 1 to 9, 
// grid may contain numbers up to 15.


class Solution {
    public int numMagicSquaresInside(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;
        for (int i = 0; i <= rows - 3; i++) {
            for (int j = 0; j <= cols - 3; j++) {
                if (isMagic(grid, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }
    private boolean isMagic(int[][] g, int r, int c) {
        if (g[r + 1][c + 1] != 5) return false;

        boolean[] seen = new boolean[10];
        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                int val = g[i][j];
                if (val < 1 || val > 9 || seen[val]) return false;
                seen[val] = true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (g[r + i][c] + g[r + i][c + 1] + g[r + i][c + 2] != 15)
                return false;
        }
        for (int j = 0; j < 3; j++) {
            if (g[r][c + j] + g[r + 1][c + j] + g[r + 2][c + j] != 15)
                return false;
        }
        if (g[r][c] + g[r + 1][c + 1] + g[r + 2][c + 2] != 15)
            return false;
        if (g[r][c + 2] + g[r + 1][c + 1] + g[r + 2][c] != 15)
            return false;
        return true;
    }
}
