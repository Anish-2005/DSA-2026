/*2573. Find the String with LCP

We define the lcp matrix of any 0-indexed string word of n lowercase English 
letters as an n x n grid such that:

lcp[i][j] is equal to the length of the longest common prefix between the 
substrings word[i,n-1] and word[j,n-1].
Given an n x n matrix lcp, return the alphabetically smallest string word 
that corresponds to lcp. If there is no such string, return an empty string.

A string a is lexicographically smaller than a string b (of the same length) 
if in the first position where a and b differ, string a has a letter that 
appears earlier in the alphabet than the corresponding letter in b. For example,
 "aabd" is lexicographically smaller than "aaca" because the first position they
  differ is at the third letter, and 'b' comes before 'c'. */

class Solution {

    public String findTheString(int[][] lcp) {

        int n = lcp.length;

        for(int i=0;i<n;i++){
            if(lcp[i][i] != n-i) return "";
            for(int j=0;j<n;j++){
                if(lcp[i][j] != lcp[j][i]) return "";
            }
        }

        char[] res = new char[n];
        for(int i=0;i<n;i++) res[i] = '#';

        char cur = 'a';

        for(int i=0;i<n;i++){

            if(res[i] != '#') continue;

            if(cur > 'z') return "";

            for(int j=i;j<n;j++){
                if(lcp[i][j] > 0)
                    res[j] = cur;
            }

            cur++;
        }

        int[][] check = new int[n+1][n+1];

        for(int i=n-1;i>=0;i--){
            for(int j=n-1;j>=0;j--){
                if(res[i] == res[j])
                    check[i][j] = check[i+1][j+1] + 1;
            }
        }

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(check[i][j] != lcp[i][j])
                    return "";
            }
        }

        return new String(res);
    }
}