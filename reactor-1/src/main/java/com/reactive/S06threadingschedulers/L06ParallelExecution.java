package com.reactive.S06threadingschedulers;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class L06ParallelExecution {

    // If we want to parallelize the single emission, we have to use two operators:
    // parallel() and runOn()
    // N.B. Flux.interval and delayElements internally use Schedulers.parallel()
    // therefore will be by default executed not in the main thread.

    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>();
        Collection<Integer> syncList = Collections.synchronizedList(new ArrayList<>());

        Flux.range(1, 1000)
                .parallel()
                .runOn(Schedulers.parallel())
                //.sequential()
                // parallel returns a ParallelFlux that doesn't have the subOn pubOn methods;
                // if we need to invoke those we need first to call sequential()
                .subscribe(integer -> {
                    list.add(integer);
                    syncList.add(integer);
                });

        Util.sleepSeconds(5);
        System.out.println("We expect list.size to be 1000 but array list is not thread-safe!");
        System.out.println("List size is: " + list.size());
        for (int i = 1; i <= 1000; i++) {
            if (!list.contains(i)) System.out.println(i + " is missing!");
        }
        System.out.println("syncList size is: " + syncList.size());

    }

    private static void printThreadName(String msg) {
        System.out.println(msg + "\tThread:\t" + Thread.currentThread().getName());
    }

}
