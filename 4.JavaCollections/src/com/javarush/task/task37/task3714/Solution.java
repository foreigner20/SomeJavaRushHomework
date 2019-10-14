package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Древний Рим
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
    }

    public static int romanToInteger(String s) {
        char[] stringArray = s.toCharArray();
        int result = 0;

        for (int i = 0; i < stringArray.length; i++) {
            int s1 = getInt(stringArray[i]);
            if(i != stringArray.length - 1){
                int s2 = getInt(stringArray[i + 1]);
                   if(s1 >= s2){
                       result += s1;
                   }else {
                       result += s2 - s1;
                       i++;
                   }
            }else {
                result += s1;
            }
        }
        return result;
    }

    public static int getInt(Character c){
        switch (c) {
            case 'M':
                return 1000;
            case 'D':
                return 500;
            case 'C':
                return 100;
            case 'L':
                return 50;
            case 'X':
                return 10;
            case 'V':
                return 5;
            case 'I':
                return 1;
        }
        return -1;
    }
}
