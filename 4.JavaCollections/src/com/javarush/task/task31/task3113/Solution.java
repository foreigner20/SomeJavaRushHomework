package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/* 
Что внутри папки?
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Path path = Paths.get(reader.readLine());
        if (Files.isDirectory(path)) {
            MyVisitor visitor = new MyVisitor();
            Files.walkFileTree(path, visitor);

            System.out.println("Всего папок - " + (visitor.countOfDir - 1));
            System.out.println("Всего файлов - " + visitor.countOfFiles);
            System.out.println("Общий размер - " + visitor.allSize);
        }else {
            System.out.println(path + " - не папка");
        }
    }

     public static class MyVisitor extends SimpleFileVisitor<Path>{
        int countOfDir = 0;
        int countOfFiles = 0;
        int allSize = 0;

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (Files.isDirectory(file)) {
                countOfDir++;
            } else {
                countOfFiles++;
                allSize += Files.size(file);
            }
            return FileVisitResult.CONTINUE;
        }

         @Override
         public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
             countOfDir++;
             return FileVisitResult.CONTINUE;
         }
     }


}



