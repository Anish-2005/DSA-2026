/*1727. Largest Submatrix With Rearrangements

You are given a binary matrix matrix of size m x n, and you are allowed 
to rearrange the columns of the matrix in any order.

Return the area of the largest submatrix within matrix where every element 
of the submatrix is 1 after reordering the columns optimally.

Example 1:

Input: matrix = [[0,0,1],[1,1,1],[1,0,1]]
Output: 4
Explanation: You can rearrange the columns as shown above.
The largest submatrix of 1s, in bold, has an area of 4. */

import java.util.*;

class Solution {
    public int largestSubmatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length; // Get matrix dimensions
        int[][] height = new int[m][n]; // height[i][j]: number of consecutive 1s above (i,j) including itself

        // Build the height matrix
        for (int j = 0; j < n; j++) { // For each column
            height[0][j] = matrix[0][j]; // First row: height is just the value itself
            for (int i = 1; i < m; i++) { // For each row from second down
                height[i][j] = matrix[i][j] == 0 ? 0 : height[i-1][j] + 1; // If 1, add to height; else reset to 0
            }
        }

        int ans = 0; // Store the maximum area found

        // For each row, treat it as the base of a submatrix
        for (int i = 0; i < m; i++) {
            int[] row = height[i].clone(); // Copy the current row's heights
            Arrays.sort(row); // Sort to simulate optimal column rearrangement (tallest to shortest)

            for (int j = 0; j < n; j++) { // Try all possible widths
                int h = row[j]; // Height for this width
                int width = n - j; // Width is number of columns from j to end
                ans = Math.max(ans, h * width); // Update max area if larger found
            }
        }

        return ans; // Return the largest area found
    }
}

/*
Visualization:

Example: matrix = [[0,0,1],
                   [1,1,1],
                   [1,0,1]]

Step 1: Build height matrix (number of consecutive 1s above each cell):
Row 0: [0, 0, 1]
Row 1: [1, 1, 2]
Row 2: [2, 0, 3]

Step 2: For each row, sort the heights (simulate column rearrangement):
Row 0 sorted: [0, 0, 1]
Row 1 sorted: [1, 1, 2]
Row 2 sorted: [0, 2, 3]

Step 3: For each sorted row, try all possible widths:
Row 0: max area = 1*1 = 1
Row 1: max area = 1*3 = 3, 1*2 = 2, 2*1 = 2 (max is 3)
Row 2: max area = 0*3 = 0, 2*2 = 4, 3*1 = 3 (max is 4)

Final answer: 4

This approach finds the largest rectangle of 1s possible after optimally rearranging columns for each row.
*/