/*3130. Find All Possible Stable Binary Arrays II
Solved
Hard
Topics
premium lock icon
Companies
Hint
You are given 3 positive integers zero, one, and limit.

A binary array arr is called stable if:

The number of occurrences of 0 in arr is exactly zero.
The number of occurrences of 1 in arr is exactly one.
Each subarray of arr with a size greater than limit must contain both 0 and 1.
Return the total number of stable binary arrays.

Since the answer may be very large, return it modulo 109 + 7.

 

Example 1:

Input: zero = 1, one = 1, limit = 2

Output: 2

Explanation:

The two possible stable binary arrays are [1,0] and [0,1].

Example 2:

Input: zero = 1, one = 2, limit = 1

Output: 1

Explanation:

The only possible stable binary array is [1,0,1].

Example 3:

Input: zero = 3, one = 3, limit = 2

Output: 14

Explanation:

All the possible stable binary arrays are [0,0,1,0,1,1], 
[0,0,1,1,0,1], [0,1,0,0,1,1], [0,1,0,1,0,1], [0,1,0,1,1,0],
 [0,1,1,0,0,1], [0,1,1,0,1,0], [1,0,0,1,0,1], [1,0,0,1,1,0], 
 [1,0,1,0,0,1], [1,0,1,0,1,0], [1,0,1,1,0,0], [1,1,0,0,1,0], 
 and [1,1,0,1,0,0].
*/


class Solution {
    int limit; 
    int mod = (int) 1e9 + 7;
    int[][][] dp;

    public int numberOfStableArrays(int zero, int one, int limit) {
        /** 

            .....0
            .....1

            (....)1 = (......)1 = (......0)1 + (......1)1 [#o < limit , #o = limit]
                        - ((...0)1111)1
            
            
            f(z, o, l) = stable 
                = l == 1 
                    f(z, o - 1, 0) + f(z, o - 1, 1) [ending with "limit" 1 or less]
                        - f(z, o - limit - 1, 0)
                = l == 0
                    f(z - 1, o, 1) + f(z - 1, o, 0) (ending with limit 0 or less)
                        - f(z - limit - 1, o, 1)
                
                = 0; z < 0 || o < 0
                = z == 0
                    = o <= limit && l == 1 ? 1 : 0
                = o == 0 
                    = z <= limit && l == 0 ? 1 : 0
         */
        dp = new int[zero + 1][one + 1][2];
        for (int i = 0; i <= zero; i++) {
            for (int j = 0; j <= one; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        this.limit = limit;

        return (f(zero, one, 0) + f(zero, one, 1)) % mod;
    }

    public int f(int z, int o, int last) {
        if (z == 0) 
            return (o <= limit && last == 1) ? 1 : 0;
        if (o == 0) 
            return (z <= limit && last == 0) ? 1 : 0;

        if (dp[z][o][last] != -1) return dp[z][o][last];

        int res = 0; 
        if (last == 0) {
            res = (f(z - 1, o, 0) + f(z - 1, o, 1)) % mod;
            if (z > limit) {
                res = (res - f(z - limit - 1, o, 1) + mod) % mod;
            } 
        } else {
            res = (f(z, o - 1, 0) + f(z, o - 1, 1)) % mod;
            if (o > limit) {
                res = (res - f(z, o - limit - 1, 0) + mod) % mod;
            }
        }

        return dp[z][o][last] = res % mod;
    } 


}