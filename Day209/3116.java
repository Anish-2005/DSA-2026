/*3116. Kth Smallest Amount With Single Denomination Combination
Hard
Topics
premium lock icon
Companies
Hint
You are given an integer array coins representing coins of different denominations and an integer k.

You have an infinite number of coins of each denomination. However, you are not allowed to combine coins of different denominations.

Return the kth smallest amount that can be made using these coins.

 

Example 1:

Input: coins = [3,6,9], k = 3

Output: 9

Explanation: The given coins can make the following amounts:
Coin 3 produces multiples of 3: 3, 6, 9, 12, 15, etc.
Coin 6 produces multiples of 6: 6, 12, 18, 24, etc.
Coin 9 produces multiples of 9: 9, 18, 27, 36, etc.
All of the coins combined produce: 3, 6, 9, 12, 15, etc. */

import java.util.*;

class Solution {
    public long findKthSmallest(int[] coins, int k) {
        // Remove duplicate / redundant denominations
        Arrays.sort(coins);

        List<Integer> useful = new ArrayList<>();

        for (int coin : coins) {
            boolean redundant = false;

            for (int x : useful) {
                if (coin % x == 0) {
                    redundant = true;
                    break;
                }
            }

            if (!redundant) {
                useful.add(coin);
            }
        }

        int n = useful.size();

        long low = 1;
        long high = (long) useful.get(0) * k;

        while (low < high) {
            long mid = low + (high - low) / 2;

            if (count(mid, useful, n) >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private long count(long x, List<Integer> coins, int n) {
        long result = 0;

        // Inclusion-exclusion
        for (int mask = 1; mask < (1 << n); mask++) {

            long lcm = 1;
            int bits = 0;
            boolean overflow = false;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bits++;

                    long g = gcd(lcm, coins.get(i));

                    // Check whether LCM would exceed x
                    if (lcm > x / (coins.get(i) / g)) {
                        overflow = true;
                        break;
                    }

                    lcm = lcm / g * coins.get(i);
                }
            }

            if (overflow || lcm > x) {
                continue;
            }

            long cnt = x / lcm;

            if ((bits & 1) == 1) {
                result += cnt;
            } else {
                result -= cnt;
            }
        }

        return result;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}