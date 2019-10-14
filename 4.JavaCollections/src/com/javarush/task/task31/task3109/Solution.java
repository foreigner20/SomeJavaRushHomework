package com.javarush.task.task31.task3109;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/* 
Читаем конфиги
*/
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        Properties properties = solution.getProperties("4.JavaCollections/src/com/javarush/task/task31/task3109/properties.xml");
        properties.list(System.out);

        properties = solution.getProperties("4.JavaCollections/src/com/javarush/task/task31/task3109/properties.txt");
        properties.list(System.out);

        properties = solution.getProperties("4.JavaCollections/src/com/javarush/task/task31/task3109/notExists");
        properties.list(System.out);
    }

    public Properties getProperties(String fileName) {
        Properties pr = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream(new File(fileName));
            // Создаём новый FileInputStream, с первым параметром fileName
            // Если fileName в нижнем регистре заканчивается на .xml
            // Вызываем метод loadFromXML
            // Иначе
            // Вызываем метод load
            // Закрываем FileInputStream (хоть это и не является условием, просто на всякий)
            if(fileName.toLowerCase().endsWith(".xml")){
                pr.loadFromXML(inputStream);
            }else{
                pr.load(inputStream);
            }
        }catch (IOException e){

        }
        return pr;
    }
}
