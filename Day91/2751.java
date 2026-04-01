/*2751. Robot Collisions
Solved
Hard
Topics
premium lock icon
Companies
Hint
There are n 1-indexed robots, each having a position on a line,
 health, and movement direction.

You are given 0-indexed integer arrays positions, healths, and a 
string directions (directions[i] is either 'L' for left or 'R' for right).
 All integers in positions are unique.

All robots start moving on the line simultaneously at the same speed in 
their given directions. If two robots ever share the same position while 
moving, they will collide.

If two robots collide, the robot with lower health is removed from the line,
 and the health of the other robot decreases by one. The surviving robot 
 continues in the same direction it was going. If both robots have the same 
 health, they are both removed from the line.

Your task is to determine the health of the robots that survive the collisions, 
in the same order that the robots were given, i.e. final health of robot 1 
(if survived), final health of robot 2 (if survived), and so on. If there 
are no survivors, return an empty array.

Return an array containing the health of the remaining robots (in the order 
    they were given in the input), after no further collisions can occur.

Note: The positions may be unsorted. */

import java.util.*;

class Solution {

    static class Robot {
        int pos, health, idx;
        char dir;

        Robot(int p, int h, char d, int i) {
            pos = p;
            health = h;
            dir = d;
            idx = i;
        }
    }

    public List<Integer> survivedRobotsHealths(int[] positions, int[] healths, String directions) {

        int n = positions.length;

        Robot[] robots = new Robot[n];
        for (int i = 0; i < n; i++) {
            robots[i] = new Robot(positions[i], healths[i], directions.charAt(i), i);
        }

        Arrays.sort(robots, (a,b)->a.pos-b.pos);

        Stack<Robot> stack = new Stack<>();
        List<Robot> survivors = new ArrayList<>();

        for (Robot r : robots) {

            if (r.dir == 'R') {
                stack.push(r);
            } 
            else {

                while (!stack.isEmpty() && r.health > 0) {

                    Robot top = stack.peek();

                    if (top.health < r.health) {
                        stack.pop();
                        r.health--;
                    }
                    else if (top.health > r.health) {
                        top.health--;
                        r.health = 0;
                    }
                    else {
                        stack.pop();
                        r.health = 0;
                    }
                }

                if (r.health > 0) {
                    survivors.add(r);
                }
            }
        }

        survivors.addAll(stack);

        survivors.sort((a,b)->a.idx-b.idx);

        List<Integer> ans = new ArrayList<>();
        for (Robot r : survivors) ans.add(r.health);

        return ans;
    }
}

 