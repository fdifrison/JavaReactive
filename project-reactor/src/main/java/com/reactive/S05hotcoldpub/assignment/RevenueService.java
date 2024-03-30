package com.reactive.S05hotcoldpub.assignment;


import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RevenueService {

    // initialize the data
    private Map<String, Double> db;

    {
        db = new HashMap<>();
        db.put("Kids", 0.0);
        db.put("Automotive", 0.0);
    }

    public Consumer<PurchaseOrder> subscribeOrderStream() {
        return purchaseOrder -> db.computeIfPresent(purchaseOrder.category(), (cat, price) -> price + purchaseOrder.price());
    }

    public Flux<String> revenueStream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> db.toString()); // so bad! never expose the whole db table
    }

}
