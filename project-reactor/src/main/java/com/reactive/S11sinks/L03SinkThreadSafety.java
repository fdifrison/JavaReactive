package com.reactive.S11sinks;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class L03SinkThreadSafety {

    // Reactor documentation says that by default sinks are thread-safe
    // from the examples below we can see that is true only if we handle the error signals with a retry strategy

    public static void main(String[] args) {

        Sinks.Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();

        Flux<Object> flux = sink.asFlux();
        List<Object> list = new ArrayList<>();

        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            final int j = i;
            CompletableFuture.runAsync(() -> sink.tryEmitNext(j));
        }

        Util.sleepSeconds(3);
        System.out.println("List size should be 1000 but is " + list.size()); // will this be 1000??

        list.removeAll(List.copyOf(list));

        for (int i = 0; i < 1000; i++) {
            final int j = i;
            CompletableFuture.runAsync(() -> sink.emitNext(j,
                            ((signalType, emitResult) -> true)) // we are brutally saying:
                    // "if you encounter any error, just retry"
            );
        }

        Util.sleepSeconds(3);
        System.out.println("List size should be 1000 and is " + list.size()); // will this be 1000??


    }

}
