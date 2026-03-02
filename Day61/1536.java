//1536. Minimum Swaps to Arrange a Binary Grid

//Given an n x n binary grid, in one step you can choose two adjacent rows of the grid and swap them.

//A grid is said to be valid if all the cells above the main diagonal are zeros.

//Return the minimum number of steps needed to make the grid valid, or -1 if the grid cannot be valid.

//The main diagonal of a grid is the diagonal that starts at cell (1, 1) and ends at cell (n, n).

 class Solution {
    public int minSwaps(int[][] grid) { // function to find min swaps
        int n = grid.length; // get grid size
        int[] zeros = new int[n]; // array to store trailing zeros for each row

        for (int i = 0; i < n; i++) { // loop through each row
            int count = 0; // count trailing zeros
            for (int j = n - 1; j >= 0; j--) { // loop from end of row
                if (grid[i][j] == 0) count++; // if cell is 0, increase count
                else break; // stop if cell is 1
            }
            zeros[i] = count; // store count for this row
        }

        int swaps = 0; // count swaps
        for (int i = 0; i < n; i++) { // loop through each row
            int needed = n - i - 1; // zeros needed for this row
            int j = i; // start from current row
            while (j < n && zeros[j] < needed) j++; // find row with enough zeros
            if (j == n) return -1; // if not found, return -1

            while (j > i) { // swap rows up to position i
                int temp = zeros[j]; // swap zeros count
                zeros[j] = zeros[j - 1];
                zeros[j - 1] = temp;
                swaps++; // increase swap count
                j--; // move up
            }
        }
        return swaps; // return total swaps
    }
}