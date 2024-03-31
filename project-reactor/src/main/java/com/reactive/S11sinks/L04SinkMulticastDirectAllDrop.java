package com.reactive.S11sinks;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class L04SinkMulticastDirectAllDrop {

    public static void main(String[] args) {

        // SINK -> for the Publisher
        // sink.asFlux() -> for the Subscribers

        Sinks.Many<Object> sinkDirect = Sinks.many()
                .multicast()
                .directAllOrNothing();

        // Now imagine we have 2 subscribers but one that is slower for some reason.
        // If we try to emit a large number of items,
        // since Sam is slowing down the queues, with directAllOrNothing() also Gandalf will be affected

        Sinks.Many<Object> sinkBest = Sinks.many()
                .multicast()
                .directBestEffort();

        // if instead, we use the bestEffort strategy, Gandalf won't be affected by the slowness of Sam

        Flux<Object> fluxDirect = sinkDirect.asFlux();
        Flux<Object> fluxBest = sinkBest.asFlux();

        fluxDirect.subscribe(Util.subscriber("Gandalf-Direct"));
        fluxDirect.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("Sam-Direct"));

        fluxBest.subscribe(Util.subscriber("Gandalf-Best"));
        fluxBest.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("Sam-Best"));

        for (int i = 0; i < 100; i++) {
            sinkDirect.tryEmitNext(i);
            sinkBest.tryEmitNext(i);
        }

        Util.sleepSeconds(10);



    }

}
