package com.reactive.part1;

import com.reactive.util.Util;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class L06SupplierRefactoring {

    public static void main(String[] args) {

        getName(); // like this, we are only building the pipeline but not the content of the supplier
        // the business logic is inside the supplier; therefore, it is executed lazily, not util we subscribe to it

        // now we are subscribing and therefore executing the pipeline
        getName()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(Util.onNext());

        System.out.println("Am I executed before?");

        Util.sleep(5); // I need to block the main thread to see the asynchronous execution of the pipeline

    }

    // we build the pipeline, with the business logic inside the supplier
    private static Mono<String> getName() {
        System.out.println("Entered getName method...");
        return Mono.fromSupplier(() -> {
                    System.out.println("Generating name...");
                    Util.sleep(3);
                    return Util.faker().name().fullName();
                }
        ).map(String::toUpperCase);
    }

}
