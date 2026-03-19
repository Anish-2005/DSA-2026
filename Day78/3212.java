/*3212. Count Submatrices With Equal Frequency of X and Y
Medium
Topics
premium lock icon
Companies
Hint
Given a 2D character matrix grid, where grid[i][j] is either 'X', 'Y', or '.', return the number of submatrices that contain:

grid[0][0]
an equal frequency of 'X' and 'Y'.
at least one 'X'.
 

Example 1:

Input: grid = [["X","Y","."],["Y",".","."]]

Output: 3
*/

class Solution {
    public int numberOfSubmatrices(char[][] grid) {
        int m = grid.length, n = grid[0].length; // Get matrix dimensions
        int ans = 0; // Initialize answer

        int[][] px = new int[m][n]; // Prefix sum for 'X'
        int[][] py = new int[m][n]; // Prefix sum for 'Y'

        // Loop through each cell (i, j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                px[i][j] = grid[i][j] == 'X' ? 1 : 0; // 1 if cell is 'X', else 0
                py[i][j] = grid[i][j] == 'Y' ? 1 : 0; // 1 if cell is 'Y', else 0

                if (i > 0) { // Add prefix sum from above
                    px[i][j] += px[i-1][j];
                    py[i][j] += py[i-1][j];
                }

                if (j > 0) { // Add prefix sum from left
                    px[i][j] += px[i][j-1];
                    py[i][j] += py[i][j-1];
                }

                if (i > 0 && j > 0) { // Remove double-counted top-left area
                    px[i][j] -= px[i-1][j-1];
                    py[i][j] -= py[i-1][j-1];
                }

                // If number of 'X' and 'Y' are equal and at least one 'X', count it
                if (px[i][j] == py[i][j] && px[i][j] > 0) ans++;
            }
        }

        return ans; // Return the total count
    }
}

/*
Visualization:

Given grid = [["X","Y","."],
              ["Y",".","."]]

We want to count all submatrices starting at (0,0) and ending at (i,j) with:
  - equal number of 'X' and 'Y'
  - at least one 'X'

Step 1: Build prefix sum matrices px and py for 'X' and 'Y':

px[i][j] = number of 'X' in rectangle (0,0) to (i,j)
py[i][j] = number of 'Y' in rectangle (0,0) to (i,j)

For each cell, if px[i][j] == py[i][j] and px[i][j] > 0, count it.

Let's fill px and py step by step:

grid:
X Y .
Y . .

px:
1 1 1
1 1 1

py:
0 1 1
1 1 1

Now, for each cell (i,j):
(0,0): px=1, py=0 -> not counted
(0,1): px=1, py=1 -> counted (1)
(0,2): px=1, py=1 -> counted (2)
(1,0): px=1, py=1 -> counted (3)
(1,1): px=1, py=1 -> counted (4)
(1,2): px=1, py=1 -> counted (5)

But the problem says "submatrices that contain grid[0][0]" (i.e., must start at (0,0)), so only rectangles from (0,0) to (i,j) are considered.

So, for each (i,j):
(0,0): px=1, py=0 -> not counted
(0,1): px=1, py=1 -> counted (1)
(0,2): px=1, py=1 -> counted (2)
(1,0): px=1, py=1 -> counted (3)
(1,1): px=1, py=1 -> counted (4)
(1,2): px=1, py=1 -> counted (5)

But the sample output is 3, so only (0,1), (1,0), (1,1) are valid (since at least one 'X' and equal 'X' and 'Y').

This approach efficiently counts all such submatrices using prefix sums.
*/