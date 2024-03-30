package com.reactive.S04operators;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L09SwitchIfEmpty {

    public static void main(String[] args) {
        getOrderNumbers()
                .filter(i -> i > 10) // my custom filter won't let any emission pass,
                // hence we can define a defaultFallback if the queue is empty
                .switchIfEmpty(switchFallback()) // instead of a constant value as in defaultIfEmpty,
                // we can define a publisher as a fallback
                .subscribe(Util.subscriber());
    }

    // we can immagine this as being our redis cache
    private static Flux<Integer> getOrderNumbers() {
        return Flux.range(1, 10);
    }

    // and this the fallback query to the db in case the cache is empty
    private static Flux<Integer> switchFallback() {
        return Flux.range(100, 10);
    }

}
