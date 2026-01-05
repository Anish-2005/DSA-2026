//1975. Maximum Matrix Sum
//You are given an n x n integer matrix. You can do the 
// following operation any number of times:

//Choose any two adjacent elements of matrix and multiply each of them by -1.
//Two elements are considered adjacent if and only if they share a border.

//Your goal is to maximize the summation of the matrix's 
// elements. Return the maximum sum of the matrix's elements 
// using the operation mentioned above.

class Solution {
    public long maxMatrixSum(int[][] matrix) {
        long sum = 0;
        int minAbs = Integer.MAX_VALUE;
        int negCount = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int v = matrix[i][j];
                if (v < 0) negCount++;
                int abs = Math.abs(v);
                minAbs = Math.min(minAbs, abs);
                sum += abs;
            }
        }
        if (negCount % 2 == 0) return sum;
        return sum - 2L * minAbs;
    }
}
