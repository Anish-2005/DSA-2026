//231. Power of Two
//Given an integer n, return true if it is a power of two. Otherwise, return false.

//An integer n is a power of two, if there exists an integer x such that n == 2x.

 class Solution {
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
}

//The idea behind the solution

//A power of two in binary has a very special property.

//Powers of two in binary
//Number	Binary
//1	0001
//2	0010
//4	0100
//8	1000
//16	1 0000

//👉 Only ONE bit is set to 1, all others are 0.

//What does (n & (n - 1)) do?
//Example 1: n = 8
//n     = 1000
//n - 1 = 0111
//----------------
//&     = 0000


//Result is 0 ✅