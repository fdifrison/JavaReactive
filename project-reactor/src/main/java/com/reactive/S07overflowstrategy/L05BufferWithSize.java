package com.reactive.S07overflowstrategy;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class L05BufferWithSize {


    public static void main(String[] args) {

        // Queues defines the reactor default sizing
        System.setProperty("reactor.bufferSize.small", "16");

        Flux.create(fluxSink -> {
                    for (int i = 1; i < 201 && !fluxSink.isCancelled(); i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed:" + i);
                        Util.sleepMillis(1);
                    }
                    fluxSink.complete();
                })
                .onBackpressureBuffer(20, o -> System.out.println("Dropped:" + o))
                // this is the default --> keep in memory;
                // however, we can define a custom maxSize upon which the subscriber will call OnError
                // we also define a fallback consumer for the dropped elements,
                // since even if we are emitting the onError signal from the subscriber,
                // the producer might have produced already some items due to latency in receiving the signal
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> Util.sleepMillis(10))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(4);
    }

}
