/*3302. Find the Lexicographically Smallest Valid Sequence

You are given two strings word1 and word2.

A string x is called almost equal to y if you can change at most one character in x to make it identical to y.

A sequence of indices seq is called valid if:

The indices are sorted in ascending order.
Concatenating the characters at these indices in word1 in the same order results in a string that is almost equal to word2.
Return an array of size word2.length representing the lexicographically smallest valid sequence of indices. If no such sequence of indices exists, return an empty array.

Note that the answer must represent the lexicographically smallest array, not the corresponding string formed by those indices.

 

Example 1:

Input: word1 = "vbcca", word2 = "abc"

Output: [0,1,2]

Explanation:

The lexicographically smallest valid sequence of indices is [0, 1, 2]:

Change word1[0] to 'a'.
word1[1] is already 'b'.
word1[2] is already 'c'. */

class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        /*
         * dp[i] = maximum number of characters of word2
         * that can be matched exactly using word1[i...n-1].
         */
        int[] dp = new int[n + 1];

        int j = m - 1;

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = dp[i + 1];

            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                dp[i]++;
                j--;
            }
        }

        int[] ans = new int[m];

        int i = 0;
        j = 0;

        while (i < n && j < m) {

            // Best case: exact match
            if (word1.charAt(i) == word2.charAt(j)) {
                ans[j] = i;
                j++;
            }

            // Use our one allowed modification here
            else if (dp[i + 1] >= m - j - 1) {
                ans[j] = i;
                j++;
                i++;

                // Now the one mismatch has been consumed.
                break;
            }

            i++;
        }

        /*
         * If we used the mismatch, or simply found the first
         * characters greedily, complete the remaining sequence
         * using exact matches only.
         */
        while (j < m && i < n) {
            if (word1.charAt(i) == word2.charAt(j)) {
                ans[j] = i;
                j++;
            }
            i++;
        }

        if (j < m) {
            return new int[0];
        }

        return ans;
    }
}