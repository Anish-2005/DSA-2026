/*3069. Distribute Elements Into Two Arrays I
Easy
Topics
premium lock icon
Companies
Hint
You are given a 1-indexed array of distinct integers nums of length n.

You need to distribute all the elements of nums between two arrays arr1 and arr2 using n operations. In the first operation, append nums[1] to arr1. In the second operation, append nums[2] to arr2. Afterwards, in the ith operation:

If the last element of arr1 is greater than the last element of arr2, append nums[i] to arr1. Otherwise, append nums[i] to arr2.
The array result is formed by concatenating the arrays arr1 and arr2. For example, if arr1 == [1,2,3] and arr2 == [4,5,6], then result = [1,2,3,4,5,6].

Return the array result.

 */

class Solution {
    public int[] resultArray(int[] nums) {
        int n = nums.length;

        int[] arr1 = new int[n];
        int[] arr2 = new int[n];

        int i1 = 0, i2 = 0;

        arr1[i1++] = nums[0];
        arr2[i2++] = nums[1];

        for (int i = 2; i < n; i++) {
            if (arr1[i1 - 1] > arr2[i2 - 1]) {
                arr1[i1++] = nums[i];
            } else {
                arr2[i2++] = nums[i];
            }
        }

        int[] result = new int[n];
        int k = 0;

        for (int i = 0; i < i1; i++) {
            result[k++] = arr1[i];
        }

        for (int i = 0; i < i2; i++) {
            result[k++] = arr2[i];
        }

        return result;
    }
}