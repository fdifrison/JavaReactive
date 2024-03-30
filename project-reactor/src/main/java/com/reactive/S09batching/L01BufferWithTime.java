package com.reactive.S09batching;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L01BufferWithTime {


    public static void main(String[] args) {

        eventStream()
//                .buffer(Duration.ofSeconds(2))
                // instead of a number of items, we can specify a batch temporal size
                .bufferTimeout(10, Duration.ofSeconds(2))
                // we can also use a hybrid approach to be able to handle spikes in the supply
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);

    }

    private static Flux<String> eventStream() {
        Integer reqTime = Util.faker().random().nextInt(100, 300);
        return Flux.interval(Duration.ofMillis(reqTime))
                .map(i -> "event:" + i);
    }
}
