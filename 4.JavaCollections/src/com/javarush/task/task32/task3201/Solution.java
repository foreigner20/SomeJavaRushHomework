package com.javarush.task.task32.task3201;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
Запись в существующий файл
*/
public class Solution {
    public static void main(String... args) throws IOException {
        RandomAccessFile file = new RandomAccessFile(args[0], "rw");
        if(file.length() < Long.parseLong(args[1])){
            file.seek(file.length());
        }else {
            file.seek(Long.parseLong(args[1]));
        }
        file.write(args[2].getBytes());
        file.close();
    }
}
