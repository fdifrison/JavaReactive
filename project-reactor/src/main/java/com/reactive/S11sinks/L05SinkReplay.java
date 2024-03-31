package com.reactive.S11sinks;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class L05SinkReplay {

    public static void main(String[] args) {

        // SINK -> for the Publisher
        // sink.asFlux() -> for the Subscribers

        Sinks.Many<Object> sink = Sinks.many()
                .replay().all();

        // With replay.all the sink will try to cache all the items emitted
        // (we can specify inside parenthesis the size of the cache).
        // Therefore, also late subscribers will be provided with the early emitted items

        Flux<Object> flux = sink.asFlux();

        flux.subscribe(Util.subscriber("Gandalf"));
        flux.subscribe(Util.subscriber("Sam"));


        sink.tryEmitNext("Hi");
        sink.tryEmitNext("Did Frodo dropped the ring?");

        flux.subscribe(Util.subscriber("Merry"));


        sink.tryEmitNext("Ehi Merry! you lost the beginning of the conversation!");


    }


}
