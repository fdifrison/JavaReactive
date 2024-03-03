package com.reactive.S2flux;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

public class L04FluxFromStream {

    public static void main(String[] args) {

        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);

        // when we apply a terminal operator to a stream, it gets consumed and can't be reused,
        // and the same applies if we first pass it to a flux
        Flux<Integer> integerFlux = Flux.fromStream(integerStream);

        integerFlux.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete());

        integerFlux.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete());

        // if we want to use the stream multiple times, we need to create it directly inside the subscribe method

        List<Integer> integerList = List.of(6, 7, 8, 9, 10);

        Flux<Integer> multiStreamFlux = Flux.fromStream(integerList::stream);

        multiStreamFlux.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete());

        multiStreamFlux.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete());


    }

}
