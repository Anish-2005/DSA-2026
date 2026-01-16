//2975. Maximum Square Area by Removing Fences From a Field

//There is a large (m - 1) x (n - 1) rectangular field with 
// corners at (1, 1) and (m, n) containing some horizontal and 
// vertical fences given in arrays hFences and vFences respectively.

//Horizontal fences are from the coordinates (hFences[i], 1) to
//  (hFences[i], n) and vertical fences are from the coordinates (1, vFences[i]) to (m, vFences[i]).

//Return the maximum area of a square field that can be formed by 
// removing some fences (possibly none) or -1 if it is impossible to make a square field.

//Since the answer may be large, return it modulo 109 + 7.

//Note: The field is surrounded by two horizontal fences from the 
// coordinates (1, 1) to (1, n) and (m, 1) to (m, n) and two vertical 
// fences from the coordinates (1, 1) to (m, 1) and (1, n) to (m, n). 
// These fences cannot be removed.

 


import java.util.*;

class Solution {
    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        final long MOD = 1000000007L;

        long[] h = new long[hFences.length + 2];
        long[] v = new long[vFences.length + 2];

        h[0] = 1;
        h[h.length - 1] = m;
        for (int i = 0; i < hFences.length; i++) h[i + 1] = hFences[i];

        v[0] = 1;
        v[v.length - 1] = n;
        for (int i = 0; i < vFences.length; i++) v[i + 1] = vFences[i];

        Arrays.sort(h);
        Arrays.sort(v);

        Set<Long> hDiff = new HashSet<>();
        for (int i = 0; i < h.length; i++) {
            for (int j = i + 1; j < h.length; j++) {
                hDiff.add(h[j] - h[i]);
            }
        }

        long maxSide = -1;
        for (int i = 0; i < v.length; i++) {
            for (int j = i + 1; j < v.length; j++) {
                long d = v[j] - v[i];
                if (hDiff.contains(d)) {
                    maxSide = Math.max(maxSide, d);
                }
            }
        }

        if (maxSide == -1) return -1;
        return (int) ((maxSide * maxSide) % MOD);
    }
}
