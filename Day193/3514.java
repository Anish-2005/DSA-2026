/* 3514. Number of Unique XOR Triplets II

You are given an integer array nums.

A XOR triplet is defined as the XOR of three elements nums[i] XOR nums[j] XOR nums[k] where i <= j <= k.

Return the number of unique XOR triplet values from all possible triplets (i, j, k).

 

Example 1:

Input: nums = [1,3]

Output: 2

Explanation:

The possible XOR triplet values are:

(0, 0, 0) → 1 XOR 1 XOR 1 = 1
(0, 0, 1) → 1 XOR 1 XOR 3 = 3
(0, 1, 1) → 1 XOR 3 XOR 3 = 1
(1, 1, 1) → 3 XOR 3 XOR 3 = 3
The unique XOR values are {1, 3}. Thus, the output is 2. */

class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int MAX = 2048;

        boolean[] one = new boolean[MAX];
        boolean[] two = new boolean[MAX];
        boolean[] three = new boolean[MAX];

        for (int num : nums) {

            // Extend existing pairs to triplets
            for (int x = 0; x < MAX; x++) {
                if (two[x]) {
                    three[x ^ num] = true;
                }
            }

            // Extend existing singles to pairs
            for (int x = 0; x < MAX; x++) {
                if (one[x]) {
                    two[x ^ num] = true;
                }
            }

            // i = j = k cases are allowed
            one[num] = true;
            two[0] = true;       // num ^ num
            three[num] = true;   // num ^ num ^ num
        }

        int count = 0;

        for (boolean possible : three) {
            if (possible) {
                count++;
            }
        }

        return count;
    }
}