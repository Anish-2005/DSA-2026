//1689. Partitioning Into Minimum Number Of Deci-Binary Numbers

//A decimal number is called deci-binary if each of its digits is either 0 or 1 
// without any leading zeros. For example, 101 and 1100 are deci-binary, while 112 and 3001 are not.

//Given a string n that represents a positive decimal integer, return the minimum 
// number of positive deci-binary numbers needed so that they sum up to n.

class Solution {
    public int minPartitions(String n) { //function  with input string n
        int ans = 0; //answer to store the max digits needed
        for (int i = 0; i < n.length(); i++) { //loop to n's length
            ans = Math.max(ans, n.charAt(i) - '0'); // answer will be the maximum digit in n because we 
            // can only use 1's to sum up to n, so if we have a digit 9 ,we need at least 9 
            // deci-binary numbers to sum up to it, if we have a digit 0, we don't need any deci-binary 
            // number for that digit, if we have a digit 1, we need at least 1 deci-binary number for that digit 
            // and so on. So the answer will be the maximum digit in n.
        }
        return ans;
    }
}