/*3700. Number of ZigZag Arrays II

You are given three integers n, l, and r.

A ZigZag array of length n is defined as follows:

Each element lies in the range [l, r].
No two adjacent elements are equal.
No three consecutive elements form a strictly increasing or strictly decreasing sequence.
Return the total number of valid ZigZag arrays.

Since the answer may be large, return it modulo 109 + 7.

A sequence is said to be strictly increasing if each element is strictly greater than its previous one (if exists).

A sequence is said to be strictly decreasing if each element is strictly smaller than its previous one (if exists).

 

Example 1:

Input: n = 3, l = 4, r = 5

Output: 2

Explanation:

There are only 2 valid ZigZag arrays of length n = 3 using values in the range [4, 5]:

[4, 5, 4]
[5, 4, 5] */


class Solution {
    static final int MOD = 1_000_000_007;

    long[][] multiply(long[][] A, long[][] B) {
        int n = A.length;
        long[][] C = new long[n][n];

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                if (A[i][k] == 0) continue;
                for (int j = 0; j < n; j++) {
                    C[i][j] = (C[i][j] + A[i][k] * B[k][j]) % MOD;
                }
            }
        }

        return C;
    }

    long[][] power(long[][] base, long exp) {
        int n = base.length;
        long[][] res = new long[n][n];

        for (int i = 0; i < n; i++)
            res[i][i] = 1;

        while (exp > 0) {
            if ((exp & 1) == 1)
                res = multiply(res, base);

            base = multiply(base, base);
            exp >>= 1;
        }

        return res;
    }

    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;
        int S = 2 * m;

        long[][] T = new long[S][S];

        // inc[z] <- dec[y] for y < z
        for (int z = 0; z < m; z++) {
            for (int y = 0; y < z; y++) {
                T[z][m + y] = 1;
            }
        }

        // dec[z] <- inc[y] for y > z
        for (int z = 0; z < m; z++) {
            for (int y = z + 1; y < m; y++) {
                T[m + z][y] = 1;
            }
        }

        long[] init = new long[S];

        for (int i = 0; i < m; i++) {
            init[i] = i;
            init[m + i] = m - i - 1;
        }

        long[][] P = power(T, n - 2);

        long[] ans = new long[S];

        for (int i = 0; i < S; i++) {
            for (int j = 0; j < S; j++) {
                ans[i] = (ans[i] + P[i][j] * init[j]) % MOD;
            }
        }

        long res = 0;
        for (long x : ans)
            res = (res + x) % MOD;

        return (int) res;
    }
}