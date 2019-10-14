package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.IOException;
import java.util.*;

/* 
Находим все файлы
*/
public class Solution {
    public static List<String> getFileTree(String root) throws IOException {
        File path = new File(root);
        List<String> result = new ArrayList<>();
        Queue<File> fileQueue = new PriorityQueue<>();

        Collections.addAll(fileQueue, path.listFiles());

        while(!fileQueue.isEmpty()){
            File currentFile = fileQueue.remove();
            if(currentFile.isDirectory()){
                Collections.addAll(fileQueue, currentFile.listFiles());
            }else {
                result.add(currentFile.getAbsolutePath());
            }
        }
        return result;

    }

    public static void main(String[] args) {
        
    }
}
