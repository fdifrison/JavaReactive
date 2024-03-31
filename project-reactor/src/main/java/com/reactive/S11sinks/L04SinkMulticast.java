package com.reactive.S11sinks;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class L04SinkMulticast {

    public static void main(String[] args) {

        // SINK -> for the Publisher
        // sink.asFlux() -> for the Subscribers

        Sinks.Many<Object> sink = Sinks.many()
                .multicast()
                .onBackpressureBuffer();

        Flux<Object> flux = sink.asFlux();
        sink.tryEmitNext("Hi");

        // Sam will receive also the first message even if they subscribed after its emission;
        // this because the publisher will wait to have at least one subscriber to emit.

        flux.subscribe(Util.subscriber("Sam"));
        flux.subscribe(Util.subscriber("Gandalf"));

        // Now that we are using multicast instead of unicast, multiple subscriber can receive items from the same sink

        sink.tryEmitNext("Did Frodo dropped the ring?");

        flux.subscribe(Util.subscriber("Merry"));
        // If we have a late subscriber, he will miss the items already emitted

        sink.tryEmitNext("Ehi Merry! you lost the beginning of the conversation!");


    }

}
