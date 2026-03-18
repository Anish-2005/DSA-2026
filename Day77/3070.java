/*3070. Count Submatrices with Top-Left Element and Sum Less Than k

You are given a 0-indexed integer matrix grid and an integer k.

Return the number of submatrices that contain the top-left element of 
the grid, and have a sum less than or equal to k.

 

Example 1:


Input: grid = [[7,6,3],[6,6,1]], k = 18
Output: 4
Explanation: There are only 4 submatrices, shown in the image above, that 
contain the top-left element of grid, and have a sum less than or equal to 18.
*/
class Solution {
    public int countSubmatrices(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length; // Get matrix dimensions
        int ans = 0; // Initialize answer

        int[][] pre = new int[m][n]; // Prefix sum matrix

        // Loop through each cell (i, j)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                pre[i][j] = grid[i][j]; // Start with the value at (i, j)

                if (i > 0) pre[i][j] += pre[i-1][j]; // Add sum from above
                if (j > 0) pre[i][j] += pre[i][j-1]; // Add sum from left
                if (i > 0 && j > 0) pre[i][j] -= pre[i-1][j-1]; // Remove double-counted top-left area

                if (pre[i][j] <= k) ans++; // If sum of submatrix (0,0) to (i,j) <= k, count it
            }
        }

        return ans; // Return the total count
    }
}

/*
Visualization:

Given grid = [[7,6,3],
              [6,6,1]], k = 18

We want to count all submatrices starting at (0,0) and ending at (i,j) with sum <= k.

Step 1: Build prefix sum matrix:
pre[i][j] = sum of all elements in rectangle from (0,0) to (i,j)

pre[0][0] = 7
pre[0][1] = 7+6 = 13
pre[0][2] = 7+6+3 = 16
pre[1][0] = 7+6 = 13
pre[1][1] = 7+6+6+6 = 25
pre[1][2] = 7+6+3+6+6+1 = 29

But with prefix sum logic:
pre[0][0] = 7
pre[0][1] = 7+6 = 13
pre[0][2] = 13+3 = 16
pre[1][0] = 7+6 = 13
pre[1][1] = 13+6+6 = 25 (but subtract pre[0][0]=7, so 13+6+6-7=18)
pre[1][2] = 16+1+13-13=17+1=18 (but let's check step by step)

Let's fill it step by step:
pre[0][0] = 7
pre[0][1] = 7+6 = 13
pre[0][2] = 13+3 = 16
pre[1][0] = 7+6 = 13
pre[1][1] = 6+pre[1][0]=13+6=19, then add pre[0][1]=13, so 19+13=32, subtract pre[0][0]=7, so 32-7=25
pre[1][2] = 1+pre[1][1]=25+1=26, add pre[0][2]=16, so 26+16=42, subtract pre[0][1]=13, so 42-13=29

But the correct prefix sum for each cell is:
pre[0][0] = 7
pre[0][1] = 13
pre[0][2] = 16
pre[1][0] = 13
pre[1][1] = 25
pre[1][2] = 29

Now, for each cell, if pre[i][j] <= k, count it:
pre[0][0]=7 (yes), pre[0][1]=13 (yes), pre[0][2]=16 (yes), pre[1][0]=13 (yes), pre[1][1]=25 (no), pre[1][2]=29 (no)

So, answer is 4.

This matches the sample output.
*/