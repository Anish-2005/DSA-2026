//1458. Max Dot Product of Two Subsequences
//Given two arrays nums1 and nums2.
//Return the maximum dot product between non-empty subsequences of nums1 and 
// nums2 with the same length.

//A subsequence of a array is a new array which is formed from the original 
// array by deleting some (can be none) of the characters without disturbing 
// the relative positions of the remaining characters. (ie, [2,3,5] is a subsequence of 
// [1,2,3,4,5] while [1,5,3] is not).

class Solution {
    public int maxDotProduct(int[] a, int[] b) {
        int n = a.length, m = b.length;
        int negInf = -1000000000;
        int[] prev = new int[m + 1];
        int[] cur = new int[m + 1];

        for (int j = 0; j <= m; j++) prev[j] = negInf;

        for (int i = 1; i <= n; i++) {
            cur[0] = negInf;
            int ai = a[i - 1];
            for (int j = 1; j <= m; j++) {
                int prod = ai * b[j - 1];
                int take = prod + prev[j - 1];
                if (take < prod) take = prod;

                int skip1 = prev[j];
                int skip2 = cur[j - 1];

                int best = take > skip1 ? take : skip1;
                cur[j] = best > skip2 ? best : skip2;
            }
            int[] t = prev; prev = cur; cur = t;
        }
        return prev[m];
    }
}
