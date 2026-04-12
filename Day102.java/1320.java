/*1320. Minimum Distance to Type a Word Using Two Fingers

You have a keyboard layout as shown above in the X-Y plane, where each English uppercase letter is located at some coordinate.

For example, the letter 'A' is located at coordinate (0, 0), the letter 'B' is located at coordinate (0, 1), the letter 'P' is located at coordinate (2, 3) and the letter 'Z' is located at coordinate (4, 1).
Given the string word, return the minimum total distance to type such string using only two fingers.

The distance between coordinates (x1, y1) and (x2, y2) is |x1 - x2| + |y1 - y2|.

Note that the initial positions of your two fingers are considered free so do not count towards your total distance, also your two fingers do not have to start at the first letter or the first two letters.

 

Example 1:

Input: word = "CAKE"
Output: 3
Explanation: Using two fingers, one optimal way to type "CAKE" is: 
Finger 1 on letter 'C' -> cost = 0 
Finger 1 on letter 'A' -> cost = Distance from letter 'C' to letter 'A' = 2 
Finger 2 on letter 'K' -> cost = 0 
Finger 2 on letter 'E' -> cost = Distance from letter 'K' to letter 'E' = 1  
Total distance = 3 */

class Solution {
    public int minimumDistance(String word) {
        int n = word.length();
        int[][] dp = new int[n][26];
        
        for(int i=0;i<n;i++)
            Arrays.fill(dp[i], Integer.MAX_VALUE);

        for(int j=0;j<26;j++)
            dp[0][j] = 0;

        for(int i=1;i<n;i++){
            int prev = word.charAt(i-1) - 'A';
            int cur = word.charAt(i) - 'A';

            for(int j=0;j<26;j++){
                if(dp[i-1][j] == Integer.MAX_VALUE) continue;

                dp[i][j] = Math.min(dp[i][j],
                        dp[i-1][j] + dist(prev, cur));

                dp[i][prev] = Math.min(dp[i][prev],
                        dp[i-1][j] + dist(j, cur));
            }
        }

        int ans = Integer.MAX_VALUE;
        for(int j=0;j<26;j++)
            ans = Math.min(ans, dp[n-1][j]);

        return ans;
    }

    int dist(int a, int b){
        int x1 = a / 6, y1 = a % 6;
        int x2 = b / 6, y2 = b % 6;
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }
}