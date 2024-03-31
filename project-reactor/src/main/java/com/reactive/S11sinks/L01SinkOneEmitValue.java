package com.reactive.S11sinks;

import com.reactive.util.Util;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class L01SinkOneEmitValue {

    public static void main(String[] args) {

        // emitValue vs. tryEmitValue
        // the difference is that tryEmit internally calls the same error handler,
        // but in the emitValue we can specify the behavior that we need on the onErro signal and even a retry strategy

        Sinks.One<Object> sink = Sinks.one(); // a sink capable of emitting a single value -> Mono / empty / error

        Mono<Object> mono = sink.asMono();

        mono.subscribe(Util.subscriber("Frodo")); // we use the mono to receive a value

        sink.emitValue("hi",
                (signalType, emitResult) -> {
                    // emitFailureHandler -> what needs
                    // to be done if an error signal is emitted?
                    System.out.println(signalType.name());
                    System.out.println(emitResult.name());
                    return false; // return false means dont retry
        });

        // If we try to emit again from the same Mono, we will receive an error since the Mono is exhausted.
        // Therefore, we will enter inside the emitFailureHandler
        sink.emitValue("hi again",
                (signalType, emitResult) -> {
                    // emitFailureHandler -> what needs
                    // to be done if an error signal is emitted?
                    System.out.println(signalType.name());
                    System.out.println(emitResult.name());
                    return false; // return false means dont retry
                });
        

    }

}
