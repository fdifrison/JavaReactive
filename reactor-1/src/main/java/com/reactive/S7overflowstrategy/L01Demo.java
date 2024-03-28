package com.reactive.S7overflowstrategy;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class L01Demo {

    public static void main(String[] args) {

        // The default behavior will be to buffer (hence, keep in memory)
        // what the source is emitting
        // so that the subscriber can still retrieve all the data even if is receiving slower for some reason;
        // this is an acceptable behavior if we immagine to have occasional spikes with high volume of data emitted.

        Flux.create(fluxSink -> {
                    for (int i = 1; i < 501; i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed:" + i);
                    }
                    fluxSink.complete();
                })
                .publishOn(Schedulers.boundedElastic())
                // from here we have different thread that is slower for some reason
                // we have backpressure
                // since the source is emitting much faster that the subscriber capacity
                // of receiving items due for example to cpu intensive steps
                .doOnNext(i -> Util.sleepMillis(10))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }

}
