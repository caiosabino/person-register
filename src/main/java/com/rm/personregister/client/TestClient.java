package com.rm.personregister.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rm.personregister.config.cache.CacheStore;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class TestClient {
    CacheStore<ObjectNode> cache;

    public ObjectNode pokeIntegration() {
        ObjectNode cachedPokemonReturn = Optional.of(cache.get("poke"))
                                                 .orElse(null);

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
        log.info("gravou no cache");

        return response;
    }
}
