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

//Quick Visualization
//Example: "1001"

//1 0 0 1
//^     ^
//segment1  segment2

//Two separate segments → false
//Example: "1111000"

//1111 000
//^^^^
//one segment
//Only one segment → true

//Example: "110"
//11 0
//^^
//one segment

//Result → true.