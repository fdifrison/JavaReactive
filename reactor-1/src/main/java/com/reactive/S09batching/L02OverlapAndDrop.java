package com.reactive.S09batching;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L02OverlapAndDrop {

    public static void main(String[] args) {

        eventStream()
                .buffer(3, 5)
                // We can tell the buffer to skip items before going on with the buffer,
                // and this can be helpful if we need to have a sense of the sequence we are receiving.
                // For example, with buffer 3 and skip 1,
                // each new least will have two elements from the previous list and one new emitted.
                // If skip is greater than max size, we are basically saying (es.
                // max = 3, skip = 5): emit 5 elements, keep 3 and drop 2.
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);

    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(1000))
                .map(i -> "event:" + i);
    }

}
