/*
3614. Process String with Special Operations II

You are given a string s consisting of lowercase English letters and the special characters: '*', '#', and '%'.

You are also given an integer k.

Build a new string result by processing s according to the following rules from left to right:

If the letter is a lowercase English letter append it to result.
A '*' removes the last character from result, if it exists.
A '#' duplicates the current result and appends it to itself.
A '%' reverses the current result.
Return the kth character of the final string result. If k is out of the bounds of result, return '.'.

 

Example 1:

Input: s = "a#b%*", k = 1

Output: "a"

Explanation:

i	s[i]	Operation	Current result
0	'a'	Append 'a'	"a"
1	'#'	Duplicate result	"aa"
2	'b'	Append 'b'	"aab"
3	'%'	Reverse result	"baa"
4	'*'	Remove the last character	"ba"
The final result is "ba". The character at index k = 1 is 'a'.

Example 2:

Input: s = "cd%#*#", k = 3

Output: "d"

Explanation:

i	s[i]	Operation	Current result
0	'c'	Append 'c'	"c"
1	'd'	Append 'd'	"cd"
2	'%'	Reverse result	"dc"
3	'#'	Duplicate result	"dcdc"
4	'*'	Remove the last character	"dcd"
5	'#'	Duplicate result	"dcddcd"
The final result is "dcddcd". The character at index k = 3 is 'd'.

Example 3:

Input: s = "z*#", k = 0

Output: "."

Explanation:

i	s[i]	Operation	Current result
0	'z'	Append 'z'	"z"
1	'*'	Remove the last character	""
2	'#'	Duplicate the string	""
The final result is "". Since index k = 0 is out of bounds, the output is '.'.

 

Constraints:

1 <= s.length <= 105
s consists of only lowercase English letters and special characters '*', '#', and '%'.
0 <= k <= 1015
The length of result after processing s will not exceed 1015. */


class Solution {
    public char processStr(String s, long k) {
        int n = s.length();
        long[] len = new long[n];

        long cur = 0;

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);

            if (ch >= 'a' && ch <= 'z') {
                cur++;
            } else if (ch == '*') {
                if (cur > 0) cur--;
            } else if (ch == '#') {
                cur *= 2;
            } // '%' does not change length

            len[i] = cur;
        }

        if (k >= cur) return '.';

        long idx = k;

        for (int i = n - 1; i >= 0; i--) {
            char ch = s.charAt(i);

            long before = (i == 0) ? 0 : len[i - 1];
            long after = len[i];

            if (ch >= 'a' && ch <= 'z') {
                if (idx == before) {
                    return ch;
                }
            } 
            else if (ch == '*') {
                // Forward: removed last character.
                // Backward: resulting string is a prefix of previous string.
                // idx remains unchanged.
            } 
            else if (ch == '#') {
                if (before > 0) {
                    idx %= before;
                }
            } 
            else { // '%'
                idx = after - 1 - idx;
            }
        }

        return '.';
    }
}