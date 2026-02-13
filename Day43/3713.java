//3713. Longest Balanced Substring I

//You are given a string s consisting of lowercase English letters.
//A substring of s is called balanced if all distinct characters in the substring appear the same number of times.
//Return the length of the longest balanced substring of s.

class Solution {
    public int longestBalanced(String s) {
        int n = s.length();
        int ans = 0;

        for (int i = 0; i < n; i++) {
            int[] freq = new int[26];

            for (int j = i; j < n; j++) {
                freq[s.charAt(j) - 'a']++;

                int minFreq = Integer.MAX_VALUE;
                int maxFreq = 0;

                for (int k = 0; k < 26; k++) {
                    if (freq[k] > 0) {
                        minFreq = Math.min(minFreq, freq[k]);
                        maxFreq = Math.max(maxFreq, freq[k]);
                    }
                }

                if (minFreq == maxFreq) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        return ans;
    }
}