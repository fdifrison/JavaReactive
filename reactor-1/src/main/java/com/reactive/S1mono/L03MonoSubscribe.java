package com.reactive.S1mono;

import com.reactive.util.Util;
import reactor.core.publisher.Mono;

public class L03MonoSubscribe {

    public static void main(String[] args) {

        //publisher
        Mono<Integer> mono = Mono.just("ball")
                .map(String::length)
                .map(l -> l / 0);

        // opt1
        // mono.subscribe(); // the publisher emits the element,
        // but we don't see anything since we didn't provide the consumer that will call the onNext method

        //opt2
        // we provide the onNext, onError and onComplete consumers
        mono.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete()
        );
    }

}
