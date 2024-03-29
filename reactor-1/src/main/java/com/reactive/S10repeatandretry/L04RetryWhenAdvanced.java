package com.reactive.S10repeatandretry;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

public class L04RetryWhenAdvanced {

    public static void main(String[] args) {

        orderService(Util.faker().business().creditCardNumber())
                .retryWhen(Retry.from(
                        retrySignalFlux -> retrySignalFlux.
                                doOnNext(retrySignal -> {
                                    System.out.println("Total retries: " + retrySignal.totalRetries());
                                    System.out.println("Error: " + retrySignal.failure().getMessage());
                                })
                                .handle((retrySignal, synchronousSink) -> {
                                    if (retrySignal.failure().getMessage().equals("500")) // we want to retry only server side errors
                                        synchronousSink.next(1); // give a signal to retry
                                    else
                                        synchronousSink.error(retrySignal.failure()); // if the erro is 400, it makes no sense to retry
                                })
                                .delayElements(Duration.ofSeconds(1))
                ))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);

    }


    // order service
    private static Mono<String> orderService(String ccNumber) {
        return Mono.fromSupplier(() -> {
            processPayment(ccNumber);
            return Util.faker().idNumber().valid();
        });
    }


    // Payment service
    // we want to simulate different situations in which we might want to retry the subscription.
    // For example,
    // if we get a 5XX error, it may have sense to retry since it could be a temporary server error;
    // while if we get a 4XX, something is wrong on the client side,
    // therefore, it might not have sense to retry the operation
    private static void processPayment(String ccNumber) {
        int random = Util.faker().random().nextInt(1, 10);
        if (random < 8) throw new RuntimeException("500");
        else if (random < 10) {
            throw new RuntimeException("404");
        }
        // only if we get a 10, the payment is successful
    }

}
