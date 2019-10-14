package com.javarush.task.task33.task3310.strategy;

import java.io.IOException;

import static java.util.Objects.hash;

public class FileStorageStrategy implements StorageStrategy{

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final long DEFAULT_BUCKET_SIZE_LIMIT  = 10000L;
    int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    FileBucket[] table;
    long maxBucketSize;

    public FileStorageStrategy() throws IOException {
        table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
        for(int i = 0; i < table.length; ++i)
            table[i] = new FileBucket();
    }

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    public int hash(Long k){
        k ^= (k >>> 20) ^ (k >>> 12);
        return (int) (k ^ (k >>> 7) ^ (k >>> 4));
    }

    public int indexFor(int hash, int length){
        return hash & (length - 1);
    }

    public Entry getEntry(Long key) throws IOException, ClassNotFoundException {
        int hash = (key == null) ? 0 : hash( (long) key.hashCode() );
        for (Entry e = table[indexFor( hash, table.length )].getEntry();
             e != null;
             e = e.next) {
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals( k ))))
                return e;
        }
        return null;
    }

    public void resize(int newCapacity) throws IOException, ClassNotFoundException {
        FileBucket[] newTable = new FileBucket[newCapacity];
        for (FileBucket fileBucket : newTable)
            fileBucket = new FileBucket();

        transfer(newTable);
        table = newTable;
    }

    public void transfer(FileBucket[] newTable) throws IOException, ClassNotFoundException {
        int newCapacity = newTable.length;

        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            Entry entry = table[i].getEntry();

            while (entry != null) {
                Entry next = entry.next;
                int indexFor = indexFor(entry.hash, newCapacity);
                entry.next = newTable[indexFor].getEntry();
                newTable[indexFor].putEntry(entry);
                entry = next;
            }
            table[i].remove();
            table[i] = null;
        }
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) throws IOException, ClassNotFoundException {
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        if (table[bucketIndex].getFileSize() > bucketSizeLimit) {
            resize(2 * table.length);
            size++;
        }
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) throws IOException {
        table[bucketIndex] = new FileBucket();
        table[bucketIndex].putEntry(new Entry(hash, key, value, null));
        size++;
    }

    @Override
    public boolean containsKey(Long key) throws IOException, ClassNotFoundException {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) throws IOException, ClassNotFoundException {
        for (int i = 0; i < table.length; i++) {
            for (Entry e = table[i].getEntry(); e != null; e = e.next) {
                if (value.equals(e.value))
                    return true;
            }
        }
        return false;
    }

    @Override
    public void put(Long key, String value) throws IOException, ClassNotFoundException {
        int hash = hash(key);
        int i = indexFor(hash, table.length);

        if (table[i] == null)
            createEntry(hash, key, value, i);
        else {
            for (Entry entry = table[i].getEntry(); entry != null; entry = entry.next) {
                Long k;
                if (entry.hash == hash && ((k = entry.key) == key || key.equals(k)))
                    entry.value = value;
            }
            addEntry(hash, key, value, i);
        }
    }

    @Override
    public Long getKey(String value) throws IOException, ClassNotFoundException {
        for (int i = 0; i < table.length ; i++) {
            if (table[i] == null) continue;
            for (Entry e = table[i].getEntry(); e != null; e = e.next)
                if (value.equals(e.value))
                    return e.key;
        }
        return null;    }

    @Override
    public String getValue(Long key) throws IOException, ClassNotFoundException {
        return getEntry(key).getValue();
    }
}
