/*2463. Minimum Total Distance Traveled

There are some robots and factories on the X-axis. You are given an integer array robot where robot[i] is the position of the ith robot. You are also given a 2D integer array factory where factory[j] = [positionj, limitj] indicates that positionj is the position of the jth factory and that the jth factory can repair at most limitj robots.

The positions of each robot are unique. The positions of each factory are also unique. Note that a robot can be in the same position as a factory initially.

All the robots are initially broken; they keep moving in one direction. The direction could be the negative or the positive direction of the X-axis. When a robot reaches a factory that did not reach its limit, the factory repairs the robot, and it stops moving.

At any moment, you can set the initial direction of moving for some robot. Your target is to minimize the total distance traveled by all the robots.

Return the minimum total distance traveled by all the robots. The test cases are generated such that all the robots can be repaired.

Note that

All robots move at the same speed.
If two robots move in the same direction, they will never collide.
If two robots move in opposite directions and they meet at some point, they do not collide. They cross each other.
If a robot passes by a factory that reached its limits, it crosses it as if it does not exist.
If the robot moved from a position x to a position y, the distance it moved is |y - x|.
 */


import java.util.*;

class Solution {
    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        Collections.sort(robot);
        Arrays.sort(factory, (a, b) -> a[0] - b[0]);

        int n = robot.size();
        int m = factory.length;

        long[] dp = new long[n + 1];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = 0;

        for (int j = 0; j < m; j++) {
            int pos = factory[j][0];
            int limit = factory[j][1];

            long[] newDp = new long[n + 1];
            Arrays.fill(newDp, Long.MAX_VALUE);

            for (int i = 0; i <= n; i++) {
                if (dp[i] == Long.MAX_VALUE) continue;

                long cost = 0;

                for (int k = 0; k <= limit && i + k <= n; k++) {
                    if (k > 0) {
                        cost += Math.abs(robot.get(i + k - 1) - pos);
                    }
                    newDp[i + k] = Math.min(newDp[i + k], dp[i] + cost);
                }
            }
            dp = newDp;
        }

        return dp[n];
    }
}