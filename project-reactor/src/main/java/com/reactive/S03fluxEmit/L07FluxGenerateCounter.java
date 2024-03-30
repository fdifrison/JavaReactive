package com.reactive.S03fluxEmit;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

public class L07FluxGenerateCounter {

    // we want to put a counter to the generate();
    // we could use the downstream operator take()
    // or another version of the generate() method which include an initial state
    public static void main(String[] args) {

        Flux.generate(
                        () -> 1, // initial value of the counter
                        (counter, sink) -> {
                            String country = Util.faker().country().name();
                            sink.next(counter + ":" + country);
                            if (counter >= 10 || country.equalsIgnoreCase("italy"))
                                sink.complete();
                            return counter + 1; // update the counter at each look unless we enter the if statement
                        }
                )
                .subscribe(Util.subscriber());

        thisIsTheSameWithTake().take(10).subscribe(Util.subscriber("SameStuff"));

    }

    private static Flux<String> thisIsTheSameWithTake() {
        return Flux.generate(stringSynchronousSink -> {
            String country = Util.faker().country().name();
            stringSynchronousSink.next(country);
            if (country.equalsIgnoreCase("italy"))
                stringSynchronousSink.complete();

        });
    }

}
