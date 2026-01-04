//808. Soup Servings
//You have two soups, A and B, each starting with n mL. 
//On every turn, one of the following four serving operations 
//is chosen at random, each with probability 0.25 independent of 
//all previous turns:

//pour 100 mL from type A and 0 mL from type B
//pour 75 mL from type A and 25 mL from type B
//pour 50 mL from type A and 50 mL from type B
//pour 25 mL from type A and 75 mL from type B
//Note:

//There is no operation that pours 0 mL from A and 100 mL from B.
//The amounts from A and B are poured simultaneously during the turn.
//If an operation asks you to pour more than you have left of a soup, pour all that remains of that soup.
//The process stops immediately after any turn in which one of the soups is used up.

//Return the probability that A is used up before B, plus half the
// probability that both soups are used up in the same turn. Answers 
// within 10-5 of the actual answer will be accepted.

class Solution {
    private Double[][] dp;
    public double soupServings(int n) {
        if (n >= 4800) return 1.0;
        int m = (n + 24) / 25;
        dp = new Double[m + 1][m + 1];
        return solve(m, m);
    }
    private double solve(int a, int b) {
        if (a <= 0 && b <= 0) return 0.5;
        if (a <= 0) return 1.0;
        if (b <= 0) return 0.0;
        if (dp[a][b] != null) return dp[a][b];
        dp[a][b] = 0.25 * (
                solve(a - 4, b) +
                solve(a - 3, b - 1) +
                solve(a - 2, b - 2) +
                solve(a - 1, b - 3)
        );
        return dp[a][b];
    }
}
