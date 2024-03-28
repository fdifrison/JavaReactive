package com.reactive.S08combinepublishers;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L05CombineLatest {

    //Example from reactor documentation
    // Combine latest will emit a combined item composed by the last emitted item from each publisher

    public static void main(String[] args) {

        Flux.combineLatest(getString(), getNumber(), (s, n) -> s + n)
                .subscribe(Util.subscriber("CombiningLatest"));

        Util.sleepSeconds(10);

    }

    private static Flux<String> getString() {
        return Flux.just("A", "B", "C", "D")
                .delayElements(Duration.ofSeconds(1))
                .doOnNext((i) -> System.out.println("Emitting:" + i));
    }

    private static Flux<Integer> getNumber() {
        return Flux.just(1, 2, 3)
                .delayElements(Duration.ofSeconds(3))
                .doOnNext((i) -> System.out.println("Emitting:" + i));
    }

}
