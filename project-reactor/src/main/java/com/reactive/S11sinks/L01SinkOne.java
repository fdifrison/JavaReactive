package com.reactive.S11sinks;

import com.reactive.util.Util;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class L01SinkOne {

    public static void main(String[] args) {

        // Flux.create(fluxSink -> ) How we have created sinks so far

        Sinks.One<Object> sink = Sinks.one(); // a sink capable of emitting a single value -> Mono / empty / error

        // The following are the most common method to use to consume a Sinks.one
//        sink.tryEmitValue(Util.subscriber());
//        sink.tryEmitEmpty();
//        sink.tryEmitError(new RuntimeException("oops"));

        // we can also transform it into a mono,
        // in this way we distinguish which is the entity that the client will call to receive an item,
        // versus the sink which is the publisher
        Mono<Object> mono = sink.asMono();

        mono.subscribe(Util.subscriber("Frodo")); // we use the mono to receive a value

        sink.tryEmitValue("HI"); //we use the sink to drop a value


    }

}
