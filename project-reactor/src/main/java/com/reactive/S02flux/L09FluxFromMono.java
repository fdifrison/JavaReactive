package com.reactive.S02flux;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class L09FluxFromMono {

    public static void main(String[] args) {

        Mono<String> mono = Mono.just("string");
        Flux<String> flux = Flux.from(mono); // convert Mono to Flux

        doSomethingWithFlux(flux);

        // can we convert a Flux into a Mono?
        Flux.range(1, 10)
                .filter(i -> i > 3) // I can apply a filter to act on which mono the flux will emit
                .next()
                .subscribe(Util.onNext(), Util.onError(), Util.onComplete());

    }

    // imagine that starting from a mono I need to provide to my pipeline a Flux
    private static void doSomethingWithFlux(Flux<String> flux) {
        flux.subscribe(Util.onNext());
    }

}
