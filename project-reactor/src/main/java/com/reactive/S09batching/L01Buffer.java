package com.reactive.S09batching;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L01Buffer {

    // Imagine we have a very fast producers
    // (i.e., we want to record the mouse clicking of the user) and the final operation is to write this info to db.
    // We don't want to create a transaction for each item;
    // instead, we want to group (batch) the emission and use them when a predetermined size is reached

    public static void main(String[] args) {

        eventStream()
                .buffer(5)
                // Items are collected in a list.of the type we are passing
                // N.B. if the source stops emitting and the batch size is not reached,
                // but the source emits the onComplete signal,
                // the last batch will return with a smaller size,
                // but we won't be hanging waiting for the buffer to be filled
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);

    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(300))
                .map(i -> "event:" + i);
    }
}
