package com.reactive.S3fluxEmit;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L01FluxCreate {

    public static void main(String[] args) {

        Flux.create(fluxSink -> {
                    String country;
                    do {
                        country = Util.faker().country().name();
                        fluxSink.next(country);

                    } while (!country.equalsIgnoreCase("italy"));
                    fluxSink.complete();
                }

        ).subscribe(Util.subscriber("Sub"));

    }

}
