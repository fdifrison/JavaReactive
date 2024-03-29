package com.reactive.S10repeatandretry;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L02Retry {

    public static void main(String[] args) {

        getIntegers()
                .retry(2)
                // like repeat, reties repeat the subscription but only if the signal onError is about to be thrown.
                // retry() without argument will retry infinite times
                .subscribe(Util.subscriber());


    }


    private static Flux<Integer> getIntegers() {
        return Flux.range(1, 3)
                .doOnSubscribe(s -> System.out.println("Subscribed"))
                .doOnComplete(() -> System.out.println("Completed"))
                .map(i -> i / (2 - i))
                .doOnError(err -> System.out.println("Error"));
    }

}
