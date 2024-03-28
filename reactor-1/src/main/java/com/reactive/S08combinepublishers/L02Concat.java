package com.reactive.S08combinepublishers;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L02Concat {

    public static void main(String[] args) {

        Flux<String> pub1 = Flux.just("a", "b");
        Flux<String> pub2 = Flux.just("c", "d", "e");
        Flux<String> pubErr = Flux.error(new RuntimeException("oops"));

        // Same behavior
        Flux<String> concatWith = pub1.concatWith(pub2);
        Flux<String> concatFlux = Flux.concat(pub1, pub2);

        concatWith.subscribe(Util.subscriber());
        concatFlux.subscribe(Util.subscriber());

        Flux<String> concatFluxWithError = Flux.concat(pub1, pubErr, pub2);
        // NOw imagine we have in the pipeline of concatenated flux one that gives an error;
        // instead of stopping the whole emission as soon as we hit the error, we can delay it as last step

        concatFluxWithError.subscribe(Util.subscriber("WithError"));

        Flux<String> concatFluxWithErrorDelayed = Flux.concatDelayError(pub1, pubErr, pub2);
        concatFluxWithErrorDelayed.subscribe(Util.subscriber("WithErrorDelayed"));

    }

}
