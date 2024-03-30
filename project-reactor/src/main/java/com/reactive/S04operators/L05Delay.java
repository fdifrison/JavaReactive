package com.reactive.S04operators;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L05Delay {

    public static void main(String[] args) {

        System.setProperty("reactor.bufferSize.x", "9");

        Flux.range(1, 10) // by default, only the 32 of the data are requested
                // this comes from the reactor.bufferSize.x (default 32, min 8)
                .log()
                // under the hood, there is an implicit limit rate when applying a delay
                .delayElements(Duration.ofMillis(200))
                .subscribe(Util.subscriber());

        // Hence im requesting for a range of 10 items,
        // the buffer size is set to 9 items,
        // therefore, the first request will be request(9); item will start to be emitted to the subscriber, and at 75%
        // of the request, the subscriber will ask for more data


        Util.sleepSeconds(5);

    }

}
