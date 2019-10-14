package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SpeedTest {



    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) throws IOException, ClassNotFoundException {
        Date start = new Date();
        Iterator stringsIterator = strings.iterator();
        while (stringsIterator.hasNext()){
            ids.add(shortener.getId(stringsIterator.next().toString()));
        }
        Date end = new Date();
        return (end.getTime() - start.getTime());
    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) throws IOException, ClassNotFoundException {
        Date start = new Date();
        Iterator idsIterator = ids.iterator();
        while (idsIterator.hasNext()){
            strings.add(shortener.getString(Long.valueOf(idsIterator.next().toString())));
        }
        Date end = new Date();
        return (end.getTime() - start.getTime());
    }

    @Test
    public void testHashMapStorage() throws IOException, ClassNotFoundException {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = new HashSet<>();
        for(int i = 0; i < 10000; i++){
            origStrings.add(Helper.generateRandomString());
        }

        Set<Long> origIdsSh1 = new HashSet<>();
        Set<Long> origIdsSh2 = new HashSet<>();
        long timeForIdsSh1 = getTimeToGetIds(shortener1, origStrings, origIdsSh1);
        long timeForIdsSh2 = getTimeToGetIds(shortener2, origStrings, origIdsSh2);
        if(timeForIdsSh1 < timeForIdsSh2){
            Assert.fail();
        }

        Set<String> forTestSh1 = new HashSet<>();
        Set<String> forTestSh2 = new HashSet<>();
        long timeForStringsSh1 = getTimeToGetStrings(shortener1, origIdsSh1, forTestSh1);
        long timeForStringsSh2 = getTimeToGetStrings(shortener2, origIdsSh2, forTestSh2);
        Assert.assertEquals(timeForStringsSh1, timeForStringsSh2, 30);

    }
}
