/*1980. Find Unique Binary String

Given an array of strings nums containing n unique binary strings 
each of length n, return a binary string of length n that does not 
appear in nums. If there are multiple answers, you may return any of them. */

class Solution {
    public String findDifferentBinaryString(String[] nums) { //function
        int n = nums.length; //length of the input array
        char[] res = new char[n]; //array to store the resulting binary string
        for (int i = 0; i < n; i++) { //iterate through each string in the input array
            res[i] = nums[i].charAt(i) == '0' ? '1' : '0'; //flip the i-th character 
            // of the i-th string to ensure uniqueness
        }
        return new String(res); //convert the character array to a string and return it
    }
}

/* 
Example

nums = ["010",
        "111",
        "001"]

Look at the diagonal elements:
   0 1 0
   ↑
   1 1 1
     ↑
   0 0 1
       ↑
Diagonal = 0 1 1
Flip each bit:
0 → 1
1 → 0
1 → 0
Result:
100
Check:

010
111
001
---
100   (different from all)

Key idea:

nums[i][i] ≠ result[i]

So the new string differs from the i-th string at position i, 
guaranteeing it is not in the array.
*/