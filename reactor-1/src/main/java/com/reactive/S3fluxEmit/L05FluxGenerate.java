package com.reactive.S3fluxEmit;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L05FluxGenerate {

    public static void main(String[] args) {

        /*
         With FluxCreate, we have only one instance of FluxSink
         With synchronous sink,
         we can emit only one item (i.e. I can call .next() only once)
         but the generate() block will be executed again and again
         (like an infinite loop) because our subscriber is set to request(unbounded).
         At each iteration we are provided with a new instance of synchronousSink
                Flux.generate(synchronousSink -> {
                        synchronousSink.next(Util.faker().color().name());
                        })
                        .subscribe(Util.subscriber());
        */

        // If we add the take() operator,
        // the loop is stopped
        // and onComplete is invoked automatically without the need of handle hte if isCancelled like on the fluxSink
        Flux.generate(synchronousSink -> {
                    synchronousSink.next(Util.faker().color().name());
                    // synchronousSink.complete(); this will override the take() operator
                    // synchronousSink.error(new RuntimeException("fail!"); this will override the take() operator
                })
                .take(2)
                .subscribe(Util.subscriber());

    }

}
