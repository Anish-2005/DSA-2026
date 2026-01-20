//3314. Construct the Minimum Bitwise Array I


//You are given an array nums consisting of n prime integers.

//You need to construct an array ans of length n, such that, 
// for each index i, the bitwise OR of ans[i] and ans[i] + 1 is equal to nums[i], i.e. ans[i] OR (ans[i] + 1) == nums[i].

//Additionally, you must minimize each value of ans[i] in the 
// resulting array.

//If it is not possible to find such a value for ans[i] that 
// satisfies the condition, then set ans[i] = -1.
import java.util.*;

class Solution {
    public List<Integer> minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        List<Integer> ans = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            int x = nums.get(i);
            if (x == 2) {
                ans.add(-1);
            } else {
                ans.add(x - 1);
            }
        }

        return ans;
    }
}
