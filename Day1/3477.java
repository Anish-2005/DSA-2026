//3477. Fruits Into Baskets II
//You are given two arrays of integers, fruits and baskets, each of length n, where fruits[i] represents the quantity of the ith type of fruit, and baskets[j] represents the capacity of the jth basket.

//From left to right, place the fruits according to these rules:

//Each fruit type must be placed in the leftmost available basket with a capacity greater than or equal to the quantity of that fruit type.
//Each basket can hold only one type of fruit.
//If a fruit type cannot be placed in any basket, it remains unplaced.
//Return the number of fruit types that remain unplaced after all possible allocations are made.

class Solution {
    public int numOfUnplacedFruits(int[] fruits, int[] baskets) {
        int i=0;
        int count=0;
        while(i<fruits.length){
            int f = fruits[i];
            int j=0;
            while(j<baskets.length && baskets[j]<f){
                j++;
            }
            if(j<baskets.length){
                baskets[j]=-1;
            }else{
                count++;
            }
            i++;
        }
        return count;
    }

    private void print(String s, Object... args){
        System.out.println(String.format(s, args));
    }
}