///1404. Number of Steps to Reduce a Number in Binary Representation to One

//Given the binary representation of an integer as a string s, return the number of steps to reduce it to 1 under the following rules:

//If the current number is even, you have to divide it by 2.

//If the current number is odd, you have to add 1 to it.

//It is guaranteed that you can always reach one for all test cases.

class Solution {
    public int numSteps(String s) {
        int steps = 0;
        int carry = 0;
        int n = s.length();

        for (int i = n - 1; i > 0; i--) {
            int bit = (s.charAt(i) - '0') + carry;
            if (bit % 2 == 0) {
                steps += 1;
            } else {
                steps += 2;
                carry = 1;
            }
        }

        if (carry == 1) {
            steps += 1;
        }

        return steps;
    }
}