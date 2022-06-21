package com.rm.person_register.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rm.person_register.config.cache.CacheStore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Component
public class Client {
    private final CacheStore<ObjectNode> cache;

    public ObjectNode pokeIntegration() {
        ObjectNode cachedPokemonReturn = cache.get("poke");

        if (Objects.nonNull(cachedPokemonReturn)) {
            return cachedPokemonReturn;
        }
        WebClient client = WebClient.builder()
                                    .baseUrl("https://pokeapi.co/api/v2")
                                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                    .build();

        ObjectNode response = client.get()
                                    .uri("/pokemon/ditto")
                                    .retrieve()
                                    .bodyToMono(ObjectNode.class)
                                    .block();

        cache.add("poke", response);

        return response;
    }
}
