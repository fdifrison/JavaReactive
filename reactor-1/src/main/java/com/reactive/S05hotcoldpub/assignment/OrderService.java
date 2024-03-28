package com.reactive.S05hotcoldpub.assignment;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Objects;

public class OrderService {

    private Flux<PurchaseOrder> flux;

    public Flux<PurchaseOrder> orderStream() {
        if (Objects.isNull(flux)) {
            flux = getOrderStream();
        }
        return flux;
    }

    // this is private because we don't want to build a pipeline for each subscriber! We want a Hot Publisher
    private Flux<PurchaseOrder> getOrderStream() {
        return Flux.interval(Duration.ofMillis(100))
                .map(i -> new PurchaseOrder())
                .publish()
                .refCount(2); // I need both the revenue and the inventory service to be registered
    }

}
