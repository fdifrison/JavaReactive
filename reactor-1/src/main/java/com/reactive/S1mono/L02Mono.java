package com.reactive.S1mono;

import reactor.core.publisher.Mono;

public class L02Mono {
    public static void main(String[] args) {
        // publisher
        Mono<Integer> mono = Mono.just(1);

        // N1 rules: nothing happen until we SUBSCRIBE!
        System.out.println(mono); // nothing happen, just print toString()

        mono.subscribe(
                i -> System.out.println("Received : " + i) // we are invoking the onNext() method
        );
    }
}
