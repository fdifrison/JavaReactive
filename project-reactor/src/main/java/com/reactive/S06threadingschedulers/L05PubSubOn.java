package com.reactive.S06threadingschedulers;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class L05PubSubOn {

    // For testing purpose, we are mixing the order of subOn and pubOn.
    // SubOn is close to the subscriber but will be executed first since it acts on the publisher;
    // when the main thread encounters the subOn, it returns to the publisher and starts the specified threadPool.
    // When the subscriber registers to the publisher,
    // then the pubOn takes action and the emission goes in the specified threadPool

    public static void main(String[] args) {


        Flux<Object> flux = Flux.create(fluxSink -> {
                    printThreadName("create");
                    for (int i = 0; i < 4; i++) {
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                })
                .doOnNext(i -> printThreadName("next " + i));


        flux
                .publishOn(Schedulers.parallel())
                .doOnNext(i -> printThreadName("next " + i))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(v -> printThreadName("sub " + v));

        Util.sleepSeconds(5);

    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\tThread:\t" + Thread.currentThread().getName());
    }


}
