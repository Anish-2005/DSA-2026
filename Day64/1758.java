//1758. Minimum Changes To Make Alternating Binary String

//You are given a string s consisting only of the characters '0' and '1'. 
//In one operation, you can change any '0' to '1' or vice versa.

//The string is called alternating if no two adjacent characters are equal. 
//For example, the string "010" is alternating, while the string "0100" is not.

//Return the minimum number of operations needed to make s alternating.

class Solution { //class
    public int minOperations(String s) { //method function
        int n = s.length(); //length of string
        int start0 = 0, start1 = 0; //counters for both patterns

        for (int i = 0; i < n; i++) { //loop upto length of string
            char expected0 = (i % 2 == 0) ? '0' : '1'; //expected character for pattern starting with '0'
            char expected1 = (i % 2 == 0) ? '1' : '0';//expected character for pattern starting with '1'

            if (s.charAt(i) != expected0) start0++; //if current character does not match expected for pattern 0, increment start0
            if (s.charAt(i) != expected1) start1++; //if current character does not match expected for pattern 1, increment start1
        }

        return Math.min(start0, start1); //return the minimum of the two patterns
    }
}

//Let’s visualize how the code works step-by-step.
///An alternating binary string can only have two possible patterns:
///Start with 0: 010101...
///Start with 1: 101010...
//The algorithm checks how many characters need to change to match each pattern, then returns the minimum.
//Example
//s = "0100"
//Pattern 1 → Start with 0

//Expected pattern: 0101
//Index	Actual	Expected	Change?
//0	0	0	❌
//1	1	1	❌
//2	0	0	❌
//3	0	1	✅
//Changes needed = 1

//Pattern 2 → Start with 1

//Expected pattern: 1010
//Index	Actual	Expected	Change?
//0	0	1	✅
//1	1	0	✅
//2	0	1	✅
//3	0	0	❌
//Changes needed = 3

//Final Answer
//min(1, 3) = 1
//So only 1 operation is needed.