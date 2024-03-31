package com.reactive.S11sinks;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class L02SinkUnicast {

    public static void main(String[] args) {

        // SINK -> for the Publisher
        // sink.asFlux() -> for the Subscribers

        // Sinks many is a sink that emits a Flux.
        // Again, the sink wìll be the handle through which we will push items programmatically
        Sinks.Many<Object> sink = Sinks.many()
                .unicast()
                .onBackpressureBuffer();
        // we have to specify a backpressure strategy to which we might also pass our custom queue

        // The flux created from the sink will be the item we will use to let the subscribers receive data from the sink
        Flux<Object> flux = sink.asFlux();
        flux.subscribe(Util.subscriber("Sam"));
        flux.subscribe(Util.subscriber("Gandalf")); // with unicast we are allowing only one subscriber,
        // hence Gandalf can't receive the messages that Sam received

        sink.tryEmitNext("Hi Sam");
        sink.tryEmitNext("Did Frodo dropped the ring?");



    }


}
