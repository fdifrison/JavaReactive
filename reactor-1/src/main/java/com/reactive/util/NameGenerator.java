package com.reactive.util;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class NameGenerator {

    public static List<String> getNamesFromList(int count) {
        List<String> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(getName());
        }
        return list;
    }
    
    public static Flux<String> getNamesFromFlux(int count) {
        return Flux.range(0, count)
                .map(i -> getName());
    }

    private static String getName() {
        Util.sleep(1); // simulating an intensive task
        return Util.faker().name().firstName();
    }

}
