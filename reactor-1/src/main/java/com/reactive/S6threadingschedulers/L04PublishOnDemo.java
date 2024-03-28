package com.reactive.S6threadingschedulers;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class L04PublishOnDemo {

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
                // We are deciding which threadPoll the subscriber will be on from this point downward
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> printThreadName("next " + i))
                .publishOn(Schedulers.parallel()) // we can also mix the schedulers depending on the operation
                // we are expecting at each step of the stream;
                // for example, we might switch to parallel because we are expecting a cpu intensive task following
                .subscribe(v -> printThreadName("sub " + v));

        Util.sleepSeconds(5);

    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\tThread:\t" + Thread.currentThread().getName());
    }

}
