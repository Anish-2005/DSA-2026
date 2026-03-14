/*1415. The k-th Lexicographical String of All Happy Strings of Length n
Solved
Medium
Topics
premium lock icon
Companies
Hint
A happy string is a string that:

consists only of letters of the set ['a', 'b', 'c'].
s[i] != s[i + 1] for all values of i from 1 to s.length - 1 
(string is 1-indexed).
For example, strings "abc", "ac", "b" and "abcbabcbcb" are all 
happy strings and strings "aa", "baa" and "ababbc" are not happy strings.

Given two integers n and k, consider a list of all happy strings 
of length n sorted in lexicographical order.

Return the kth string of this list or return an empty string if 
there are less than k happy strings of length n.*/

 

// Solution class to generate the k-th lexicographical happy string of length n
class Solution {
    int count = 0; // Counter for how many happy strings have been generated so far
    String ans = ""; // Stores the k-th happy string when found

    // Main function to get the k-th happy string of length n
    public String getHappyString(int n, int k) {
        dfs(n, k, ""); // Start DFS with an empty string
        return ans; // Return the answer (k-th happy string or empty if not found)
    }

    // Depth-First Search to build happy strings
    void dfs(int n, int k, String cur) {
        // If the current string has reached length n
        if (cur.length() == n) {
            count++; // Increment the count of happy strings found
            if (count == k) ans = cur; // If this is the k-th, store it as answer
            return; // Stop further recursion
        }

        // Try to append each character 'a', 'b', 'c' to the current string
        for (char c : new char[]{'a','b','c'}) {
            // Skip if the last character is the same as the current character (no consecutive same letters)
            if (cur.length() > 0 && cur.charAt(cur.length()-1) == c) continue;
            // If answer is already found, stop further recursion
            if (!ans.equals("")) return;
            // Recurse with the new character appended
            dfs(n, k, cur + c);
        }
    }
}

/*
Visualization of the DFS tree for n = 3:

                ""
              /  |  \
            a    b   c
          /  \  / \  / \
         b    c a  c a  b
        /      |    |    \
      c      b    b     c

Each path from root to a leaf of length n (here n=3) is a happy string.
The DFS explores all such paths, skipping those with consecutive same letters.
The k-th string found in lexicographical order is returned.
*/