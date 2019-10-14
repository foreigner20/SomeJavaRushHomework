package com.javarush.task.task33.task3310;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;

public class Helper {

    public static String generateRandomString(){
        return new BigInteger(130, new SecureRandom()).toString(36);
    }

    public static void printMessage(String message){
        System.out.println(message);
    }
}
