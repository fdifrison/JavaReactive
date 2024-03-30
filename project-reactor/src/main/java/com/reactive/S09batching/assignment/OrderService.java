package com.reactive.S09batching.assignment;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class OrderService {
    public static Flux<PurchaseOrder> orderStream() {
        return Flux.interval(Duration.ofMillis(100))
                .map(I -> new PurchaseOrder());
    }
}
