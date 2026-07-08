/*3756. Concatenate Non-Zero Digits and Multiply by Sum II

You are given a string s of length m consisting of digits. You are also given a 2D integer array queries, where queries[i] = [li, ri].

For each queries[i], extract the substring s[li..ri]. Then, perform the following:

Form a new integer x by concatenating all the non-zero digits from the substring in their original order. If there are no non-zero digits, x = 0.
Let sum be the sum of digits in x. The answer is x * sum.
Return an array of integers answer where answer[i] is the answer to the ith query.

Since the answers may be very large, return them modulo 109 + 7.

 

Example 1:

Input: s = "10203004", queries = [[0,7],[1,3],[4,6]]

Output: [12340, 4, 9]

Explanation:

s[0..7] = "10203004"
x = 1234
sum = 1 + 2 + 3 + 4 = 10
Therefore, answer is 1234 * 10 = 12340.
s[1..3] = "020"
x = 2
sum = 2
Therefore, the answer is 2 * 2 = 4.
s[4..6] = "300"
x = 3
sum = 3
Therefore, the answer is 3 * 3 = 9. */

class Solution {
    static final int MOD = 1_000_000_007;

    public int[] sumAndMultiply(String s, int[][] queries) {
        int n = s.length();

        // Store non-zero positions and digits
        ArrayList<Integer> posList = new ArrayList<>();
        ArrayList<Integer> digitList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c != '0') {
                posList.add(i);
                digitList.add(c - '0');
            }
        }

        int m = posList.size();

        int[] pos = new int[m];
        int[] digit = new int[m];

        for (int i = 0; i < m; i++) {
            pos[i] = posList.get(i);
            digit[i] = digitList.get(i);
        }

        long[] pow10 = new long[m + 1];
        pow10[0] = 1;
        for (int i = 1; i <= m; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        long[] pref = new long[m + 1];
        for (int i = 0; i < m; i++) {
            pref[i + 1] = (pref[i] * 10 + digit[i]) % MOD;
        }

        int[] sumPref = new int[m + 1];
        for (int i = 0; i < m; i++) {
            sumPref[i + 1] = sumPref[i] + digit[i];
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];

            int left = lowerBound(pos, l);
            int right = upperBound(pos, r) - 1;

            if (left > right) {
                ans[i] = 0;
                continue;
            }

            int len = right - left + 1;

            long x = (pref[right + 1] - pref[left] * pow10[len]) % MOD;
            if (x < 0) x += MOD;

            long sum = sumPref[right + 1] - sumPref[left];

            ans[i] = (int) ((x * sum) % MOD);
        }

        return ans;
    }

    private int lowerBound(int[] arr, int target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = (l + r) >>> 1;
            if (arr[mid] < target)
                l = mid + 1;
            else
                r = mid;
        }
        return l;
    }

    private int upperBound(int[] arr, int target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = (l + r) >>> 1;
            if (arr[mid] <= target)
                l = mid + 1;
            else
                r = mid;
        }
        return l;
    }
}