package com.reactive.mono;

import com.reactive.util.Util;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class L05MonoFromSupplier {

    public static void main(String[] args) {

        // We can use .just only when we already have data. In fact, even if we don't subscribe,
        // the printing will be performed anyway
        // Mono<String> mono = Mono.just(getName());

        // with .fromSupplier we will perform the action ony when we actually subscribe to the mono
        // in this way we are LAZY! and that's what we want
        Mono<String> mono = Mono.fromSupplier(L05MonoFromSupplier::getName);

        mono.subscribe(
                Util.onNext()
        );

        // instead of a Supplier, we can also use a Callable
        Supplier<String> stringSupplier = L05MonoFromSupplier::getName;
        Callable<String> stringCallable = L05MonoFromSupplier::getName;

        Mono.fromCallable(stringCallable)
                .subscribe(
                        Util.onNext()
                );

        Mono.fromSupplier(stringSupplier)
                .subscribe(
                        Util.onNext()
                );


    }

    private static String getName() {
        System.out.println("Generating name..");
        return Util.faker().name().firstName();
    }
}
