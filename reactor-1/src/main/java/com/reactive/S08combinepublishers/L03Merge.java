package com.reactive.S08combinepublishers;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L03Merge {

    // EAGER strategy

    // MErge serves as a collector of multiple publisher

    public static void main(String[] args) {

        Flux<String> qatar = getFlights("QA", 5);
        Flux<String> emirates = getFlights("EM", 10);
        Flux<String> airIta = getFlights("IT", 3);

        Flux<String> merged = Flux.merge(qatar, emirates, airIta);

        merged.subscribe(Util.subscriber("SkyScanner"));

        Util.sleepSeconds(10);


    }

    // A generator of flights
    private static Flux<String> getFlights(String company, int maxFlight) {
        return Flux.range(1, Util.faker().random().nextInt(2, maxFlight))
                .delayElements(Duration.ofSeconds(1))
                .map(i -> company + Util.faker().random().nextInt(100, 999))
                .filter(i -> Util.faker().random().nextBoolean());
    }
}

