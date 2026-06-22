/*
1189. Maximum Number of Balloons

Given a string text, you want to use the characters of text to form as many instances of the word "balloon" as possible.

You can use each character in text at most once. Return the maximum number of instances that can be formed.

 

Example 1:



Input: text = "nlaebolko"
Output: 1
Example 2: */


class Solution {
    public int maxNumberOfBalloons(String text) {
        int[] count = new int[26];

        for (char c : text.toCharArray()) {
            count[c - 'a']++;
        }

        int ans = Integer.MAX_VALUE;

        ans = Math.min(ans, count['b' - 'a']);
        ans = Math.min(ans, count['a' - 'a']);
        ans = Math.min(ans, count['l' - 'a'] / 2); // need two l's
        ans = Math.min(ans, count['o' - 'a'] / 2); // need two o's
        ans = Math.min(ans, count['n' - 'a']);

        return ans;
    }
}