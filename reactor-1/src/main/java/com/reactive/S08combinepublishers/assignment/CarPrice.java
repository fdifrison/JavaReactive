package com.reactive.S08combinepublishers.assignment;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class CarPrice {

    public static void main(String[] args) {

        AtomicInteger price = new AtomicInteger(10_000);

        Flux.combineLatest(getMonthPrice(price), getQuarterDemand(), (p, d) -> p * d)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);

    }


    private static Flux<Integer> getMonthPrice(AtomicInteger initialPrice) {
        return Flux.interval(Duration.ZERO, Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("One month has past.."))
                .map(i -> initialPrice.addAndGet(-100));
    }


    private static Flux<Double> getQuarterDemand() {
        return Flux.interval(Duration.ofSeconds(3))
                .map(i -> Util.faker().random().nextInt(8, 12) / 10d)
                .doOnNext(i -> System.out.println("Quarter eval. is : " + i))
                .startWith(1d);

    }

}
