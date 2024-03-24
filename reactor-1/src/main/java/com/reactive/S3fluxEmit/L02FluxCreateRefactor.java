package com.reactive.S3fluxEmit;

import com.reactive.util.StringProducer;
import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L02FluxCreateRefactor {

    public static void main(String[] args) {

        StringProducer stringProducer = new StringProducer();

        Flux.create(stringProducer) // we need to pass a Consumer<FluxSink<>>
                .subscribe(Util.subscriber("Sub"));

        // In this way, we are producing outside the lambda expression inside the fluxSink
        stringProducer.produceUntilCountryEqual("italy");

    }

}
