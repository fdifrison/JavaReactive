package com.reactive.S08combinepublishers;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple3;

import javax.swing.*;

public class L04Zip {

    // Imagine we have 3 concurring publishers providing parts of a car;
    // a car make sense only when all its part are assembled,
    // therefore, we need a way to emit a car object only when it is ready.
    // The 3 producers emit at a different rate; therefore, we need to use Zip() in order to emit a completed Car

    public static void main(String[] args) {
        Flux.zip(getBody(), getEngine(), getTireSet())
                // at max, I can have a Tuple8 by default without providing a custom BiConsumer
                .map((Tuple3<String, String, String> body) -> new Car(body.getT1(), body.getT2(), body.getT3()))
                .subscribe(Util.subscriber());
    }

    private static Flux<String> getBody() {
        return Flux.range(1, 5)
                .map(i -> "carBody");
    }

    private static Flux<String> getEngine() {
        return Flux.range(1, 2)
                .map(i -> "carEngine");
    }

    private static Flux<String> getTireSet() {
        return Flux.range(1, 10)
                .map(i -> "tires");
    }

    public record Car(String body, String engine, String tires) {

    };

}


