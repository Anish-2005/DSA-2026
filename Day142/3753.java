/* 
3753. Total Waviness of Numbers in Range II

You are given two integers num1 and num2 representing an inclusive range [num1, num2].

The waviness of a number is defined as the total count of its peaks and valleys:

A digit is a peak if it is strictly greater than both of its immediate neighbors.
A digit is a valley if it is strictly less than both of its immediate neighbors.
The first and last digits of a number cannot be peaks or valleys.
Any number with fewer than 3 digits has a waviness of 0.
Return the total sum of waviness for all numbers in the range [num1, num2].
 

Example 1:

Input: num1 = 120, num2 = 130

Output: 3

Explanation:

In the range [120, 130]:

120: middle digit 2 is a peak, waviness = 1.
121: middle digit 2 is a peak, waviness = 1.
130: middle digit 3 is a peak, waviness = 1.
All other numbers in the range have a waviness of 0.
Thus, total waviness is 1 + 1 + 1 = 3.*/ 

class Solution {
    private char[] digits;
    private long[][][][] memoCnt;
    private long[][][][] memoSum;
    private boolean[][][][] seen;

    public long totalWaviness(long num1, long num2) {
        return solve(num2) - solve(num1 - 1);
    }

    private long solve(long n) {
        if (n < 0) return 0;
        digits = Long.toString(n).toCharArray();

        int len = digits.length;
        memoCnt = new long[len + 1][11][11][3];
        memoSum = new long[len + 1][11][11][3];
        seen = new boolean[len + 1][11][11][3];

        return dfs(0, 10, 10, 0, true)[1];
    }

    private long[] dfs(int pos, int prev2, int prev1, int state, boolean tight) {
        if (pos == digits.length) {
            return new long[]{1, 0};
        }

        if (!tight && seen[pos][prev2][prev1][state]) {
            return new long[]{
                memoCnt[pos][prev2][prev1][state],
                memoSum[pos][prev2][prev1][state]
            };
        }

        int limit = tight ? digits[pos] - '0' : 9;

        long totalCnt = 0;
        long totalSum = 0;

        for (int d = 0; d <= limit; d++) {
            boolean ntight = tight && (d == limit);

            int nPrev2 = prev2;
            int nPrev1 = prev1;
            int nState = state;
            int add = 0;

            if (state == 0) {
                if (d != 0) {
                    nState = 1;
                    nPrev1 = d;
                    nPrev2 = 10;
                }
            } else if (state == 1) {
                nState = 2;
                nPrev2 = prev1;
                nPrev1 = d;
            } else {
                if ((prev1 > prev2 && prev1 > d) ||
                    (prev1 < prev2 && prev1 < d)) {
                    add = 1;
                }

                nPrev2 = prev1;
                nPrev1 = d;
                nState = 2;
            }

            long[] next = dfs(pos + 1, nPrev2, nPrev1, nState, ntight);

            totalCnt += next[0];
            totalSum += next[1] + next[0] * add;
        }

        if (!tight) {
            seen[pos][prev2][prev1][state] = true;
            memoCnt[pos][prev2][prev1][state] = totalCnt;
            memoSum[pos][prev2][prev1][state] = totalSum;
        }

        return new long[]{totalCnt, totalSum};
    }
}