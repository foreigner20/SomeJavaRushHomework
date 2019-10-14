package com.javarush.task.task39.task3904;

import java.util.Arrays;
import java.util.HashMap;

/* 
Лестница
*/
public class Solution {
    private static int n = 10;

    static HashMap<Integer, Long> map = new HashMap<>();
    static {
        map.put(0, 1L);
        map.put(1, 1L);
        map.put(2, 2L);
        map.put(3, 4L);
    }
    public static void main(String[] args) {
        System.out.println("The number of possible ascents for " + n + " steps is: " + numberOfPossibleAscents(n));
    }

    public static long numberOfPossibleAscents(int n) {
        if (n < 0)
            return 0;

        if (map.containsKey(n)) {
            return map.get(n);
        } else {
            long fibonacciValue = ((numberOfPossibleAscents(n - 3) + numberOfPossibleAscents(n - 2))  + numberOfPossibleAscents(n - 1));
            map.put(n, fibonacciValue);
            return fibonacciValue;
        }
    }

}

