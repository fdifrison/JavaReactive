package com.reactive.S09batching;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L05GroupBy {

    public static void main(String[] args) {

        Flux.range(1, 30)
                .delayElements(Duration.ofSeconds(1))
                .groupBy(i -> i % 2)
                // we define the groups, in this case odd and even numbers
                // what is returned is a GroupedFlux
                .subscribe(groupedFlux -> process(groupedFlux, groupedFlux.key()));


        Util.sleepSeconds(60);


    }

    private static void process(Flux<Integer> flux, int key) {
        // This will be called one time for each group in the groupedFlux
        System.out.printf("process() called for key [%d]%n", key);
        flux.subscribe(i -> System.out.println("key:" + key + "-item:" + i));
    }

}
