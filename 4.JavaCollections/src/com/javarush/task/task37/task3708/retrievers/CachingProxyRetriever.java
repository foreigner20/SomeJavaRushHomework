package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.storage.Storage;

public class CachingProxyRetriever implements Retriever{

    OriginalRetriever originalRetriever;
    LRUCache lruCache = new LRUCache(16);

    public CachingProxyRetriever(Storage storage) {
        originalRetriever = new OriginalRetriever(storage);
    }

    @Override
    public Object retrieve(long id) {
        if(lruCache.find(id) == null){
            lruCache.set(id, originalRetriever.retrieve(id));
        }
        return lruCache.find(id);
    }
}
