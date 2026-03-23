/*1594. Maximum Non Negative Product in a Matrix

You are given a m x n matrix grid. Initially, you are located at the 
top-left corner (0, 0), and in each step, you can only move right or down in the matrix.

Among all possible paths starting from the top-left corner (0, 0) and 
ending in the bottom-right corner (m - 1, n - 1), find the path with the 
maximum non-negative product. The product of a path is the product of all 
integers in the grid cells visited along the path.

Return the maximum non-negative product modulo 109 + 7. If the maximum 
product is negative, return -1.

Notice that the modulo is performed after getting the maximum product. */

class Solution {
    public int maxProductPath(int[][] grid) {
        int m = grid.length, n = grid[0].length; // Get matrix dimensions
        long[][] max = new long[m][n]; // max[i][j]: max product to (i,j)
        long[][] min = new long[m][n]; // min[i][j]: min product to (i,j)
        max[0][0] = grid[0][0]; // Start at top-left
        min[0][0] = grid[0][0];

        // Fill first column (can only come from above)
        for (int i = 1; i < m; i++) {
            max[i][0] = max[i-1][0] * grid[i][0]; // Multiply down the column
            min[i][0] = max[i][0]; // Same for min (since only one path)
        }

        // Fill first row (can only come from left)
        for (int j = 1; j < n; j++) {
            max[0][j] = max[0][j-1] * grid[0][j]; // Multiply across the row
            min[0][j] = max[0][j]; // Same for min (since only one path)
        }

        // Fill the rest of the grid
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                long a = max[i-1][j] * grid[i][j]; // From above, using max
                long b = min[i-1][j] * grid[i][j]; // From above, using min
                long c = max[i][j-1] * grid[i][j]; // From left, using max
                long d = min[i][j-1] * grid[i][j]; // From left, using min
                max[i][j] = Math.max(Math.max(a, b), Math.max(c, d)); // Best possible product to (i,j)
                min[i][j] = Math.min(Math.min(a, b), Math.min(c, d)); // Worst possible product to (i,j)
            }
        }

        long res = max[m-1][n-1]; // Result is the max product at bottom-right
        if (res < 0) return -1; // If negative, return -1
        return (int)(res % 1000000007); // Otherwise, return modulo 1e9+7
    }
}

/*
Visualization:

Suppose grid = [[1, -2, 1], [1, -2, 1], [3, -4, 1]]

We want the max non-negative product from (0,0) to (2,2), moving only right or down.

At each cell, we track both the max and min product to that cell, because multiplying by a negative can flip min to max and vice versa.

For each cell, we consider both coming from above and from the left, and both the max and min so far.

For example, at (1,1):
  - max from above: max[0][1] * grid[1][1]
  - min from above: min[0][1] * grid[1][1]
  - max from left:  max[1][0] * grid[1][1]
  - min from left:  min[1][0] * grid[1][1]
Then, max[1][1] = max of all four, min[1][1] = min of all four.

At the end, if max at bottom-right is negative, return -1; else return max % 1e9+7.

This approach ensures all possible sign flips are considered due to negative numbers.
*/