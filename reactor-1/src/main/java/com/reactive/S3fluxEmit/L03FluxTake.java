package com.reactive.S3fluxEmit;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L03FluxTake {

    public static void main(String[] args) {

        Flux.range(1, 10)
                .log()
                .take(3) // from the logs we cna see that take()
                // cancel the subscription as soon as it gets the declared number of items
                .log()
                .subscribe(Util.subscriber("Sub"));

    }
}
