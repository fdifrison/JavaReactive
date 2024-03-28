package com.reactive.S7overflowstrategy;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;

public class L02DropHandleDropped {

    public static void main(String[] args) {

        // simulating the db to store dropped data
        ArrayList<Object> db = new ArrayList<>();

        // Queues defines the reactor default sizing
        System.setProperty("reactor.bufferSize.small", "100");

        Flux.create(fluxSink -> {
                    for (int i = 1; i < 201; i++) {
                        fluxSink.next(i);
                        System.out.println("Pushed:" + i);
                        Util.sleepMillis(7);
                    }
                    fluxSink.complete();
                })
//                .onBackpressureBuffer() // this is the default --> keep in memory
                .onBackpressureDrop(db::add)
                // we can decide what to do to the elements dropped
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> Util.sleepMillis(10))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(6);
        System.out.println("The following are the dropped items redirected to db:");
        System.out.println(db);
    }

}
