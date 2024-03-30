package com.reactive.S03fluxEmit;

import com.reactive.util.StringProducer;
import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L08FluxPush {

        // Very similar to .create() but it is not threadSafe therefore we can safely use it only on one thread
    public static void main(String[] args) {

        StringProducer stringProducer = new StringProducer();

        Flux.push(stringProducer) // we need to pass a Consumer<FluxSink<>>
                .subscribe(Util.subscriber("Sub"));

        // FluxSink can emit items from multiple threads
        Runnable runnable = stringProducer::produceNames;

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start(); // we are not thread safe, probably something will be missing
        }

    }

}
