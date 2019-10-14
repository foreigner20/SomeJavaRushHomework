package com.javarush.task.task38.task3804;

/* 
Фабрика исключений
*/

public class Solution {
    public static Class getFactoryClass() {
        return new ExceptionFactory().getClass();
    }

    public static void main(String[] args) {

    }
}