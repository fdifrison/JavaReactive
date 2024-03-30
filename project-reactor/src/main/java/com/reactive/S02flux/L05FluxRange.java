package com.reactive.S02flux;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L05FluxRange {

    public static void main(String[] args) {

        Flux<Integer> range = Flux.range(0, 10);

        range.subscribe(Util.onNext());

        // we can use Flux Range as a for loop
        range.log()
                .map(integer -> Util.faker().backToTheFuture().quote())
                .log()
                .subscribe(Util.onNext());

    }

}
