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