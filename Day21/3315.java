//3315. Construct the Minimum Bitwise Array II

//You are given an array nums consisting of n prime integers.

//You need to construct an array ans of length n, such that, 
// for each index i, the bitwise OR of ans[i] and ans[i] + 1 is equal to nums[i], i.e. ans[i] OR (ans[i] + 1) == nums[i].

//Additionally, you must minimize each value of ans[i] in the
//  resulting array.

//If it is not possible to find such a value for ans[i] that 
// satisfies the condition, then set ans[i] = -1.
 

class Solution {
    public int[] minBitwiseArray(java.util.List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {
            int x = nums.get(i);

            if ((x & 1) == 0) {
                ans[i] = -1;
                continue;
            }

            int b = 0;
            while (((x >> b) & 1) == 1) b++;

            int a = x ^ (1 << (b - 1));
            ans[i] = (a | (a + 1)) == x ? a : -1;
        }
        return ans;
    }
}
