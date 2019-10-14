package com.javarush.task.task38.task3802;

/* 
Проверяемые исключения (checked exception)
*/


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class VeryComplexClass {
    public void veryComplexMethod() throws Exception {
        //напишите тут ваш код
        FileInputStream inputStream = new FileInputStream("lol");
    }

    public static void main(String[] args) {

    }
}
