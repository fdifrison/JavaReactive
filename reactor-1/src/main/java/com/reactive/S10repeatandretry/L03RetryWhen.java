package com.reactive.S10repeatandretry;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;

public class L03RetryWhen {

    public static void main(String[] args) {

        getIntegers()
                .retryWhen(Retry.fixedDelay(2, Duration.ofSeconds(2)))
                // retryWhen is like retry but with more options on the retry strategy
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);


    }


    private static Flux<Integer> getIntegers() {
        return Flux.range(1, 3)
                .doOnSubscribe(s -> System.out.println("Subscribed"))
                .doOnComplete(() -> System.out.println("Completed"))
                .map(i -> i / (2 - i))
                .doOnError(err -> System.out.println("Error"));
    }

}
