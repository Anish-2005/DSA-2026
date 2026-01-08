//2787. Ways to Express an Integer as Sum of Powers

//Given two positive integers n and x.

//Return the number of ways n can be expressed as the 
//sum of the xth power of unique positive integers, in other 
//words, the number of sets of unique integers [n1, n2, ..., nk] 
//where n = n1x + n2x + ... + nkx.

//Since the result can be very large, return it modulo 109 + 7.

//For example, if n = 160 and x = 3, one way to express n is n = 23 + 33 + 53.

class Solution {
    static final int MOD = 1000000007;

    public int numberOfWays(int n, int x) {
        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (int i = 1; ; i++) {
            long p = 1;
            for (int j = 0; j < x; j++) p *= i;
            if (p > n) break;

            int power = (int) p;
            for (int s = n; s >= power; s--) {
                dp[s] += dp[s - power];
                if (dp[s] >= MOD) dp[s] -= MOD;
            }
        }
        return dp[n];
    }
}
