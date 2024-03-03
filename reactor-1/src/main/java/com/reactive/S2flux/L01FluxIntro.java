package com.reactive.S2flux;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L01FluxIntro {

    public static void main(String[] args) {

        Flux<Integer> flux = Flux.just(1,2,3,4);

        flux.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete());

    }

}
