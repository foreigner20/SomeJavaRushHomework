package com.javarush.task.task38.task3803;

/* 
Runtime исключения (unchecked exception)
*/

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;

public class VeryComplexClass {
    public void methodThrowsClassCastException() {
        Object x = 0;
        System.out.println((String) x);
    }

    public void methodThrowsNullPointerException() {
        Object obj = null;
        obj.hashCode();
    }

    public static void main(String[] args) {

    }
}
