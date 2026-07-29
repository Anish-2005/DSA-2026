/*3518. Smallest Palindromic Rearrangement II
Hard
Topics
premium lock icon
Companies
Hint
You are given a palindromic string s and an integer k.

Return the k-th lexicographically smallest palindromic permutation of s. If there are fewer than k distinct palindromic permutations, return an empty string.

Note: Different rearrangements that yield the same palindromic string are considered identical and are counted once.

 

Example 1:

Input: s = "abba", k = 2

Output: "baab"

Explanation:

The two distinct palindromic rearrangements of "abba" are "abba" and "baab".
Lexicographically, "abba" comes before "baab". Since k = 2, the output is "baab". */

class Solution {

    long LIMIT;

    public String smallestPalindrome(String s, int k) {
        LIMIT = k;

        int[] freq = new int[26];
        for (char ch : s.toCharArray())
            freq[ch - 'a']++;

        int[] half = new int[26];
        int halfLen = 0;
        char mid = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            halfLen += half[i];
            if ((freq[i] & 1) == 1)
                mid = (char) ('a' + i);
        }

        long total = countWays(half, halfLen);
        if (total < k)
            return "";

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {

            for (int c = 0; c < 26; c++) {

                if (half[c] == 0)
                    continue;

                half[c]--;

                long ways = countWays(half, halfLen - pos - 1);

                if (ways >= k) {
                    left.append((char) ('a' + c));
                    break;
                }

                k -= ways;
                half[c]++;
            }
        }

        StringBuilder ans = new StringBuilder();
        ans.append(left);

        if (mid != 0)
            ans.append(mid);

        ans.append(new StringBuilder(left).reverse());

        return ans.toString();
    }

    private long countWays(int[] cnt, int total) {
        long ways = 1;
        int rem = total;

        for (int x : cnt) {
            if (x == 0)
                continue;
            ways = multiplyCap(ways, comb(rem, x));
            if (ways >= LIMIT)
                return LIMIT;
            rem -= x;
        }
        return ways;
    }

    private long comb(int n, int r) {
        if (r > n)
            return 0;
        r = Math.min(r, n - r);

        long res = 1;

        for (int i = 1; i <= r; i++) {
            res = res * (n - r + i) / i;
            if (res >= LIMIT)
                return LIMIT;
        }
        return res;
    }

    private long multiplyCap(long a, long b) {
        if (a == 0 || b == 0)
            return 0;
        if (a >= LIMIT || b >= LIMIT)
            return LIMIT;
        if (a > LIMIT / b)
            return LIMIT;
        long v = a * b;
        return Math.min(v, LIMIT);
    }
}