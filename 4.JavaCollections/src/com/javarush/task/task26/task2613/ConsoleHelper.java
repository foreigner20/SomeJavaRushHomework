package com.javarush.task.task26.task2613;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return bis.readLine();
    }

    public static String askCurrencyCode() throws IOException{
        while (true) {
            System.out.print("Enter the currency code: ");
            String result = bis.readLine();
            if(result.length() == 3){
                return result.toUpperCase();
            }
            System.out.println("Wrong currency code!");
        }
    }

    public static String[] getValidTwoDigits(String currencyCode) throws IOException {
        String[] cash;
        while (true) {
            writeMessage("Пожалуйста введите два целых положительных числа!");

            try {
                cash = readString().split(" ");
                if (Integer.parseInt(cash[0]) > 0 && Integer.parseInt(cash[1]) > 0 && cash.length==2) break;
            } catch (Exception e) {
                writeMessage("Код должен содержать 2 положительных числа!");
                continue;
            }
            writeMessage("Код должен содержать 2 положительных числа!");

        }
        return cash;

    }
}
