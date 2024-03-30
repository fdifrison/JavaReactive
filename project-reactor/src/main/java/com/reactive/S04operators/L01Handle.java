package com.reactive.S04operators;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L01Handle {

    public static void main(String[] args) {

        // handle = filter + map
        Flux.range(1, 20)
                .handle((integer, synchronousSink) -> { // integer because we are emitting integers from the range
                    if (integer % 2 == 0) // filter
                        synchronousSink.next(integer);
                    else
                        synchronousSink.next("odd"); // map
                })
                .subscribe(Util.subscriber());

    }

}
