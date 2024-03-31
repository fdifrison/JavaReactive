package com.reactive.S11sinks;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class L04SinkMulticastDirectAll {

    public static void main(String[] args) {

        // SINK -> for the Publisher
        // sink.asFlux() -> for the Subscribers

        Sinks.Many<Object> sink = Sinks.many()
                .multicast()
                .directAllOrNothing();

        // Now, with the directOrNothing options,
        // the publisher will start emitting even if no subscriber is listening; hence nobody will see the "hi" message

        Flux<Object> flux = sink.asFlux();
        sink.tryEmitNext("Hi");

        flux.subscribe(Util.subscriber("Gandalf"));
        flux.subscribe(Util.subscriber("Sam"));

        // Now that we are using multicast instead of unicast, multiple subscriber can receive items from the same sink

        sink.tryEmitNext("Did Frodo dropped the ring?");

        flux.subscribe(Util.subscriber("Merry"));
        // If we have a late subscriber, he will miss the items already emitted

        sink.tryEmitNext("Ehi Merry! you lost the beginning of the conversation!");


    }

}
