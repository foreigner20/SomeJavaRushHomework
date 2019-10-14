package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i =0; i<3; i++) {
            byteArrayOutputStream.write((byte) (97 + Math.random() * 26));
        }
        for (int i =3; i<5; i++){
            byteArrayOutputStream.write((byte) (65 + Math.random() * 26));
        }
        for (int i =5; i<8; i++){
            byteArrayOutputStream.write(48 + (byte)(Math.random()*9));
        }
        return byteArrayOutputStream;
    }
}