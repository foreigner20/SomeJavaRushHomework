package com.javarush.task.task33.task3310.strategy;

import com.javarush.task.task33.task3310.Helper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {

    private Path path;

    public FileBucket() throws IOException {
        path = Files.createTempFile(Helper.generateRandomString(), "");
        Files.deleteIfExists(path);
        Files.createFile(path);
        path.toFile().deleteOnExit();
    }

    public long getFileSize() throws IOException {
        return Files.size(path);
    }

    public void putEntry(Entry entry) throws IOException {
        OutputStream fos = Files.newOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(entry);
        fos.close();
        oos.close();
    }

    public Entry getEntry() throws IOException, ClassNotFoundException {
        Entry entry = null;
        if(getFileSize() > 0){
            InputStream fis = Files.newInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            entry = (Entry) ois.readObject();
            fis.close();
            ois.close();
        }
        return entry;
    }

    public void remove() throws IOException {
        Files.delete(path);
    }

}
