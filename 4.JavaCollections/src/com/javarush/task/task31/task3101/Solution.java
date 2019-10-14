package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.*;

/*
Проход по дереву файлов
*/
public class Solution {
    public static void main(String[] args) throws FileNotFoundException {

        File myFile = new File(args[1]);
        String myFilePath = myFile.getParent();
        String myRenameFilePath = myFilePath + "/" + "allFilesContent.txt";
        File myRenameFile = new File(myRenameFilePath);
        if (FileUtils.isExist(myFile))
            FileUtils.renameFile(myFile, myRenameFile);

        try(FileWriter writer = new FileWriter(myRenameFile);
            BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            File folder = new File(args[0]);
            List<File> fileList = new ArrayList<>();
            List<File> files = new ArrayList<>();
            files = Arrays.asList(folder.listFiles());

            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                if (file != null && file.isFile()) {
                    fileList.add(file);

                } else if (file != null && file.isDirectory()) {
                    addFilesFromDirectory(file, fileList);
                }
            }

            Collections.sort(fileList);

//            for (File file: fileList) {
//                System.out.println(file.getName());
//            }

            for (File file: fileList) {
                if (file.length() <= 50) {
                    try (FileReader reader = new FileReader(file);
                         BufferedReader bufferedReader = new BufferedReader(reader)) {
                        while (bufferedReader.ready()) {
                            String line = bufferedReader.readLine();
                            bufferedWriter.write(line);
                        }
                        bufferedWriter.write("\n");
                    } catch (IOException e) {
                        System.out.println("Uuups! Something wrong.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addFilesFromDirectory(File directory, List<File> fileList) {
        File[] files = directory.listFiles();
        for (File file: files) {
            if(file != null && file.isFile()) {
                fileList.add(file);
            } else if (file != null && file.isDirectory()) {
                addFilesFromDirectory(file, fileList);
            }
        }
    }
}

