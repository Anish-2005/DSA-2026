/*1622. Fancy Sequence

Write an API that generates fancy sequences using the append, 
addAll, and multAll operations.

Implement the Fancy class:

Fancy() Initializes the object with an empty sequence.
void append(val) Appends an integer val to the end of the sequence.
void addAll(inc) Increments all existing values in the sequence by 
an integer inc.
void multAll(m) Multiplies all existing values in the sequence by 
an integer m.
int getIndex(idx) Gets the current value at index idx (0-indexed) 
of the sequence modulo 109 + 7. If the index is greater or equal 
than the length of the sequence, return -1.
 

Example 1:

Input
["Fancy", "append", "addAll", "append", "multAll", "getIndex", 
"addAll", "append", "multAll", "getIndex", "getIndex", "getIndex"]
[[], [2], [3], [7], [2], [0], [3], [10], [2], [0], [1], [2]]
Output
[null, null, null, null, null, 10, null, null, null, 26, 34, 20]

Explanation
Fancy fancy = new Fancy();
fancy.append(2);   // fancy sequence: [2]
fancy.addAll(3);   // fancy sequence: [2+3] -> [5]
fancy.append(7);   // fancy sequence: [5, 7]
fancy.multAll(2);  // fancy sequence: [5*2, 7*2] -> [10, 14]
fancy.getIndex(0); // return 10
fancy.addAll(3);   // fancy sequence: [10+3, 14+3] -> [13, 17]
fancy.append(10);  // fancy sequence: [13, 17, 10]
fancy.multAll(2);  // fancy sequence: [13*2, 17*2, 10*2] -> [26, 34, 20]
fancy.getIndex(0); // return 26
fancy.getIndex(1); // return 34
fancy.getIndex(2); // return 20
 

Constraints:

1 <= val, inc, m <= 100
0 <= idx <= 105
At most 105 calls total will be made to append, addAll, multAll, and 
getIndex.
*/

import java.util.*; // Importing the utility package for List and ArrayList

class Fancy {
    static final long MOD = 1000000007; // Modulo value for all operations
    List<Long> arr = new ArrayList<>(); // Stores the sequence in a transformed way
    long mul = 1, add = 0; // Cumulative multiplication and addition factors

    public Fancy() {} // Constructor initializes everything

    // Appends a value to the sequence
    public void append(int val) {
        long inv = modInverse(mul); // Find modular inverse of current mul
        // Undo the effect of all previous add and mul to store the base value
        long x = ((val - add) % MOD + MOD) % MOD; // Remove the effect of add
        x = (x * inv) % MOD; // Remove the effect of mul
        arr.add(x); // Store the transformed value
    }

    // Increments all values in the sequence by inc
    public void addAll(int inc) {
        add = (add + inc) % MOD; // Just update the cumulative add
    }

    // Multiplies all values in the sequence by m
    public void multAll(int m) {
        mul = (mul * m) % MOD; // Update cumulative mul
        add = (add * m) % MOD; // Scale the add as well
    }

    // Gets the value at index idx after all operations
    public int getIndex(int idx) {
        if (idx >= arr.size()) return -1; // Out of bounds
        long val = arr.get(idx); // Get the stored base value
        // Apply all cumulative operations to get the real value
        return (int)((val * mul + add) % MOD);
    }

    // Computes modular inverse using Fermat's little theorem
    long modInverse(long x) {
        return pow(x, MOD - 2);
    }

    // Fast exponentiation (a^b % MOD)
    long pow(long a, long b) {
        long res = 1;
        a %= MOD;
        while (b > 0) {
            if ((b & 1) == 1) res = (res * a) % MOD; // If b is odd, multiply result
            a = (a * a) % MOD; // Square a
            b >>= 1; // Divide b by 2
        }
        return res;
    }
}

/*
Visualization:

Suppose we perform the following operations:
Fancy fancy = new Fancy();
fancy.append(2);   // [2]
fancy.addAll(3);   // [5]
fancy.append(7);   // [5, 7]
fancy.multAll(2);  // [10, 14]
fancy.getIndex(0); // 10
fancy.addAll(3);   // [13, 17]
fancy.append(10);  // [13, 17, 10]
fancy.multAll(2);  // [26, 34, 20]
fancy.getIndex(0); // 26
fancy.getIndex(1); // 34
fancy.getIndex(2); // 20

At each step, the sequence is transformed as follows:
1. append(2): [2]
2. addAll(3): [2+3] = [5]
3. append(7): [5, 7]
4. multAll(2): [5*2, 7*2] = [10, 14]
5. getIndex(0): 10
6. addAll(3): [10+3, 14+3] = [13, 17]
7. append(10): [13, 17, 10]
8. multAll(2): [13*2, 17*2, 10*2] = [26, 34, 20]
9. getIndex(0): 26
10. getIndex(1): 34
11. getIndex(2): 20

Internally, the class stores values in a way that allows all addAll and multAll to be applied lazily, so each append only stores the value after undoing all previous operations, and getIndex applies all operations up to the current point.
*/