//1461. Check If a String Contains All Binary Codes of Size K

//Given a binary string s and an integer k, return true if every binary code of length k is a substring of s. Otherwise, return false.

import java.util.HashSet;

class Solution {
    public boolean hasAllCodes(String s, int k) {
        int totalCodes = 1 << k; // 2^k
        
        if (s.length() < k) return false;

        HashSet<String> set = new HashSet<>();

        for (int i = 0; i <= s.length() - k; i++) {
            String sub = s.substring(i, i + k);
            set.add(sub);
            
            if (set.size() == totalCodes) {
                return true;
            }
        }

        return false;
    }
}