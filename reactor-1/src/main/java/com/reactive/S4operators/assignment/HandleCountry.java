package com.reactive.S4operators.assignment;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class HandleCountry {

    public static void main(String[] args) {
        Flux.generate(synchronousSink -> {
                    synchronousSink.next(Util.faker().country().name());
                })
                .map(Object::toString)
                .handle(((country, synchronousSink) -> {
                    if (country.equalsIgnoreCase("italy"))
                        synchronousSink.complete();
                    else
                        synchronousSink.next(country);
                }))
                .subscribe(Util.subscriber());
    }

}
