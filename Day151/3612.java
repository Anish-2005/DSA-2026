/*
3612. Process String with Special Operations I

You are given a string s consisting of lowercase English letters and the special characters: *, #, and %.

Build a new string result by processing s according to the following rules from left to right:

If the letter is a lowercase English letter append it to result.
A '*' removes the last character from result, if it exists.
A '#' duplicates the current result and appends it to itself.
A '%' reverses the current result.
Return the final string result after processing all characters in s.

 

Example 1:

Input: s = "a#b%*"

Output: "ba"

Explanation:

i	s[i]	Operation	Current result
0	'a'	Append 'a'	"a"
1	'#'	Duplicate result	"aa"
2	'b'	Append 'b'	"aab"
3	'%'	Reverse result	"baa"
4	'*'	Remove the last character	"ba"
Thus, the final result is "ba". */

class Solution {
    public String processStr(String s) {
        StringBuilder sb = new StringBuilder();

        for (char ch : s.toCharArray()) {
            if (ch >= 'a' && ch <= 'z') {
                sb.append(ch);
            } 
            else if (ch == '*') {
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
            } 
            else if (ch == '#') {
                sb.append(sb.toString());
            } 
            else if (ch == '%') {
                sb.reverse();
            }
        }

        return sb.toString();
    }
}