/*3546. Equal Sum Grid Partition I

You are given an m x n matrix grid of positive integers. Your task is to 
determine if it is possible to make either one horizontal or one vertical 
cut on the grid such that:

Each of the two resulting sections formed by the cut is non-empty.
The sum of the elements in both sections is equal.
Return true if such a partition exists; otherwise return false.

 

Example 1:

Input: grid = [[1,4],[2,3]]

Output: true

Explanation:

A horizontal cut between row 0 and row 1 results in two non-empty sections, 
each with a sum of 5. Thus, the answer is true. */

class Solution {
    public boolean canPartitionGrid(int[][] grid) {
        int m = grid.length, n = grid[0].length; // Get grid dimensions
        long total = 0; // Total sum of all elements

        // Calculate total sum of the grid
        for (int[] r : grid)
            for (int v : r)
                total += v;

        long sum = 0; // Running sum for horizontal cut
        // Try all possible horizontal cuts (between rows)
        for (int i = 0; i < m - 1; i++) {
            for (int j = 0; j < n; j++) sum += grid[i][j]; // Add row i to sum
            if (sum * 2 == total) return true; // If both parts equal, return true
        }

        sum = 0; // Running sum for vertical cut
        // Try all possible vertical cuts (between columns)
        for (int j = 0; j < n - 1; j++) {
            for (int i = 0; i < m; i++) sum += grid[i][j]; // Add column j to sum
            if (sum * 2 == total) return true; // If both parts equal, return true
        }

        return false; // No valid partition found
    }
}

/*
Visualization:

Example: grid = [[1,4],[2,3]]

Total sum = 1+4+2+3 = 10

Try horizontal cut after row 0:
  Top part: 1+4 = 5
  Bottom part: 2+3 = 5
  Both parts equal → return true

If not possible horizontally, try vertical cut after each column:
  Left part: 1+2 = 3
  Right part: 4+3 = 7 (not equal)

This approach checks all possible single horizontal and vertical cuts to see if the two resulting sections have equal sums.
*/