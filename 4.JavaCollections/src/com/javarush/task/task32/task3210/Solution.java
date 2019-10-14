package com.javarush.task.task32.task3210;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException {
        RandomAccessFile file = new RandomAccessFile(args[0], "rw");
        file.seek(Long.parseLong(args[1]));

        byte[] b = new byte[args[2].length()];

        file.read(b, 0, args[2].length());

        file.seek(file.length());

        String textFromFile = new String(b);
        String f;
        if(textFromFile.equals(args[2])){
            f = "true";
            file.write(f.getBytes());
        }else {
            f = "false";
            file.write(f.getBytes());
        }
        file.close();
    }
}
