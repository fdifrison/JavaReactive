package com.reactive.S06threadingschedulers;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class L02SubscribeOnDemo {

    //N.B. the publisher should make the choice of the scheduler that needs to be used,
    // since in real scenarios the subscriber might not know the entity of the task that is going to be executed.
    // This is also why even if we have multiple schedulers defined in the pipeline,
    // only the closer to the publisher will act

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(fluxSink -> {
                    printThreadName("create");
                    fluxSink.next(1);
                })
                .subscribeOn(Schedulers.boundedElastic()) // This is the subscribeOn
                // that do the work since it is closer to the publisher
                .doOnNext(i -> printThreadName("next " + i));

        System.out.println("Running on Main Thread:");
        flux.doFirst(() -> printThreadName("first2"))
                // N.B. from here upward we are on the Scheduler Thread!
                .subscribeOn(Schedulers.boundedElastic()) // we will have by default x10 the physical cpu core of our machine
                // Until here we are on the main Thread
                .doFirst(() -> printThreadName("first1"))
                .subscribe(v -> printThreadName("sub " + v));


        Util.sleepSeconds(5);

    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\tThread:\t" + Thread.currentThread().getName());
    }

}
