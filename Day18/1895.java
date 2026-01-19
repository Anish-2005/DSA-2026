class Solution {
    public int largestMagicSquare(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] row = new int[m + 1][n + 1];
        int[][] col = new int[m + 1][n + 1];
        int[][] diag1 = new int[m + 1][n + 1];
        int[][] diag2 = new int[m + 1][n + 2];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                row[i + 1][j + 1] = row[i + 1][j] + grid[i][j];
                col[i + 1][j + 1] = col[i][j + 1] + grid[i][j];
                diag1[i + 1][j + 1] = diag1[i][j] + grid[i][j];
                diag2[i + 1][j + 1] = diag2[i][j + 2] + grid[i][j];
            }
        }

        int max = 1;
        for (int k = 2; k <= Math.min(m, n); k++) {
            for (int i = 0; i + k <= m; i++) {
                for (int j = 0; j + k <= n; j++) {
                    int target = row[i + 1][j + k] - row[i + 1][j];
                    boolean ok = true;

                    for (int r = 0; r < k && ok; r++) {
                        int rs = row[i + r + 1][j + k] - row[i + r + 1][j];
                        if (rs != target) ok = false;
                    }

                    for (int c = 0; c < k && ok; c++) {
                        int cs = col[i + k][j + c + 1] - col[i][j + c + 1];
                        if (cs != target) ok = false;
                    }

                    int d1 = diag1[i + k][j + k] - diag1[i][j];
                    int d2 = diag2[i + k][j + 1] - diag2[i][j + k + 1];
                    if (d1 != target || d2 != target) ok = false;

                    if (ok) max = k;
                }
            }
        }
        return max;
    }
}