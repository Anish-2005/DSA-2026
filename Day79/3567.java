/*3567. Minimum Absolute Difference in Sliding Submatrix

You are given an m x n integer matrix grid and an integer k.

For every contiguous k x k submatrix of grid, compute the minimum absolute 
difference between any two distinct values within that submatrix.

Return a 2D array ans of size (m - k + 1) x (n - k + 1), where ans[i][j] 
is the minimum absolute difference in the submatrix whose top-left corner 
is (i, j) in grid.

Note: If all elements in the submatrix have the same value, the answer will be 0.

A submatrix (x1, y1, x2, y2) is a matrix that is formed by choosing all 
cells matrix[x][y] where x1 <= x <= x2 and y1 <= y <= y2.
 

Example 1:
Input: grid = [[1,8],[3,-2]], k = 2
Output: [[2]]
Explanation:
There is only one possible k x k submatrix: [[1, 8], [3, -2]].
Distinct values in the submatrix are [1, 8, 3, -2].
The minimum absolute difference in the submatrix is |1 - 3| = 2. Thus, the 
answer is [[2]].

Example 2:
Input: grid = [[3,-1]], k = 1
Output: [[0,0]]
Explanation:
Both k x k submatrix has only one distinct element.
Thus, the answer is [[0, 0]].

Example 3:
Input: grid = [[1,-2,3],[2,3,5]], k = 2
Output: [[1,2]]
Explanation:

There are two possible k × k submatrix:
Starting at (0, 0): [[1, -2], [2, 3]].
Distinct values in the submatrix are [1, -2, 2, 3].
The minimum absolute difference in the submatrix is |1 - 2| = 1.
Starting at (0, 1): [[-2, 3], [3, 5]].
Distinct values in the submatrix are [-2, 3, 5].
The minimum absolute difference in the submatrix is |3 - 5| = 2.
Thus, the answer is [[1, 2]]. */

import java.util.*;

class Solution {
    public int[][] minAbsDiff(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length; // Get matrix dimensions
        int[][] ans = new int[m - k + 1][n - k + 1]; // Output array for answers

        // Slide a k x k window over the grid
        for (int i = 0; i <= m - k; i++) { // For each possible top row
            for (int j = 0; j <= n - k; j++) { // For each possible left column

                TreeSet<Integer> set = new TreeSet<>(); // Store unique values in sorted order

                // Collect all values in the current k x k submatrix
                for (int r = i; r < i + k; r++) {
                    for (int c = j; c < j + k; c++) {
                        set.add(grid[r][c]); // Add value to set
                    }
                }

                if (set.size() <= 1) { // If all values are the same or only one value
                    ans[i][j] = 0; // Min abs diff is 0
                    continue;
                }

                int prev = -1000000000; // Initialize previous value for diff calculation
                int min = Integer.MAX_VALUE; // Track minimum absolute difference

                // Iterate through sorted unique values
                for (int val : set) {
                    if (prev != -1000000000) { // Not the first value
                        min = Math.min(min, val - prev); // Update min diff
                    }
                    prev = val; // Update previous value
                }

                ans[i][j] = min; // Store result for this submatrix
            }
        }

        return ans; // Return the answer matrix
    }
}

/*
Visualization:

Example: grid = [[1,8],[3,-2]], k = 2

Only one 2x2 submatrix: [[1,8],[3,-2]]
Unique values: [1, 3, 8, -2] (sorted: [-2, 1, 3, 8])
Min abs diff: |1-(-2)|=3, |3-1|=2, |8-3|=5 → minimum is 2
So, ans = [[2]]

Example: grid = [[1,-2,3],[2,3,5]], k = 2
Submatrices:
1. Top-left (0,0): [[1,-2],[2,3]] → unique: [-2,1,2,3] (sorted: [-2,1,2,3])
   Diffs: 1-(-2)=3, 2-1=1, 3-2=1 → min=1
2. Top-left (0,1): [[-2,3],[3,5]] → unique: [-2,3,5] (sorted: [-2,3,5])
   Diffs: 3-(-2)=5, 5-3=2 → min=2
So, ans = [[1,2]]

This approach checks every k x k submatrix, collects unique values, sorts them, and finds the smallest difference between consecutive values.
*/