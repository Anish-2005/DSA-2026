/*3129. Find All Possible Stable Binary Arrays I

You are given 3 positive integers zero, one, and limit.

A binary array arr is called stable if:

The number of occurrences of 0 in arr is exactly zero.
The number of occurrences of 1 in arr is exactly one.
Each subarray of arr with a size greater than limit must contain both 0 and 1.
Return the total number of stable binary arrays.

Since the answer may be very large, return it m*/

class Solution {
    static final int MOD = 1000000007;

    public int numberOfStableArrays(int zero, int one, int limit) {
        int[][][] dp = new int[zero + 1][one + 1][2];

        for (int i = 1; i <= Math.min(limit, zero); i++) dp[i][0][0] = 1;
        for (int j = 1; j <= Math.min(limit, one); j++) dp[0][j][1] = 1;

        for (int i = 0; i <= zero; i++) {
            for (int j = 0; j <= one; j++) {
                if (i > 0) {
                    for (int k = 1; k <= limit && k <= i; k++) {
                        dp[i][j][0] = (dp[i][j][0] + dp[i - k][j][1]) % MOD;
                    }
                }
                if (j > 0) {
                    for (int k = 1; k <= limit && k <= j; k++) {
                        dp[i][j][1] = (dp[i][j][1] + dp[i][j - k][0]) % MOD;
                    }
                }
            }
        }

        return (dp[zero][one][0] + dp[zero][one][1]) % MOD;
    }
}

/* 
Quick ASCII Visualization

Example:

zero = 1
one = 1
limit = 2

Possible arrays:

0 1
1 0

Both valid because:

length ≤ limit
no long identical runs

Result

2

Example:

zero = 1
one = 2
limit = 1

Cannot have consecutive same numbers.

Valid:

1 0 1

Invalid:

1 1 0
0 1 1

Result

1

*/