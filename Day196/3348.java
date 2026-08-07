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

    private static final int[][] FACTORS = {
        {0, 0, 0, 0}, // 0
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

        // ========================================
        // 1. Factorize t
        // ========================================

        int[] need = new int[4];

        while (t % 2 == 0) {
            need[0]++;
            t /= 2;
        }

        while (t % 3 == 0) {
            need[1]++;
            t /= 3;
        }

        while (t % 5 == 0) {
            need[2]++;
            t /= 5;
        }

        while (t % 7 == 0) {
            need[3]++;
            t /= 7;
        }

        // Contains prime factors other than 2,3,5,7
        if (t != 1) {
            return "-1";
        }

        // ========================================
        // 2. Try same length
        // ========================================

        String ans = solveSameLength(num, need);

        if (ans != null) {
            return ans;
        }

        // ========================================
        // 3. Need a longer number
        // ========================================

        int minLen = minDigits(need);

        int length = Math.max(num.length() + 1, minLen);

        return buildSmallest(length, need);
    }


    // =========================================================
    // Try finding smallest valid number >= num
    // having exactly num.length() digits
    // =========================================================

    private String solveSameLength(String num, int[] target) {

        int n = num.length();

        /*
         * prefixNeed[i]
         *
         * Remaining prime requirements after consuming
         * num[0 ... i-1].
         */

        int[][] prefixNeed = new int[n + 1][4];

        for (int j = 0; j < 4; j++) {
            prefixNeed[0][j] = target[j];
        }

        boolean prefixZeroFree = true;

        for (int i = 0; i < n; i++) {

            int digit = num.charAt(i) - '0';

            if (digit == 0) {
                prefixZeroFree = false;

                // after a zero, exact prefix cannot be used
                for (int j = i + 1; j <= n; j++) {
                    prefixNeed[j] = null;
                }

                break;
            }

            prefixNeed[i + 1] =
                    subtract(prefixNeed[i], digit);
        }


        // ========================================
        // Check if num itself works
        // ========================================

        if (prefixZeroFree &&
            satisfied(prefixNeed[n])) {

            return num;
        }


        // ========================================
        // Find rightmost position we can increase
        // ========================================

        for (int i = n - 1; i >= 0; i--) {

            // Exact prefix before i must be valid
            if (prefixNeed[i] == null) {
                continue;
            }

            int currentDigit =
                    num.charAt(i) - '0';

            int start =
                    Math.max(1, currentDigit + 1);


            // Try smallest larger digit
            for (int d = start; d <= 9; d++) {

                int[] remaining =
                        subtract(prefixNeed[i], d);

                int slots =
                        n - i - 1;

                if (minDigits(remaining) <= slots) {

                    StringBuilder result =
                            new StringBuilder();

                    result.append(
                        num.substring(0, i)
                    );

                    result.append(d);

                    result.append(
                        buildSmallest(slots, remaining)
                    );

                    return result.toString();
                }
            }
        }


        // ========================================
        // Special case:
        //
        // num contains zero.
        //
        // Example:
        // 1024
        //
        // We can keep "1" and replace 0 with >=1.
        // ========================================

        int[] remaining = target.clone();

        StringBuilder prefix =
                new StringBuilder();

        for (int i = 0; i < n; i++) {

            int current =
                    num.charAt(i) - '0';

            if (current == 0) {

                for (int d = 1; d <= 9; d++) {

                    int[] next =
                            subtract(remaining, d);

                    int slots =
                            n - i - 1;

                    if (minDigits(next) <= slots) {

                        return prefix
                                + String.valueOf(d)
                                + buildSmallest(
                                    slots,
                                    next
                                );
                    }
                }

                break;
            }

            prefix.append(current);

            remaining =
                    subtract(remaining, current);
        }

        return null;
    }


    // =========================================================
    // Build lexicographically smallest number of given length
    // satisfying remaining prime requirements
    // =========================================================

    private String buildSmallest(
            int length,
            int[] target) {

        StringBuilder result =
                new StringBuilder();

        int[] need =
                target.clone();


        for (int pos = 0;
             pos < length;
             pos++) {

            int slots =
                    length - pos - 1;


            // Try digits in increasing order
            for (int d = 1; d <= 9; d++) {

                int[] next =
                        subtract(need, d);

                if (minDigits(next) <= slots) {

                    result.append(d);

                    need = next;

                    break;
                }
            }
        }

        return result.toString();
    }


    // =========================================================
    // Subtract digit's prime contribution
    // =========================================================

    private int[] subtract(
            int[] need,
            int digit) {

        int[] result =
                new int[4];

        for (int i = 0; i < 4; i++) {

            result[i] =
                    Math.max(
                        0,
                        need[i]
                        - FACTORS[digit][i]
                    );
        }

        return result;
    }


    // =========================================================
    // Minimum digits needed
    // =========================================================

    private int minDigits(int[] need) {

        int two   = need[0];
        int three = need[1];
        int five  = need[2];
        int seven = need[3];

        int count = 0;


        // -----------------------------------------
        // 5 and 7 cannot combine with other primes
        // -----------------------------------------

        count += five;
        count += seven;


        // -----------------------------------------
        // Pack 2s and 3s optimally
        //
        // Digits:
        //
        // 8 = 2^3
        // 9 = 3^2
        // 6 = 2*3
        // 4 = 2^2
        // 2 = 2
        // 3 = 3
        // -----------------------------------------


        // First use 8s
        count += two / 3;
        two %= 3;


        // Use 9s
        count += three / 2;
        three %= 2;


        // Remaining possibilities are tiny

        if (two == 0 && three == 0) {
            return count;
        }


        if (two == 1 && three == 1) {

            // 6
            count++;

        } else if (two == 2 && three == 1) {

            /*
             * Need:
             *
             * 2^2 * 3
             *
             * Can use:
             *
             * 2 * 6
             *
             * OR
             *
             * 3 * 4
             */

            count += 2;

        } else {

            if (two == 1) {
                count++;       // 2
            }

            if (two == 2) {
                count++;       // 4
            }

            if (three == 1) {
                count++;       // 3
            }
        }

        return count;
    }


    private boolean satisfied(int[] need) {

        return need[0] == 0 &&
               need[1] == 0 &&
               need[2] == 0 &&
               need[3] == 0;
    }
}