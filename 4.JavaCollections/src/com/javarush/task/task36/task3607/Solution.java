package com.javarush.task.task36.task3607;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.PriorityBlockingQueue;

/*
Найти класс по описанию
*/
public class Solution{
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
//        Class<?>[] arrayOfClasses = Collections.class.getDeclaredClasses();
//
//        return Collections.EMPTY_LIST.getClass();
//        Queue<Integer> queue = new PriorityQueue<>();

        return DelayQueue.class;
    }
}
