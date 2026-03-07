
/*1888. Minimum Number of Flips to Make the Binary String Alternating
Medium
Topics
premium lock icon
Companies
Hint
You are given a binary string s. You are allowed to perform two 
types of operations on the string in any sequence:

Type-1: Remove the character at the start of the string s and append 
it to the end of the string.
Type-2: Pick any character in s and flip its value, i.e., 
if its value is '0' it becomes '1' and vice-versa.
Return the minimum number of type-2 operations you need to perform 
such that s becomes alternating.

The string is called alternating if no two adjacent characters are equal.

For example, the strings "010" and "1010" are alternating, while the string 
"0100" is not.
 */ 

class Solution { //class
    public int minFlips(String s) { //method function
        int n = s.length(); //length of string
        String str = s + s; //double the string to handle rotations
        int alt1 = 0, alt2 = 0, res = Integer.MAX_VALUE; //counters for mismatches and result
        int l = 0; //left pointer for sliding window

        for (int r = 0; r < str.length(); r++) { //right pointer for sliding window
            if (str.charAt(r) != (r % 2 == 0 ? '0' : '1')) alt1++; //check mismatch with pattern 010101...
            if (str.charAt(r) != (r % 2 == 0 ? '1' : '0')) alt2++; // check mismatch with pattern 101010...

            if (r - l + 1 > n) { //if window size exceeds n, move left pointer
                if (str.charAt(l) != (l % 2 == 0 ? '0' : '1')) alt1--;//remove left character mismatch from alt1
                if (str.charAt(l) != (l % 2 == 0 ? '1' : '0')) alt2--; //remove left character mismatch from alt2
                l++; //move left pointer
            }

            if (r - l + 1 == n) { //when window size is n, calculate minimum flips needed
                res = Math.min(res, Math.min(alt1, alt2)); //update result with minimum of alt1 and alt2
            }
        }

        return res; //return the minimum flips needed
    }
}

/*Let’s visualize the algorithm step-by-step.

Example

s = 111000
n = 6

Goal: make the string alternating.

Valid alternating patterns:

Pattern A: 010101
Pattern B: 101010
1️⃣ Why We Double the String

Operation Type-1 allows rotation:

111000
↓
110001
↓
100011
↓
000111
↓
001110
↓
011100

Instead of rotating manually, we do

str = s + s
111000111000

Now every rotation appears as a window of length 6.

2️⃣ Sliding Window Over s+s
str = 1 1 1 0 0 0 1 1 1 0 0 0
        ← window size = 6 →
Window 1
111000

Compare with

010101
101010

Flips needed:

111000
101010
 ↑   ↑
2 flips
Window 2

Shift window →

110001

Compare

110001
010101
 ↑   ↑
2 flips
Window 3
100011

Compare

100011
101010
   ↑  ↑
2 flips
Window 4
000111

Compare

000111
010101
 ↑ ↑ ↑
3 flips
Window 5
001110
Window 6
011100
3️⃣ Sliding Window Mechanism

Instead of recomputing flips every time:

remove left character
add right character
update mismatch count

Visualization:

111000111000
[111000]
 [110001]
  [100011]
   [000111]
    [001110]
     [011100]

Each bracket = one possible rotation.

4️⃣ Track Two Mismatch Counters

We compare with both patterns simultaneously:

alt1 → mismatch with 010101
alt2 → mismatch with 101010

At each window:

answer = min(answer, min(alt1, alt2))
5️⃣ Final Idea

Algorithm flow:

1. Create s + s
2. Slide window of size n
3. Compare with two alternating patterns
4. Maintain mismatch counts
5. Take minimum flips

Time Complexity

O(n)

Space

O(1)

✅ Core Insight

Rotation problem → convert to

Sliding window on s+s

This trick appears in many advanced problems.
*/