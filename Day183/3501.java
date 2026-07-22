/*3501. Maximize Active Section with Trade II
Hard
Topics
premium lock icon
Companies
Hint
You are given a binary string s of length n, where:

'1' represents an active section.
'0' represents an inactive section.
You can perform at most one trade to maximize the number of active sections in s. In a trade, you:

Convert a contiguous block of '1's that is surrounded by '0's to all '0's.
Afterward, convert a contiguous block of '0's that is surrounded by '1's to all '1's.
Additionally, you are given a 2D array queries, 
where queries[i] = [li, ri] represents a substring s[li...ri].

For each query, determine the maximum possible number of active sections in s 
after making the optimal trade on the substring s[li...ri].

Return an array answer, where answer[i] is the result for queries[i].

Note

For each query, treat s[li...ri] as if it is augmented with a '1' at both ends, 
forming t = '1' + s[li...ri] + '1'. The augmented '1's do not contribute to the final count.
The queries are independent of each other.
 

Example 1:

Input: s = "01", queries = [[0,1]]

Output: [1]

Explanation:

Because there is no block of '1's surrounded by '0's, no valid trade is possible. 
The maximum number of active sections is 1. */

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {

        List<Integer> ans = new ArrayList<>();

        for (int q = 0; q < queries.length; q++) {

            int l = queries[q][0];
            int r = queries[q][1];

            String sub = s.substring(l, r + 1);

            int ones = 0;
            for (char c : sub.toCharArray())
                if (c == '1')
                    ones++;

            String t = "1" + sub + "1";

            ArrayList<Character> type = new ArrayList<>();
            ArrayList<Integer> len = new ArrayList<>();

            int i = 0;

            while (i < t.length()) {
                char c = t.charAt(i);
                int j = i;

                while (j < t.length() && t.charAt(j) == c)
                    j++;

                type.add(c);
                len.add(j - i);

                i = j;
            }

            int best = ones;

            for (int k = 1; k + 1 < type.size(); k++) {
                if (type.get(k) == '1'
                        && type.get(k - 1) == '0'
                        && type.get(k + 1) == '0') {

                    int candidate = ones + len.get(k - 1) + len.get(k + 1);
                    best = Math.max(best, candidate);
                }
            }

            ans.add(best);
        }

        return ans;
    }
}