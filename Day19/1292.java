//1292. Maximum Side Length of a Square with Sum Less than or Equal to Threshold

//Given a m x n matrix mat and an integer threshold, return the maximum side-length
//  of a square with a sum less than or equal to threshold or return 0 if there is no such square.

class Solution {
    public int maxSideLength(int[][] mat, int threshold) {
        int m = mat.length;
        int n = mat[0].length;

        int[][] prefix = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefix[i][j] = mat[i - 1][j - 1]
                             + prefix[i - 1][j]
                             + prefix[i][j - 1]
                             - prefix[i - 1][j - 1];
            }
        }

        int left = 0, right = Math.min(m, n);

        while (left < right) {
            int mid = (left + right + 1) / 2;
            if (exists(prefix, mid, threshold)) left = mid;
            else right = mid - 1;
        }

        return left;
    }

    private boolean exists(int[][] prefix, int k, int threshold) {
        for (int i = k; i < prefix.length; i++) {
            for (int j = k; j < prefix[0].length; j++) {
                int sum = prefix[i][j]
                        - prefix[i - k][j]
                        - prefix[i][j - k]
                        + prefix[i - k][j - k];
                if (sum <= threshold) return true;
            }
        }
        return false;
    }
}