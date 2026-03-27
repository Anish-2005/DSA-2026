/*2946. Matrix Similarity After Cyclic Shifts
Easy
Topics
premium lock icon
Companies
Hint
You are given an m x n integer matrix mat and an integer k. The matrix rows 
are 0-indexed.

The following proccess happens k times:

Even-indexed rows (0, 2, 4, ...) are cyclically shifted to the left.


Odd-indexed rows (1, 3, 5, ...) are cyclically shifted to the right.  */

class Solution {
    public boolean areSimilar(int[][] mat, int k) {

        int m = mat.length;
        int n = mat[0].length;

        k %= n;

        for(int i = 0; i < m; i++) {

            for(int j = 0; j < n; j++) {

                int newIndex;

                if(i % 2 == 0)
                    newIndex = (j + k) % n;
                else
                    newIndex = (j - k + n) % n;

                if(mat[i][j] != mat[i][newIndex])
                    return false;
            }
        }

        return true;
    }
}