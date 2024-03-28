package com.reactive.S6threadingschedulers;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L01ThreadDemo {

    // The default behavior will be to execute everything on the currentThread

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(fluxSink -> {
                    printThreadName("create");
                    fluxSink.next(1);
                })
                .doOnNext(i -> printThreadName("next " + i));

        System.out.println("Running on Main Thread:");
        flux.subscribe(v -> printThreadName("sub "+ v));

        // To switch to another thread, we use Runnable
        Runnable runnable = () -> flux.subscribe(v -> printThreadName("sub " + v));


        System.out.println("Running on new Thread:");
        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start(); // everything will happen in the new Thread
        }

        Util.sleepSeconds(5);

    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\tThread:\t" + Thread.currentThread().getName());
    }

}
