
/*1009. Complement of Base 10 Integer

The complement of an integer is the integer you get when you flip all the 0's to 1's and all the 1's to 0's in its binary representation.

For example, The integer 5 is "101" in binary and its complement is "010" which is the integer 2.
Given an integer n, return its complement.
*/

class Solution { //class
    public int bitwiseComplement(int n) { //method function
        if (n == 0) return 1; //edge case
        int mask = 1; //mask to find the highest power of 2 less than or equal to n
        while (mask <= n) { //loop to find the highest power of 2 less than or equal to n
            mask <<= 1;//left shift to find the next power of 2
        }
        return mask - 1 - n; //mask - 1 gives us a number with all bits set to 1 up 
        // to the highest bit of n, then we subtract n to get the complement
    }
}
 