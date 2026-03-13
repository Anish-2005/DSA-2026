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
    // Main function to find the minimum seconds required to reduce mountain 
    // height to zero
    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {
        // 'left' is the minimum possible time (0 seconds)
        // 'right' is a safe upper bound for the maximum possible time needed (1e18 seconds)

        // 'ans' stores the minimum valid time found so far, initialized to 'right'
        long left = 0, right = (long)1e18, ans = right;

        // Binary search on the total time range [left, right]
        while (left <= right) {
            // Calculate the middle point 'mid' as the current candidate for minimum time
            long mid = (left + right) / 2;

            // Check if it's possible to reduce the mountain height to 0 within 'mid' seconds
            if (can(mid, mountainHeight, workerTimes)) {
                // If possible, save 'mid' as a potential answer
                ans = mid;
                // Try searching for a smaller time in the left half
                right = mid - 1;
            } else {
                // If not possible, we need more time, so search in the right half
                left = mid + 1;
            }
        }

        // Return the minimum successful time identified
        return ans;
    }

    // Helper function to check if the total work done by all workers in 'time' 
    // seconds is >= 'h'
    boolean can(long time, int h, int[] w) {
        // 'total' keeps track of the cumulative height reduction across all workers
        long total = 0;

        // Iterate through each worker's base work time 't'
        for (int t : w) {
            // For each worker, use binary search to find the maximum height 'm' they 
            // can reduce
            // 'l' starts at 0, 'r' is a reasonable max height a single worker might
            //  handle
            long l = 0, r = (long)1e6;

            while (l <= r) {
                // 'm' is the candidate height reduction for this specific worker
                long m = (l + r) / 2;
                // Formula for time taken to reduce height by 'm': t * (1 + 2 + ... + m)
                // which simplifies to t * (m * (m + 1) / 2)
                long need = (long)t * m * (m + 1) / 2;

                // Check if the time 'need' is within the allowed 'time' limit
                if (need <= time) {
                    // If worker can finish, try finding an even larger height 
                    // reduction
                    l = m + 1;
                } else {
                    // Otherwise, reduce the target height
                    r = m - 1;
                }
            }

            // 'r' is the maximum height this worker can contribute within 'time'
            total += r;
            // Early exit if the mountain height is already covered by the workers 
            // processed so far
            if (total >= h) return true;
        }

        // Return false if all workers combined cannot reduce the mountain height to 0
        return false;
    }
}

/*
VISUALIZATION OF LOGIC:

1. GLOBAL BINARY SEARCH (Outer Loop):
   - We search for the 'Minimum Time' (T) in the range [0 to 1e18].
   - For any time T, if we can reduce the mountain height, we try a smaller T.
   - If we can't, we look for a larger T.

2. WORKER CAPABILITY CHECK (Inner Loop in 'can' function):
   - For a fixed Time T, we check the maximum work each worker can do.
   - For a worker with base time W:
     - 1st unit takes: W * 1
     - 2nd unit takes: W * 2 (Total: W * 3)
     - 3rd unit takes: W * 3 (Total: W * 6)
     - X-th unit takes: W * X (Total: W * X*(X+1)/2)
   - We find the largest X such that [W * X * (X + 1) / 2] <= T.

3. LOCAL BINARY SEARCH (Inside 'can' function):
   - Finding X can be slow, so we use ANOTHER binary search to find X for each worker.
   - Range for X: [0 to 1,000,000].

4. SUCCESS CONDITION:
   - Sum of all largest X for all workers >= mountainHeight.

EXAMPLE: mountainHeight = 4, workerTimes = [1]
   - Time = 1: Work = 1 unit (1*1*(2)/2 = 1 <= 1)
   - Time = 3: Work = 2 units (1*2*(3)/2 = 3 <= 3)
   - Time = 6: Work = 3 units (1*3*(4)/2 = 6 <= 6)
   - Time = 10: Work = 4 units (1*4*(5)/2 = 10 <= 10) -> DONE!
*/