package com.reactive.S2flux;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class L08FluxFromInterval {

    public static void main(String[] args) {

        Flux.interval(Duration.ofSeconds(1)) //publish item periodically (non-blocking using thread pool)
                .subscribe(Util.onNext());

        Util.sleepSeconds(5); // to stop the main thread and see what the flux is providing

    }

}
