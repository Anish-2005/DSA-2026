/* 1406. Stone Game III

Alice and Bob continue their games with piles of stones. There are several stones arranged in a row, and each stone has an associated value which is an integer given in the array stoneValue.

Alice and Bob take turns, with Alice starting first. On each player's turn, that player can take 1, 2, or 3 stones from the first remaining stones in the row.

The score of each player is the sum of the values of the stones taken. The score of each player is 0 initially.

The objective of the game is to end with the highest score, and the winner is the player with the highest score and there could be a tie. The game continues until all the stones have been taken.

Assume Alice and Bob play optimally.

Return "Alice" if Alice will win, "Bob" if Bob will win, or "Tie" if they will end the game with the same score.

 

Example 1:

Input: stoneValue = [1,2,3,7]
Output: "Bob"
Explanation: Alice will always lose. Her best move will be to take three piles and the score become 6. Now the score of Bob is 7 and Bob wins.
*/

class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;

        // dp[i] = maximum score difference
        // current player can achieve starting from index i
        int[] dp = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {

            int sum = 0;
            dp[i] = Integer.MIN_VALUE;

            // Take 1, 2 or 3 stones
            for (int j = i; j < Math.min(i + 3, n); j++) {

                sum += stoneValue[j];

                dp[i] = Math.max(
                    dp[i],
                    sum - dp[j + 1]
                );
            }
        }

        if (dp[0] > 0)
            return "Alice";

        if (dp[0] < 0)
            return "Bob";

        return "Tie";
    }
}