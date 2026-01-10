//2264. Largest 3-Same-Digit Number in String

//You are given a string num representing a large integer. 
// An integer is good if it meets the following conditions:

//It is a substring of num with length 3.
//It consists of only one unique digit.
//Return the maximum good integer as a string or an 
// empty string "" if no such integer exists.

class Solution {
    public String largestGoodInteger(String num) {
        String ans = "";

        for (int i = 0; i <= num.length() - 3; i++) {
            char c = num.charAt(i);
            if (c == num.charAt(i + 1) && c == num.charAt(i + 2)) {
                String curr = num.substring(i, i + 3);
                if (curr.compareTo(ans) > 0) {
                    ans = curr;
                }
            }
        }
        return ans;
    }
}