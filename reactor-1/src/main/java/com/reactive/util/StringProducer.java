package com.reactive.util;

import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class StringProducer implements Consumer<FluxSink<String>> {

    private FluxSink<String> fluxSink;

    @Override
    public void accept(FluxSink<String> stringFluxSink) {
        this.fluxSink = stringFluxSink;
    }

    public void produceUntilCountryEqual(String input) {
        String name;
        String thread = Thread.currentThread().getName();
        do {
            name = Util.faker().country().name();
            this.fluxSink.next(thread + ":" + name);
        } while (!name.equalsIgnoreCase(input));
        this.fluxSink.complete();
    }

    public void produceNames() {
        String name;
        String thread = Thread.currentThread().getName();
        for (int i = 0; i < 3; i++) {
            name = Util.faker().name().fullName();
            this.fluxSink.next(thread + ":" + name);
        }
    }


}
