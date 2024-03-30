package com.reactive.S09batching;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class L04Window {

    // The difference between windows and buffer is that buffer group items into lists while window group items in fluxes
    // that are ready to use even if not filled.
    // Even for buffer the behavior is non-blocking,
    // the blocking part is in the creation of the datastructures (the lists),
    // but the emission of items is always non-blocking

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {


        eventStream()
                // a flux inside a flux, we can also set a duration instead of a number of elements
                .window(5)
                // we got a UnicastProcessor, essentially a flux, and we need to subscribe to it
                .flatMap(L04Window::saveEvents)
                // we need a flatmap and not a map since we have a 1-n mapping
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);

    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(i -> "event:" + i);
    }

    private static Mono<Integer> saveEvents(Flux<String> flux) {
        return flux.doOnNext(e -> System.out.println("saving:" + e))
                .doOnComplete(() -> {
                    System.out.println("saved batch n°" + atomicInteger);
                    System.out.println("----------------");
                })
                .then(Mono.just(atomicInteger.getAndIncrement()));
        // wait for the flux to be completed
        // and then return the Mono<Integer>

    }
}
