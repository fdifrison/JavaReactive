package com.reactive.util.flatMap;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class OrderService {

    // Mock the db
    private final static Map<Integer, List<PurchaseOrder>> db =
            Map.ofEntries(
                    Map.entry(1, List.of(
                            new PurchaseOrder(1),
                            new PurchaseOrder(1),
                            new PurchaseOrder(1)
                    )),
                    Map.entry(2, List.of(
                            new PurchaseOrder(2),
                            new PurchaseOrder(2)
                    )),
                    Map.entry(3, List.of(
                            new PurchaseOrder(3),
                            new PurchaseOrder(3),
                            new PurchaseOrder(3),
                            new PurchaseOrder(3)
                    ))
            );

    public static Flux<PurchaseOrder> getOrders(int userId) {
        return Flux.create((FluxSink<PurchaseOrder> purchaseOrderFluxSink) -> {
                    db.get(userId).forEach(purchaseOrderFluxSink::next);
                    purchaseOrderFluxSink.complete();
                })
                .delayElements(Duration.ofSeconds(1)); // to see
        // that the item are not received in order since they come from a different thread (
    }

}
