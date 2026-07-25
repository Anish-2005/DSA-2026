/*3536. Maximum Product of Two Digits
Solved
Easy
Topics
premium lock icon
Companies
Hint
You are given a positive integer n.

Return the maximum product of any two digits in n.

Note: You may use the same digit twice if it appears more than once in n.

 

Example 1:

Input: n = 31

Output: 3

Explanation:

The digits of n are [3, 1].
The possible products of any two digits are: 3 * 1 = 3.
The maximum product is 3.*/

class Solution {
    public int maxProduct(int n) {
        int max1 = -1, max2 = -1;

        while (n > 0) {
            int digit = n % 10;

            if (digit >= max1) {
                max2 = max1;
                max1 = digit;
            } else if (digit > max2) {
                max2 = digit;
            }

            n /= 10;
        }

        return max1 * max2;
    }
}