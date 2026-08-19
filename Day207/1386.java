/* [**1386. Cinema Seat Allocation**](https://leetcode.com/problems/cinema-seat-allocation/)

A cinema has `n` rows of seats, numbered from 1 to `n`. Each row has 10 seats, numbered from 1 to 10.

You are given a 2D integer array `reservedSeats`, where `reservedSeats[i] = [rowi, seati]` means that seat `seati` in row `rowi` is already reserved.

A four-person group must be assigned to four seats in the **same** row. The group can be seated in one of the following seat blocks:

- seats `2, 3, 4, 5`
- seats `4, 5, 6, 7`
- seats `6, 7, 8, 9`

A block can be used only if **none** of its seats are reserved. Each seat can be assigned to **at most **one group.

Return an integer denoting the **maximum** number of four-person groups that can be assigned.*/

import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        // Store only rows that actually have reservations.
        Map<Integer, Integer> map = new HashMap<>();

        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            // Only seats 2 to 9 affect the answer.
            if (col >= 2 && col <= 9) {
                map.put(row, map.getOrDefault(row, 0) | (1 << col));
            }
        }

        // Every completely empty row can fit 2 groups.
        int ans = (n - map.size()) * 2;

        for (int mask : map.values()) {

            // 2,3,4,5
            int left = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5);

            // 4,5,6,7
            int middle = (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7);

            // 6,7,8,9
            int right = (1 << 6) | (1 << 7) | (1 << 8) | (1 << 9);

            boolean canLeft = (mask & left) == 0;
            boolean canMiddle = (mask & middle) == 0;
            boolean canRight = (mask & right) == 0;

            if (canLeft && canRight) {
                ans += 2;
            } else if (canLeft || canMiddle || canRight) {
                ans += 1;
            }
        }

        return ans;
    }
}
