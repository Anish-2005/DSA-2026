/*
3635. Earliest Finish Time for Land and Water Rides II

You are given two categories of theme park attractions: land rides and water rides.

Land rides
landStartTime[i] – the earliest time the ith land ride can be boarded.
landDuration[i] – how long the ith land ride lasts.
Water rides
waterStartTime[j] – the earliest time the jth water ride can be boarded.
waterDuration[j] – how long the jth water ride lasts.
A tourist must experience exactly one ride from each category, in either order.

A ride may be started at its opening time or any later moment.
If a ride is started at time t, it finishes at time t + duration.
Immediately after finishing one ride the tourist may board the other (if it is already open) or wait until it opens.
Return the earliest possible time at which the tourist can finish both rides.

 

Example 1:

Input: landStartTime = [2,8], landDuration = [4,1], waterStartTime = [6], waterDuration = [3]

Output: 9

Explanation:​​​​​​​

Plan A (land ride 0 → water ride 0):
Start land ride 0 at time landStartTime[0] = 2. Finish at 2 + landDuration[0] = 6.
Water ride 0 opens at time waterStartTime[0] = 6. Start immediately at 6, finish at 6 + waterDuration[0] = 9.
Plan B (water ride 0 → land ride 1):
Start water ride 0 at time waterStartTime[0] = 6. Finish at 6 + waterDuration[0] = 9.
Land ride 1 opens at landStartTime[1] = 8. Start at time 9, finish at 9 + landDuration[1] = 10.
Plan C (land ride 1 → water ride 0):
Start land ride 1 at time landStartTime[1] = 8. Finish at 8 + landDuration[1] = 9.
Water ride 0 opened at waterStartTime[0] = 6. Start at time 9, finish at 9 + waterDuration[0] = 12.
Plan D (water ride 0 → land ride 0):
Start water ride 0 at time waterStartTime[0] = 6. Finish at 6 + waterDuration[0] = 9.
Land ride 0 opened at landStartTime[0] = 2. Start at time 9, finish at 9 + landDuration[0] = 13.
Plan A gives the earliest finish time of 9.
 */

import java.util.*;

class Solution {

    public int earliestFinishTime(int[] landStartTime, int[] landDuration,
                                  int[] waterStartTime, int[] waterDuration) {

        return Math.min(
                solve(landStartTime, landDuration,
                        waterStartTime, waterDuration),

                solve(waterStartTime, waterDuration,
                        landStartTime, landDuration)
        );
    }

    private int solve(int[] firstStart, int[] firstDur,
                      int[] secondStart, int[] secondDur) {

        int m = secondStart.length;

        int[][] rides = new int[m][2];

        for (int i = 0; i < m; i++) {
            rides[i][0] = secondStart[i];
            rides[i][1] = secondDur[i];
        }

        Arrays.sort(rides, Comparator.comparingInt(a -> a[0]));

        int[] suffix = new int[m + 1];
        Arrays.fill(suffix, Integer.MAX_VALUE);

        for (int i = m - 1; i >= 0; i--) {
            suffix[i] = Math.min(
                    suffix[i + 1],
                    rides[i][0] + rides[i][1]
            );
        }

        int[] prefix = new int[m];
        prefix[0] = rides[0][1];

        for (int i = 1; i < m; i++) {
            prefix[i] = Math.min(prefix[i - 1], rides[i][1]);
        }

        int ans = Integer.MAX_VALUE;

        for (int i = 0; i < firstStart.length; i++) {

            int finish = firstStart[i] + firstDur[i];

            int idx = lowerBound(rides, finish);

            if (idx < m) {
                ans = Math.min(ans, suffix[idx]);
            }

            if (idx > 0) {
                ans = Math.min(ans, finish + prefix[idx - 1]);
            }
        }

        return ans;
    }

    private int lowerBound(int[][] rides, int target) {

        int l = 0, r = rides.length;

        while (l < r) {

            int mid = (l + r) / 2;

            if (rides[mid][0] < target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }
}