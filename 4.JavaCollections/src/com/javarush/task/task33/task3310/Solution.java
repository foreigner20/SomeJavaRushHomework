package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.StorageStrategy;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        testStrategy(new HashMapStorageStrategy(), 10000);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) throws IOException, ClassNotFoundException {
        Set<Long> Ids = new HashSet<>();
        for(String s : strings){
            Ids.add(shortener.getId(s));
        }
        return Ids;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) throws IOException, ClassNotFoundException {
        Set<String> strings = new HashSet<>();
        for(Long l : keys){
            strings.add(shortener.getString(l));
        }
        return strings;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) throws IOException, ClassNotFoundException {
        Helper.printMessage(strategy.getClass().getSimpleName());

        Set<String> forTest = new HashSet<>();
        for(int i = 0; i < elementsNumber; i++){
            forTest.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(strategy);

        Date startIdTest = new Date();
        Set<Long> testForIds = getIds(shortener, forTest);
        Date endIdTest = new Date();

        Helper.printMessage(String.valueOf(endIdTest.getTime() - startIdTest.getTime()));

        Date startStringTest = new Date();
        Set<String> testForStrings = getStrings(shortener, testForIds);
        Date endStringTest = new Date();

        Helper.printMessage(String.valueOf(endStringTest.getTime() - startStringTest.getTime()));

        if(forTest.size() == testForStrings.size()){
            Helper.printMessage("Тест пройден.");
        }else {
            Helper.printMessage("Тест не пройден.");
        }
    }
}
