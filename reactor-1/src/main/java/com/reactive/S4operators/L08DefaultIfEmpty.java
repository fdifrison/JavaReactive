package com.reactive.S4operators;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L08DefaultIfEmpty {

    public static void main(String[] args) {
        getOrderNumbers()
                .filter(i -> i > 10) // my custom filter won't let any emission pass,
                // hence we can define a defaultFallback if the queue is empty
                .defaultIfEmpty(-100) // we can provide a constant value as fallback
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> getOrderNumbers() {
        return Flux.range(1, 10);
    }


}
