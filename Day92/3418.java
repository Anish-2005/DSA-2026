/*3418. Maximum Amount of Money Robot Can Earn
Solved
Medium
Topics
premium lock icon
Companies
Hint
You are given an m x n grid. A robot starts at the top-left corner of the grid (0, 0) and wants to reach the bottom-right corner (m - 1, n - 1). The robot can move either right or down at any point in time.

The grid contains a value coins[i][j] in each cell:

If coins[i][j] >= 0, the robot gains that many coins.
If coins[i][j] < 0, the robot encounters a robber, and the robber steals the absolute value of coins[i][j] coins.
The robot has a special ability to neutralize robbers in at most 2 cells on its path, preventing them from stealing coins in those cells.

Note: The robot's total coins can be negative.

Return the maximum profit the robot can gain on the route. */

class Solution {

    public int maximumAmount(int[][] coins) {

        int m = coins.length;
        int n = coins[0].length;

        int[][][] dp = new int[m][n][3];

        for(int i=0;i<m;i++)
            for(int j=0;j<n;j++)
                for(int k=0;k<3;k++)
                    dp[i][j][k] = Integer.MIN_VALUE;

        dp[0][0][0] = coins[0][0];

        if(coins[0][0] < 0)
            dp[0][0][1] = 0;

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){

                for(int k=0;k<=2;k++){

                    if(i>0 && dp[i-1][j][k] != Integer.MIN_VALUE)
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i-1][j][k] + coins[i][j]);

                    if(j>0 && dp[i][j-1][k] != Integer.MIN_VALUE)
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i][j-1][k] + coins[i][j]);

                    if(coins[i][j] < 0 && k>0){

                        if(i>0 && dp[i-1][j][k-1] != Integer.MIN_VALUE)
                            dp[i][j][k] = Math.max(dp[i][j][k], dp[i-1][j][k-1]);

                        if(j>0 && dp[i][j-1][k-1] != Integer.MIN_VALUE)
                            dp[i][j][k] = Math.max(dp[i][j][k], dp[i][j-1][k-1]);
                    }
                }
            }
        }

        int ans = Integer.MIN_VALUE;

        for(int k=0;k<=2;k++)
            ans = Math.max(ans, dp[m-1][n-1][k]);

        return ans;
    }
}