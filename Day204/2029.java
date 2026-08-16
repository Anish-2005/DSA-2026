/*2029. Stone Game IX

Alice and Bob continue their games with stones. There is a row of n stones, and each stone has an associated value. You are given an integer array stones, where stones[i] is the value of the ith stone.

Alice and Bob take turns, with Alice starting first. On each turn, the player may remove any stone from stones. The player who removes a stone loses if the sum of the values of all removed stones is divisible by 3. Bob will win automatically if there are no remaining stones (even if it is Alice's turn).

Assuming both players play optimally, return true if Alice wins and false if Bob wins.

 

Example 1:

Input: stones = [2,1]
Output: true
Explanation: The game will be played as follows:
- Turn 1: Alice can remove either stone.
- Turn 2: Bob removes the remaining stone. 
The sum of the removed stones is 1 + 2 = 3 and is divisible by 3. Therefore, Bob loses and Alice wins the game.*/

class Solution {
    public boolean stoneGameIX(int[] stones) {
        int cnt1 = 0;
        int cnt2 = 0;

        for (int stone : stones) {
            if (stone % 3 == 1) {
                cnt1++;
            } else if (stone % 3 == 2) {
                cnt2++;
            }
        }

        // If only one type of non-zero remainder exists
        if (cnt1 == 0 && cnt2 == 0) {
            return false;
        }

        if (cnt1 == 0) {
            return cnt2 >= 3;
        }

        if (cnt2 == 0) {
            return cnt1 >= 3;
        }

        // Both types exist
        return Math.abs(cnt1 - cnt2) <= 2;
    }
}