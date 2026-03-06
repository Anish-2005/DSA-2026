//1784. Check if Binary String Has at Most One Segment of Ones

//Given a binary string s ​​​​​without leading zeros, return true​​​ if s contains at most one contiguous segment of ones. Otherwise, return false.

//Example 1:

//Input: s = "1001"
//Output: false
//Explanation: The ones do not form a contiguous segment.

class Solution {
    public boolean checkOnesSegment(String s) {
        return !s.contains("01");
    }
}

//0 dividing into segments of 1s. 01 divides into 2 segments checks.

//if not found then result= true.