package com.reactive.S5hotcoldpub.assignment;


import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class InventoryService {

    // initialize the data
    private Map<String, Integer> db;

    {
        db = new HashMap<>();
        db.put("Kids", 100);
        db.put("Automotive", 100);
    }

    public Consumer<PurchaseOrder> subscribeInventoryStream() {
        return purchaseOrder -> db.computeIfPresent(purchaseOrder.category(),
                (cat, quantity) -> quantity - purchaseOrder.quantity());
    }

    public Flux<String> inventoryStream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> db.toString()); // so bad! never expose the whole db table
    }

}
