package com.reactive.S06threadingschedulers;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;

public class L03SubscribeOnMultipleItems {

    // All the items are emitted by the same thread... why??
    // Even if we use schedulers.parallel!
    // This is because items are emitted always sequentially,
    // the parallelism given by the Thread pool is meant
    // to have parallelism among different subscribers and not on the data processed by the single subscriber

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(fluxSink -> {
                    printThreadName("create");
                    for (int i = 0; i < 5; i++) {
                        fluxSink.next(i);
                        Util.sleepSeconds(1);
                    }
                    fluxSink.complete();
                })
                .doOnNext(i -> printThreadName("next " + i));

        flux.subscribeOn(Schedulers.boundedElastic())
                .subscribe(v -> printThreadName("sub " + v));

        // If we want to see the parallelism, we have to ask for multiple threads
        // Now each boundElastic thread is used for each subscriber
        // we can also mix up different schedulers
        Function<Scheduler, Runnable> subThread = scheduler -> () -> flux.subscribeOn(scheduler)
                .subscribe(v -> printThreadName("sub " + v));
        for (int i = 0; i < 4; i++) {
            Scheduler s = i % 2 == 0 ? Schedulers.boundedElastic() : Schedulers.parallel();
            new Thread(subThread.apply(s)).start();
        }


        Util.sleepSeconds(10);

    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\tThread:\t" + Thread.currentThread().getName());
    }
}
