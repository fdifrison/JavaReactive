package com.reactive.S7overflowstrategy;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.concurrent.Queues;

public class L02Drop {

    public static void main(String[] args) {

        // Queues defines the reactor default sizing
        System.setProperty("reactor.bufferSize.small", "16");

        Flux.create(fluxSink -> {
                    for (int i = 1; i < 501; i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed:" + i);
                        Util.sleepMillis(1);
                        // Adding a small delay to the producer will let the consumer
                        // start before all the item are emitted;
                        // in this case, we can observe the dropping behavior of this backpressure strategy.
                        // We are asking the buffer to hold 16 items; this means
                        // that by default, when we drain 75% of the buffer, we can start inserting new items.
                        // Hence, 75% of 16 is 12 -> when the consumer reaches the 12th element, it will insert into que
                        // what the source is producing at that time
                        // (roughly the 76th element in this case).
                        // Therefore, after processing element 16, it will go to element 76!
                    }
                    fluxSink.complete();
                })
//                .onBackpressureBuffer() // this is the default --> keep in memory
                .onBackpressureDrop()
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> Util.sleepMillis(10))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }

}
