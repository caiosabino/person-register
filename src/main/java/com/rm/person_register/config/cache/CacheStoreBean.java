package com.rm.person_register.config.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheStoreBean {

    @Bean
    public CacheStore pokemonCache() {
        return new CacheStore (10, TimeUnit.SECONDS);
    }

}