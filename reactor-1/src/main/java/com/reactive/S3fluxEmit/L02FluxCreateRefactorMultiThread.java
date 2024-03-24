package com.reactive.S3fluxEmit;

import com.reactive.util.StringProducer;
import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L02FluxCreateRefactorMultiThread {

    public static void main(String[] args) {

        StringProducer stringProducer = new StringProducer();

        Flux.create(stringProducer) // we need to pass a Consumer<FluxSink<>>
                .subscribe(Util.subscriber("Sub"));

        // FluxSink can emit items from multiple threads
        Runnable runnable = stringProducer::produceNames;

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }

    }

}
