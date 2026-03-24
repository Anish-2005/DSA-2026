/*2906. Construct Product Matrix
Solved
Medium
Topics
premium lock icon
Companies
Hint
Given a 0-indexed 2D integer matrix grid of size n * m, we 
define a 0-indexed 2D matrix p of size n * m as the product matrix 
of grid if the following condition is met:

Each element p[i][j] is calculated as the product of all elements in 
grid except for the element grid[i][j]. This product is then taken modulo 12345.
Return the product matrix of grid. */

class Solution {
    public int[][] constructProductMatrix(int[][] grid) {
        int n = grid.length, m = grid[0].length; // Get grid dimensions
        int mod = 12345; // Modulo value
        int size = n * m; // Total number of elements

        int[] arr = new int[size]; // Flattened grid
        int idx = 0;
        for (int[] row : grid) { // Flatten the 2D grid into 1D array
            for (int val : row) {
                arr[idx++] = val % mod; // Store value modulo mod
            }
        }

        long[] pre = new long[size]; // Prefix product array
        long[] suf = new long[size]; // Suffix product array

        pre[0] = arr[0]; // First prefix is first element
        for (int i = 1; i < size; i++) {
            pre[i] = (pre[i - 1] * arr[i]) % mod; // Prefix product up to i
        }

        suf[size - 1] = arr[size - 1]; // Last suffix is last element
        for (int i = size - 2; i >= 0; i--) {
            suf[i] = (suf[i + 1] * arr[i]) % mod; // Suffix product from i
        }

        int[][] res = new int[n][m]; // Result matrix
        for (int i = 0; i < size; i++) {
            long left = i > 0 ? pre[i - 1] : 1; // Product of all elements before i
            long right = i < size - 1 ? suf[i + 1] : 1; // Product of all elements after i
            int val = (int)((left * right) % mod); // Product of all except arr[i]
            res[i / m][i % m] = val; // Map back to 2D
        }

        return res; // Return the product matrix
    }
}

/*
Visualization:

Suppose grid = [[2, 3], [4, 5]]
Flattened: [2, 3, 4, 5]
Prefix:    [2, 6, 24, 120]
Suffix:    [120, 60, 20, 5]

For each element:
0: left=1, right=60 → 1*60=60
1: left=2, right=20 → 2*20=40
2: left=6, right=5  → 6*5=30
3: left=24, right=1 → 24*1=24

So, result matrix:
[[60, 40],
 [30, 24]]

This approach efficiently computes the product of all elements except the current one for each cell, using prefix and suffix products, and maps the result back to the 2D grid.
*/