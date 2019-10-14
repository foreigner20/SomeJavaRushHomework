package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class FunctionalTest {

    public void testStorage(Shortener shortener) throws IOException, ClassNotFoundException {
        String first = "testSame";
        String second = "test";
        String third = "testSame";

        Long firstIds = shortener.getId(first);
        Long secondIds = shortener.getId(second);
        Long thirdIds = shortener.getId(third);

        Assert.assertNotEquals(second, first);
        Assert.assertNotEquals(second, third);
        Assert.assertEquals(first, third);

        String firstFromStorage = shortener.getString(firstIds);
        String secondFromStorage = shortener.getString(secondIds);
        String thirdFromStorage = shortener.getString(thirdIds);

        Assert.assertEquals(first, firstFromStorage);
        Assert.assertEquals(second, secondFromStorage);
        Assert.assertEquals(third, thirdFromStorage);
    }

    @Test
    public void testHashMapStorageStrategy() throws IOException, ClassNotFoundException {
        Shortener shortener = new Shortener(new HashMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy() throws IOException, ClassNotFoundException {
        Shortener shortener = new Shortener(new OurHashMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy() throws IOException, ClassNotFoundException {
        Shortener shortener = new Shortener(new FileStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy() throws IOException, ClassNotFoundException {
        Shortener shortener = new Shortener(new HashBiMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy() throws IOException, ClassNotFoundException {
        Shortener shortener = new Shortener(new DualHashBidiMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy() throws IOException, ClassNotFoundException {
        Shortener shortener = new Shortener(new OurHashBiMapStorageStrategy());
        testStorage(shortener);
    }
}
