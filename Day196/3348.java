/*[**3348. Smallest Divisible Digit Product II**](https://leetcode.com/problems/smallest-divisible-digit-product-ii/)
Hard
Topics
Companies
Hint
You are given a string `num` which represents a **positive** integer, and an integer `t`.
A number is called **zero-free** if *none* of its digits are 0.
Return a string representing the **smallest** **zero-free** number greater than or equal to `num` such that the **product of its digits** is divisible by `t`. If no such number exists, return `"-1"`.
 
**Example 1:**
**Input:** num = "1234", t = 256
**Output:** "1488"
**Explanation:**
The smallest zero-free number that is greater than 1234 and has the product of its digits divisible by 256 is 1488, with the product of its digits equal to 256.
**Example 2:** */ 

import java.util.*;

class Solution {

    // Prime contribution of digits 1..9
    // index: {power of 2, power of 3, power of 5, power of 7}
    private static final int[][] FACTORS = {
        {0, 0, 0, 0}, // 0 - unused
        {0, 0, 0, 0}, // 1
        {1, 0, 0, 0}, // 2
        {0, 1, 0, 0}, // 3
        {2, 0, 0, 0}, // 4
        {0, 0, 1, 0}, // 5
        {1, 1, 0, 0}, // 6
        {0, 0, 0, 1}, // 7
        {3, 0, 0, 0}, // 8
        {0, 2, 0, 0}  // 9
    };

    public String smallestNumber(String num, long t) {

        // --------------------------------------------
        // STEP 1: Factorize t into 2, 3, 5 and 7
        // --------------------------------------------

        long temp = t;

        int need2 = 0;
        int need3 = 0;
        int need5 = 0;
        int need7 = 0;

        while (temp % 2 == 0) {
            need2++;
            temp /= 2;
        }

        while (temp % 3 == 0) {
            need3++;
            temp /= 3;
        }

        while (temp % 5 == 0) {
            need5++;
            temp /= 5;
        }

        while (temp % 7 == 0) {
            need7++;
            temp /= 7;
        }

        // t contains some other prime factor
        if (temp != 1) {
            return "-1";
        }

        int[] target = {need2, need3, need5, need7};

        // --------------------------------------------
        // STEP 2: Try to construct answer
        // with same length as num
        // --------------------------------------------

        String sameLength = buildGreaterOrEqual(num, target);

        if (sameLength != null) {
            return sameLength;
        }

        // --------------------------------------------
        // STEP 3: Same length impossible.
        // Build the smallest number of length n + 1.
        // --------------------------------------------

        return buildSmallest(num.length() + 1, target);
    }


    // ==========================================================
    // Build smallest zero-free number >= num
    // ==========================================================

    private String buildGreaterOrEqual(String num, int[] target) {

        int n = num.length();

        int[] remaining = target.clone();

        StringBuilder prefix = new StringBuilder();

        /*
         * First try following num exactly.
         *
         * Whenever something becomes impossible,
         * we backtrack and increase a previous digit.
         */

        for (int i = 0; i < n; i++) {

            int originalDigit = num.charAt(i) - '0';

            // zero cannot be used
            if (originalDigit == 0) {

                // We already match prefix.
                // At this position choose smallest digit > 0.
                for (int d = 1; d <= 9; d++) {

                    int[] next = subtract(remaining, d);

                    if (canFill(next, n - i - 1)) {

                        return prefix.toString()
                                + d
                                + buildSuffix(n - i - 1, next);
                    }
                }

                return backtrack(num, prefix, remaining, i);
            }

            // Try keeping same digit
            int[] next = subtract(remaining, originalDigit);

            if (canFill(next, n - i - 1)) {

                prefix.append(originalDigit);
                remaining = next;

                continue;
            }

            /*
             * Keeping same digit doesn't work.
             *
             * Try increasing current digit.
             */

            for (int d = originalDigit + 1; d <= 9; d++) {

                next = subtract(remaining, d);

                if (canFill(next, n - i - 1)) {

                    return prefix.toString()
                            + d
                            + buildSuffix(n - i - 1, next);
                }
            }

            // Current position can't be fixed.
            return backtrack(num, prefix, remaining, i);
        }

        // Exact num itself works
        if (isSatisfied(remaining)) {
            return prefix.toString();
        }

        return backtrack(num, prefix, remaining, n);
    }


    // ==========================================================
    // Backtrack to an earlier position and increase its digit
    // ==========================================================

    private String backtrack(
            String num,
            StringBuilder prefix,
            int[] currentRemaining,
            int position) {

        int n = num.length();

        /*
         * Recompute prime requirements for every possible
         * backtracking position.
         *
         * n is small enough that this is fine.
         */

        for (int pos = position - 1; pos >= 0; pos--) {

            int[] remaining = getRemainingForPrefix(num, pos);

            int originalDigit = num.charAt(pos) - '0';

            for (int d = Math.max(1, originalDigit + 1); d <= 9; d++) {

                int[] next = subtract(remaining, d);

                int slots = n - pos - 1;

                if (canFill(next, slots)) {

                    StringBuilder result = new StringBuilder();

                    // Copy unchanged prefix
                    for (int j = 0; j < pos; j++) {
                        result.append(num.charAt(j));
                    }

                    result.append(d);

                    result.append(buildSuffix(slots, next));

                    return result.toString();
                }
            }
        }

        return null;
    }


    // ==========================================================
    // Remaining requirement after using prefix num[0..length-1]
    // ==========================================================

    private int[] getRemainingForPrefix(String num, int length) {

        int[] remaining = currentTarget.clone();

        for (int i = 0; i < length; i++) {

            int digit = num.charAt(i) - '0';

            if (digit == 0) {
                break;
            }

            remaining = subtract(remaining, digit);
        }

        return remaining;
    }


    // Need target globally for helper
    private int[] currentTarget;


    // ==========================================================
    // Subtract contribution of a digit
    // ==========================================================

    private int[] subtract(int[] need, int digit) {

        return new int[]{
            Math.max(0, need[0] - FACTORS[digit][0]),
            Math.max(0, need[1] - FACTORS[digit][1]),
            Math.max(0, need[2] - FACTORS[digit][2]),
            Math.max(0, need[3] - FACTORS[digit][3])
        };
    }


    // ==========================================================
    // Can remaining requirements fit inside slots?
    // ==========================================================

    private boolean canFill(int[] need, int slots) {

        return minDigits(
                need[0],
                need[1],
                need[2],
                need[3]
        ) <= slots;
    }


    // ==========================================================
    // Minimum digits required for given prime requirements
    //
    // Instead of large DP, greedily pack prime factors into
    // digits 8,9,6,4, etc.
    // ==========================================================

    private int minDigits(int two, int three, int five, int seven) {

        int count = 0;

        // 5 and 7 each require their own digit
        count += five;
        count += seven;

        /*
         * For 2 and 3:
         *
         * 8 = 2^3
         * 9 = 3^2
         * 6 = 2*3
         *
         * We try combining leftover 2 and 3 into 6.
         */

        count += two / 3;
        two %= 3;

        count += three / 2;
        three %= 2;

        // leftovers
        if (two == 1 && three == 1) {
            count++; // digit 6
        } else {

            if (two == 2) {
                count++; // digit 4
            } else if (two == 1) {
                count++; // digit 2
            }

            if (three == 1) {
                count++; // digit 3
            }
        }

        return count;
    }


    // ==========================================================
    // Build lexicographically smallest suffix
    // ==========================================================

    private String buildSuffix(int length, int[] need) {

        StringBuilder result = new StringBuilder();

        for (int pos = 0; pos < length; pos++) {

            int remainingSlots = length - pos - 1;

            // smallest digit first
            for (int d = 1; d <= 9; d++) {

                int[] next = subtract(need, d);

                if (canFill(next, remainingSlots)) {

                    result.append(d);
                    need = next;

                    break;
                }
            }
        }

        return result.toString();
    }


    // ==========================================================
    // Build smallest number of a specified length
    // ==========================================================

    private String buildSmallest(int length, int[] target) {

        int[] need = target.clone();

        StringBuilder result = new StringBuilder();

        for (int pos = 0; pos < length; pos++) {

            int remainingSlots = length - pos - 1;

            for (int d = 1; d <= 9; d++) {

                int[] next = subtract(need, d);

                if (canFill(next, remainingSlots)) {

                    result.append(d);
                    need = next;
                    break;
                }
            }
        }

        return result.toString();
    }


    private boolean isSatisfied(int[] need) {

        return need[0] == 0
                && need[1] == 0
                && need[2] == 0
                && need[3] == 0;
    }
}