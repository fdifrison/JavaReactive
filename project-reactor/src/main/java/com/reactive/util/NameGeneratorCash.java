package com.reactive.util;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class NameGeneratorCash {

    // We are caching the names as soon as they are produced
    List<String> cache = new ArrayList<>();

    public Flux<String> generateNames() {
        return Flux.generate(stringSynchronousSink -> {
                    System.out.println("Nothing in cache... generating new name");
                    Util.sleepSeconds(1); // imagine che this task is time-consuming; hence we use a cache mechanism
                    String name = Util.faker().name().firstName();
                    cache.add(name);
                    stringSynchronousSink.next(name);
                })
                .cast(String.class)
                .startWith(getFromCache());
                // We are emitting values from the cache first, and if not present, we will do the generate() block
    }

    private Flux<String> getFromCache() {
        System.out.println("Getting name from cache");
        return Flux.fromIterable(cache);
    }

}
