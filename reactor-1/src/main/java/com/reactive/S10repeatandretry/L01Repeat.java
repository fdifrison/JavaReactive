package com.reactive.S10repeatandretry;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L01Repeat {


    public static void main(String[] args) {

        getIntegers()
//                .repeat(2)
                // Repeat two more time/**/s; the repetition starts before the onComplete signal
                // The means of repeating depends on the design strategy,
                // maybe we can expect different data from one repetition to the other.
                // If we specify repeat without the number, the repetition will go on indefinitely.
                // We can also specify a boolean supplier,
                // a function that based on a condition returns true or false and become the discriminant for the repetition,
                .repeat(L01Repeat::decideIfRepeat)
                // However, if we encounter an onError signal, the repetition will stop.
                .subscribe(Util.subscriber());


    }

    private static boolean decideIfRepeat() {
        Integer i = Util.faker().random().nextInt(1, 100);
        System.out.println("Extracted:" + i);
        return i % 2 == 0;
    }

    private static Flux<Integer> getIntegers() {
        return Flux.range(1, 3)
                .doOnSubscribe(s -> System.out.println("Subscribed"))
                .doOnComplete(() -> System.out.println("Completed"));
    }

}
