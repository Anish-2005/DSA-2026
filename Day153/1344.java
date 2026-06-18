/*
1344. Angle Between Hands of a Clock

Given two numbers, hour and minutes, return the smaller angle (in degrees) formed between the hour and the minute hand.

Answers within 10-5 of the actual value will be accepted as correct.

 

Example 1:


Input: hour = 12, minutes = 30
Output: 165
Example 2:


Input: hour = 3, minutes = 30
Output: 75
*/

class Solution {
    public double angleClock(int hour, int minutes) {
        double minuteAngle = 6.0 * minutes; // 360 / 60
        double hourAngle = 30.0 * (hour % 12) + 0.5 * minutes; // 360 / 12 + minute adjustment

        double diff = Math.abs(hourAngle - minuteAngle);

        return Math.min(diff, 360.0 - diff);
    }
}