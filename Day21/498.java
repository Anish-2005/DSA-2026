//498. Diagonal Traverse


//Given an m x n matrix mat, return an array of all the elements of the 
// array in a diagonal order.

class Solution {
    public int[] findDiagonalOrder(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[] res = new int[m * n];

        int i = 0, j = 0, d = 1, k = 0;

        while (k < res.length) {
            res[k++] = mat[i][j];

            if (d == 1) {
                if (j == n - 1) { i++; d = -1; }
                else if (i == 0) { j++; d = -1; }
                else { i--; j++; }
            } else {
                if (i == m - 1) { j++; d = 1; }
                else if (j == 0) { i++; d = 1; }
                else { i++; j--; }
            }
        }
        return res;
    }
}
