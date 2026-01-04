//1390. Four Divisors
//Given an integer array nums, return 
//the sum of divisors of the integers in 
//that array that have exactly four divisors. 
//If there is no such integer in the array, return 0.

class Solution {
    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
    public int sumFourDivisors(int[] nums) {
        int sum = 0;
        for (int x : nums) {
            int cbrt = (int) Math.round(Math.cbrt(x));
            if (cbrt * cbrt * cbrt == x && isPrime(cbrt)) {
                sum += 1 + cbrt + cbrt * cbrt + x;
                continue;
            }
            int divisor = -1;
            for (int d = 2; d * d <= x; d++) {
                if (x % d == 0) {
                    divisor = d;
                    break;
                }
            }
            if (divisor == -1) continue;
            int other = x / divisor;
            if (divisor != other && isPrime(divisor) && isPrime(other)) {
                sum += 1 + divisor + other + x;
            }
        }
        return sum;
    }
}
