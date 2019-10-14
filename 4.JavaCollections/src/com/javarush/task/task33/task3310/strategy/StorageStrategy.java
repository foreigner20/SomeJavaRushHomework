package com.javarush.task.task33.task3310.strategy;

import java.io.IOException;

public interface StorageStrategy {

    boolean containsKey(Long key) throws ClassNotFoundException, IOException;
    boolean containsValue(String value) throws IOException, ClassNotFoundException;
    void put(Long key, String value) throws IOException, ClassNotFoundException;
    Long getKey(String value) throws IOException, ClassNotFoundException;
    String getValue(Long key) throws IOException, ClassNotFoundException;
}
