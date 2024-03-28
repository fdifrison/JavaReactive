package com.reactive.S02flux;

import reactor.core.publisher.Flux;

public class L02MultipleSubscribers {

    public static void main(String[] args) {

        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4, 5);

        integerFlux
                .filter(i -> i % 2 != 0)
                .subscribe(integer -> System.out.println("Sub1: " + integer));

        integerFlux
                .filter(i -> i % 2 == 0)
                .subscribe(integer -> System.out.println("Sub2: " + integer));

    }

}
