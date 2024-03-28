package com.reactive.S7overflowstrategy;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class L03Latest {


    public static void main(String[] args) {

        // Queues defines the reactor default sizing
        System.setProperty("reactor.bufferSize.small", "16");

        Flux.create(fluxSink -> {
                    for (int i = 1; i < 501; i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed:" + i);
                        Util.sleepMillis(1);
                    }
                    fluxSink.complete();
                })
//                .onBackpressureBuffer() // this is the default --> keep in memory
                .onBackpressureLatest()
                // Latest() has a small difference with Drop()
                // since it will cache the last value emitted
                // before reaching the 75% of drain while Drop will give us the element right after.
                // This also means that we can be sure that we will always get the last item emitted
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> Util.sleepMillis(10))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(6);
    }

}
