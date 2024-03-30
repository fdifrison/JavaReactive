package com.reactive.S03fluxEmit;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L04FluxCreateFix {

    public static void main(String[] args) {

        Flux.create(fluxSink -> {
                    String country;
                    do {
                        country = Util.faker().country().name();
                        System.out.println("Emitting:" + country);
                        fluxSink.next(country);

                    } while (!country.equalsIgnoreCase("italy")
                            && !fluxSink.isCancelled() // needed to stop the emission
                        // after the subscription is cancelled by take()
                    );
                    fluxSink.complete();
                })
                .take(3) // without the check on fluxSink.isCancelled(),
                // even if take() cancel the subscription,
                // the sink will continue to emit item, and this is not what we want
                .subscribe(Util.subscriber("Sub"));

    }

}
