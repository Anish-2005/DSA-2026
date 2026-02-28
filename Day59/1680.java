//1680. Concatenation of Consecutive Binary Numbers

//Given an integer n, return the decimal value of the binary 
// string formed by concatenating the binary representations 
// of 1 to n in order, modulo 109 + 7.

class Solution { //class
    public int concatenatedBinary(int n) {// function with input n
        long mod = 1000000007; //modulo 10^9 + 7
        long res = 0; //resultant output
        int bits = 0; //no of bits required

        for (int i = 1; i <= n; i++) { //each number 1 to n
            if ((i & (i - 1)) == 0) bits++; //if i is power of 2 then we need an extra bit
            res = ((res << bits) + i) % mod; //shift rest to left by bits and adding i to it and take modulo
        }
        return (int) res; //returning result as integer
    }
}