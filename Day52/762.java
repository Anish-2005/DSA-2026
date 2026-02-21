//762. Prime Number of Set Bits in Binary Representation

//Given two integers left and right, return the count of numbers in the inclusive range [left, right] having a prime number of set bits in their binary representation.

//Recall that the number of set bits an integer has is the number of 1's present when written in binary.

//For example, 21 written in binary is 10101, which has 3 set bits.

class Solution {
    private int countSetBits(int n) {
        int count = 0;
        while (n > 0) {
            n = n & (n - 1);
            count++;
        }
        return count;
    }

    public int countPrimeSetBits(int left, int right) {
        boolean[] isPrime = {
            false, false, true,  true,  false,
            true,  false, true,  false, false,
            false, true,  false, true,  false,
            false, false, true,  false, true, false
        };

        int result = 0;

        for (int i = left; i <= right; i++) {
            int bits = countSetBits(i);
            if (isPrime[bits]) {
                result++;
            }
        }

        return result;
    }
}