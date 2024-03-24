package com.reactive.S3fluxEmit.assignment;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class FluxGenerateWhileLoop {

    public static void main(String[] args) {

        Flux.generate(synchronousSink -> {
                    String country = Util.faker().country().name();
                    synchronousSink.next(country);
                    if ("italy".equalsIgnoreCase(country)) {
                        synchronousSink.complete();
                    }

                })
                .subscribe(Util.subscriber("CountryMatcher"));

    }

}
