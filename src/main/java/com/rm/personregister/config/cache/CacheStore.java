package com.rm.personregister.config.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheStore<T> {
    private Cache<String, T> cache;

    //Constructor to build Cache Store
    public CacheStore(int expiryDuration, TimeUnit timeUnit) {
        cache = CacheBuilder.newBuilder()
                            .expireAfterWrite(expiryDuration, timeUnit)
                            .concurrencyLevel(Runtime.getRuntime()
                                                     .availableProcessors())
                            .build();
    }

    //Method to fetch previously stored record using record key
    public T get(String key) {
        return cache.getIfPresent(key);
    }

    //Method to put a new record in Cache Store with record key
    public void add(String key, T value) {
        if (Objects.nonNull(key) && Objects.nonNull(value)) {
            cache.put(key, value);
            log.info("Record stored in {} Cache whit key = {}", value.getClass()
                                                                     .getSimpleName(), key);
        }
    }
}