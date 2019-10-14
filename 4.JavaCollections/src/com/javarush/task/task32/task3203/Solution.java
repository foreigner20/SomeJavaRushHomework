package com.javarush.task.task32.task3203;

import java.io.PrintWriter;
import java.io.StringWriter;

/*
Пишем стек-трейс
*/
public class Solution {
    public static void main(String[] args) {
        String text = getStackTrace(new IndexOutOfBoundsException("fff"));
        System.out.println(text);
    }

    public static String getStackTrace(Throwable throwable) {
//        StackTraceElement[] list = throwable.getStackTrace();
//        for(int i = 0; i < list.length; i++){
//            if (i != list.length - 1) {
//                stringWriter.append(list[i].toString());
//                stringWriter.append(" ");
//            }else {
//                stringWriter.append(list[i].toString());
//            }
//        }
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}