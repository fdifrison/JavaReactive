package com.reactive.S4operators;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L07Timeout {

    public static void main(String[] args) {

        getOrderNumbers()
                .timeout(Duration.ofSeconds(2), fallback()) // if we don't provide the fallback
                // and the subscriber can't emit anything before the timeout, we will just receive an error
                .subscribe(Util.subscriber());


        Util.sleep(60); // to stop the main thread and see the output


    }

    // immagine we have a service that is emitting items very slowly
    private static Flux<Integer> getOrderNumbers() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(5));
    }

    private static Flux<Integer> fallback() {
        return Flux.range(100, 10)
                .delayElements(Duration.ofMillis(200));
    }

}
