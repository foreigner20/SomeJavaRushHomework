package com.javarush.task.task33.task3310.strategy;

import com.google.common.collect.HashBiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.io.IOException;

public class DualHashBidiMapStorageStrategy implements StorageStrategy{


    DualHashBidiMap<Long, String> data = new DualHashBidiMap<>();

    @Override
    public boolean containsKey(Long key) throws ClassNotFoundException, IOException {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(String value) throws IOException, ClassNotFoundException {
        return data.containsValue(value);
    }

    @Override
    public void put(Long key, String value) throws IOException, ClassNotFoundException {
        data.put(key, value);
    }

    @Override
    public Long getKey(String value) throws IOException, ClassNotFoundException {
        return data.getKey(value);
    }

    @Override
    public String getValue(Long key) throws IOException, ClassNotFoundException {
        return data.get(key);
    }
}
