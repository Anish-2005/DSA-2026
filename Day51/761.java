//761. Special Binary String

//Special binary strings are binary strings with the following two properties:

//The number of 0's is equal to the number of 1's.
//Every prefix of the binary string has at least as many 1's as 0's.
//You are given a special binary string s.

//A move consists of choosing two consecutive, non-empty, special substrings of s, and swapping them. Two strings are consecutive if the last character of the first string is exactly one index before the first character of the second string.

//Return the lexicographically largest resulting string possible after applying the mentioned operations on the string.

class Solution {
    public String makeLargestSpecial(String s) {
        List<String> parts = new ArrayList<>();
        int count = 0, start = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') count++;
            else count--;

            if (count == 0) {
                String inner = s.substring(start + 1, i);
                parts.add("1" + makeLargestSpecial(inner) + "0");
                start = i + 1;
            }
        }

        parts.sort(Collections.reverseOrder());
        return String.join("", parts);
    }
}