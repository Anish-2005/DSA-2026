//1200. Minimum Absolute Difference

//Given an array of distinct integers arr, find all pairs of elements with the minimum absolute difference of any two elements.

//Return a list of pairs in ascending order(with respect to pairs), each pair [a, b] follows

//a, b are from arr
//a < b
//b - a equals to the minimum absolute difference of any two elements in arr
 
class Solution {
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        List<List<Integer>> result = new ArrayList<>();
        int minDiff = Integer.MAX_VALUE;

        for (int i = 1; i < arr.length; i++) {
            minDiff = Math.min(minDiff, arr[i] - arr[i - 1]);
        }
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - arr[i - 1] == minDiff) {
                result.add(Arrays.asList(arr[i - 1], arr[i]));
            }
        }
        return result;
    }
}

