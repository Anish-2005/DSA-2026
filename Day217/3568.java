/*3568. Minimum Moves to Clean the Classroom

You are given an m x n grid classroom where a student volunteer is tasked with cleaning up litter scattered around the room. Each cell in the grid is one of the following:

'S': Starting position of the student
'L': Litter that must be collected (once collected, the cell becomes empty)
'R': Reset area that restores the student's energy to full capacity, regardless of their current energy level (can be used multiple times)
'X': Obstacle the student cannot pass through
'.': Empty space
You are also given an integer energy, representing the student's maximum energy capacity. The student starts with this energy from the starting position 'S'.

Each move to an adjacent cell (up, down, left, or right) costs 1 unit of energy. If the energy reaches 0, the student can only continue if they are on a reset area 'R', which resets the energy to its maximum capacity energy.

Return the minimum number of moves required to collect all litter items, or -1 if it's impossible.

 

Example 1:

Input: classroom = ["S.", "XL"], energy = 2

Output: 2

Explanation:

The student starts at cell (0, 0) with 2 units of energy.
Since cell (1, 0) contains an obstacle 'X', the student cannot move directly downward.
A valid sequence of moves to collect all litter is as follows:
Move 1: From (0, 0) → (0, 1) with 1 unit of energy and 1 unit remaining.
Move 2: From (0, 1) → (1, 1) to collect the litter 'L'.
The student collects all the litter using 2 moves. Thus, the output is 2. */

import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int startR = 0, startC = 0;
        Map<String, Integer> litterMap = new HashMap<>();

        // Find S and assign index to every L
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                char ch = classroom[r].charAt(c);

                if (ch == 'S') {
                    startR = r;
                    startC = c;
                } else if (ch == 'L') {
                    litterMap.put(r + "," + c, litterMap.size());
                }
            }
        }

        int totalLitter = litterMap.size();

        // All litter collected
        int targetMask = (1 << totalLitter) - 1;

        /*
         * State:
         * row, col, mask, remainingEnergy, moves
         */
        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{
            startR, startC, 0, energy, 0
        });

        /*
         * visited[row][col][mask] = maximum energy
         * with which we have reached this state.
         */
        int[][][] visited = new int[m][n][1 << totalLitter];

        for (int[][] arr : visited) {
            for (int[] row : arr) {
                Arrays.fill(row, -1);
            }
        }

        visited[startR][startC][0] = energy;

        int[][] directions = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
        };

        while (!queue.isEmpty()) {

            int[] current = queue.poll();

            int r = current[0];
            int c = current[1];
            int mask = current[2];
            int currEnergy = current[3];
            int moves = current[4];

            // All litter collected
            if (mask == targetMask) {
                return moves;
            }

            // Cannot move with zero energy
            if (currEnergy == 0) {
                continue;
            }

            for (int[] dir : directions) {

                int nr = r + dir[0];
                int nc = c + dir[1];

                // Outside grid
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                // Obstacle
                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                // Moving costs 1 energy
                int newEnergy = currEnergy - 1;

                int newMask = mask;

                // Collect litter
                if (classroom[nr].charAt(nc) == 'L') {
                    int index = litterMap.get(nr + "," + nc);
                    newMask |= (1 << index);
                }

                // Reset area restores energy
                if (classroom[nr].charAt(nc) == 'R') {
                    newEnergy = energy;
                }

                /*
                 * If we've already reached the same
                 * (position + litterMask) with MORE energy,
                 * this state is useless.
                 */
                if (visited[nr][nc][newMask] >= newEnergy) {
                    continue;
                }

                visited[nr][nc][newMask] = newEnergy;

                queue.offer(new int[]{
                    nr,
                    nc,
                    newMask,
                    newEnergy,
                    moves + 1
                });
            }
        }

        return -1;
    }
}