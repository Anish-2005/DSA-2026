/*3296. Minimum Number of Seconds to Make Mountain Height Zero

You are given an integer mountainHeight denoting the height of a mountain.

You are also given an integer array workerTimes representing the work time 
of workers in seconds.

The workers work simultaneously to reduce the height of the mountain. 
For worker i:

To decrease the mountain's height by x, 
it takes workerTimes[i] + workerTimes[i] * 2 + ... + workerTimes[i] * x seconds. 
For example:
To reduce the height of the mountain by 1, it takes workerTimes[i] seconds.
To reduce the height of the mountain by 2, it takes workerTimes[i] + 
workerTimes[i] * 2 seconds, and so on.
Return an integer representing the minimum number of seconds required for the workers 
to make the height of the mountain 0. */

class Solution {
    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {
        long left = 0, right = (long)1e18, ans = right;

        while (left <= right) {
            long mid = (left + right) / 2;

            if (can(mid, mountainHeight, workerTimes)) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    boolean can(long time, int h, int[] w) {
        long total = 0;

        for (int t : w) {
            long l = 0, r = (long)1e6;

            while (l <= r) {
                long m = (l + r) / 2;
                long need = (long)t * m * (m + 1) / 2;

                if (need <= time) l = m + 1;
                else r = m - 1;
            }

            total += r;
            if (total >= h) return true;
        }

        return false;
    }
}