package com.reactive.S4operators;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class L06OnError {

    public static void main(String[] args) {

        Flux.range(1, 10)
                .log()
                .map(i -> 10 / (5 - i))
//                .onErrorReturn(-1) // handle an error with a fallback
//                .onErrorResume(e -> fallback())
                .onErrorContinue( (err, o) -> System.out.println("Keep going even if: "+ err.getMessage()))
                .subscribe(Util.subscriber());

    }

    private static Mono<Integer> fallback() {
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(100, 200));
    }

}
