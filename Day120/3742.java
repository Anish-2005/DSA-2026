/*3742. Maximum Path Score in a Grid

You are given an m x n grid where each cell contains one of the values 0, 1, or 2. You are also given an integer k.

You start from the top-left corner (0, 0) and want to reach the bottom-right corner (m - 1, n - 1) by moving only right or down.

Each cell contributes a specific score and incurs an associated cost, according to their cell values:

0: adds 0 to your score and costs 0.
1: adds 1 to your score and costs 1.
2: adds 2 to your score and costs 1. ​​​​​​​
Return the maximum score achievable without exceeding a total cost of k, or -1 if no valid path exists.

Note: If you reach the last cell but the total cost exceeds k, the path is invalid.
*/

class Solution {
    public int maxPathScore(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        
        int[][][] dp = new int[m][n][k + 1];
        
        // Initialize
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int c = 0; c <= k; c++) {
                    dp[i][j][c] = -1;
                }
            }
        }
        
        int startCost = (grid[0][0] == 0) ? 0 : 1;
        int startScore = grid[0][0];
        
        if (startCost <= k) {
            dp[0][0][startCost] = startScore;
        }
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                
                int cellCost = (grid[i][j] == 0) ? 0 : 1;
                int cellScore = grid[i][j];
                
                for (int c = 0; c <= k; c++) {
                    
                    if (dp[i][j][c] == -1) continue;
                    
                    // Move right
                    if (j + 1 < n) {
                        int newCost = c + ((grid[i][j + 1] == 0) ? 0 : 1);
                        if (newCost <= k) {
                            dp[i][j + 1][newCost] = Math.max(
                                dp[i][j + 1][newCost],
                                dp[i][j][c] + grid[i][j + 1]
                            );
                        }
                    }
                    
                    // Move down
                    if (i + 1 < m) {
                        int newCost = c + ((grid[i + 1][j] == 0) ? 0 : 1);
                        if (newCost <= k) {
                            dp[i + 1][j][newCost] = Math.max(
                                dp[i + 1][j][newCost],
                                dp[i][j][c] + grid[i + 1][j]
                            );
                        }
                    }
                }
            }
        }
        
        int ans = -1;
        for (int c = 0; c <= k; c++) {
            ans = Math.max(ans, dp[m - 1][n - 1][c]);
        }
        
        return ans;
    }
}