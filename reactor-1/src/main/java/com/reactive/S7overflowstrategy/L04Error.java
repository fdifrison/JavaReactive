package com.reactive.S7overflowstrategy;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class L04Error {


    public static void main(String[] args) {

        // Queues defines the reactor default sizing
        System.setProperty("reactor.bufferSize.small", "16");

        Flux.create(fluxSink -> {
                    for (int i = 1; i < 501 && !fluxSink.isCancelled(); i++) {
                        // We are checking if the fluxSink is canceled because as soon as the BackPressure strategy with OnError its,
                        // the subscription will be canceled
                        fluxSink.next(i);
                        System.out.println("Pushed:" + i);
                        Util.sleepMillis(1);
                    }
                    fluxSink.complete();
                })
//                .onBackpressureBuffer() // this is the default --> keep in memory
                .onBackpressureError()
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> Util.sleepMillis(10))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(6);
    }

}
