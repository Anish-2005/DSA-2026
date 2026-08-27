/*3720. Lexicographically Smallest Permutation Greater Than Target
Solved
Medium
Topics
premium lock icon
Companies
Hint
You are given two strings s and target, both having length n, consisting of lowercase English letters.

Return the lexicographically smallest permutation of s that is strictly greater than target. If no permutation of s is lexicographically strictly greater than target, return an empty string.

A string a is lexicographically strictly greater than a string b (of the same length) if in the first position where a and b differ, string a has a letter that appears later in the alphabet than the corresponding letter in b.

 

Example 1:

Input: s = "abc", target = "bba"

Output: "bca"

Explanation:

The permutations of s (in lexicographical order) are "abc", "acb", "bac", "bca", "cab", and "cba".
The lexicographically smallest permutation that is strictly greater than target is "bca". */

class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] cnt = new int[26];

        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }

        char[] ans = new char[s.length()];

        if (build(ans, 0, target, cnt)) {
            return new String(ans);
        }

        return "";
    }

    private boolean build(char[] ans, int pos, String target, int[] cnt) {
        if (pos == target.length()) {
            return false;
        }

        int t = target.charAt(pos) - 'a';

        if (cnt[t] > 0) {
            ans[pos] = target.charAt(pos);
            cnt[t]--;

            if (build(ans, pos + 1, target, cnt)) {
                cnt[t]++;
                return true;
            }

            cnt[t]++;
        }

        for (int c = t + 1; c < 26; c++) {
            if (cnt[c] > 0) {
                ans[pos] = (char) ('a' + c);
                cnt[c]--;

                int idx = pos + 1;
                for (int x = 0; x < 26; x++) {
                    while (cnt[x] > 0) {
                        ans[idx++] = (char) ('a' + x);
                        cnt[x]--;
                    }
                }

                return true;
            }
        }

        return false;
    }
}