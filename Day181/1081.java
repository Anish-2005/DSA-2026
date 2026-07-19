/*1081. Smallest Subsequence of Distinct Characters

Given a string s, return the lexicographically smallest subsequence of s that 
contains all the distinct characters of s exactly once.

Example 1:

Input: s = "bcabc"
Output: "abc" */

class Solution {
    public String smallestSubsequence(String s) {
        int[] last = new int[26];

        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }

        boolean[] visited = new boolean[26];
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (visited[ch - 'a'])
                continue;

            while (!stack.isEmpty()
                    && stack.peekLast() > ch
                    && last[stack.peekLast() - 'a'] > i) {

                visited[stack.pollLast() - 'a'] = false;
            }

            stack.offerLast(ch);
            visited[ch - 'a'] = true;
        }

        StringBuilder ans = new StringBuilder();

        while (!stack.isEmpty())
            ans.append(stack.pollFirst());

        return ans.toString();
    }
}