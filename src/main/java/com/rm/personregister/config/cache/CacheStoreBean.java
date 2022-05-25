package com.rm.personregister.config.cache;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheStoreBean {

    @Bean
    public CacheStore<ObjectNode> pokemonCache() {
        return new CacheStore<ObjectNode>(10, TimeUnit.SECONDS);
    }

}