/*2839. Check if Strings Can be Made Equal With Operations I
Easy
Topics
premium lock icon
Companies
Hint
You are given two strings s1 and s2, both of length 4, consisting of lowercase 
English letters.

You can apply the following operation on any of the two strings any number of times:

Choose any two indices i and j such that j - i = 2, then swap the two characters at 
those indices in the string.
Return true if you can make the strings s1 and s2 equal, and false otherwise.

 

Example 1:

Input: s1 = "abcd", s2 = "cdab"
Output: true
Explanation: We can do the following operations on s1:
- Choose the indices i = 0, j = 2. The resulting string is s1 = "cbad".
- Choose the indices i = 1, j = 3. The resulting string is s1 = "cdab" = s2.
Example 2:

Input: s1 = "abcd", s2 = "dacb"
Output: false
Explanation: It is not possible to make the two strings equal. */

class Solution {
    public boolean canBeEqual(String s1, String s2) {
        
        char[] a = {s1.charAt(0), s1.charAt(2)};
        char[] b = {s2.charAt(0), s2.charAt(2)};
        
        java.util.Arrays.sort(a);
        java.util.Arrays.sort(b);
        
        if(a[0] != b[0] || a[1] != b[1]) return false;
        
        char[] c = {s1.charAt(1), s1.charAt(3)};
        char[] d = {s2.charAt(1), s2.charAt(3)};
        
        java.util.Arrays.sort(c);
        java.util.Arrays.sort(d);
        
        return c[0] == d[0] && c[1] == d[1];
    }
}