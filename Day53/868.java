//868. Binary Gap

//Given a positive integer n, find and return the longest 
// distance between any two adjacent 1's in the binary representation of n. If there are no two adjacent 1's, return 0.

//Two 1's are adjacent if there are only 0's separating them 
// (possibly no 0's). The distance between two 1's is the absolute 
// difference between their bit positions. For example, the two 1's in "1001" have a distance of 3.
import java.util.*;
class Solution {
    public int binaryGap(int n) { //function with input n
        int last = -1; //last position of 1
        int pos = 0; //current position
        int ans = 0; //to return the maximum distance

        while (n > 0) { //n must be greater than 0
            if ((n & 1) == 1) { //if last bit is 1
                if (last != -1) { //if last is not -1 then we got a pair of 1's
                    ans = Math.max(ans, pos - last); //update ans with the maximum distance found
                }
                last = pos; //last position of 1
            }
            pos++; //pos increase
            n >>= 1; //right shift n by 1 to check next bit
        }
        return ans; //return answer
    }
}