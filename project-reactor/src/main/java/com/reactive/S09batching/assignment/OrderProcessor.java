package com.reactive.S09batching.assignment;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class OrderProcessor {

    /*
    We have an OrderService responsible to emit constantly PurchaseOrders.
    The emitted orders are processed by two different processors, one for automobile and one for kids;
    each pipeline has its own requirements
    (automobile has 10% taxes and packaging while kids has 50% discount and a free product)
    The stream of product is then consumed with a groupBy operators, which created two buckets for the two order types;
    A flatMap operator is used to apply at runtime at each flux (created by the groupBy) the correct processor pipeline
     */


    public static void main(String[] args) {

        // We are using a strategy
        // to choose at runtime which king of processor we need in the group function based on the map.key
        Map<String, Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>>> map = Map.of(
                "Kids", OrderProcessor.kidsProcessor(),
                "Automotive", OrderProcessor.automotiveProcessor()
        );

        Set<String> keys = map.keySet();

        OrderService.orderStream()
                .filter(p -> keys.contains(p.getCategory()))
                .groupBy(PurchaseOrder::getCategory)
                .flatMap(gf -> map.get(gf.key()).apply(gf))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);

    }


    public static Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>> automotiveProcessor() {
        return flux -> flux
                .doOnNext(p -> p.setPrice(1.1 * p.getPrice())) // add 10% of taxes
                .doOnNext(p -> p.setItem("[[" + p.getItem() + "]]"));
    }

    public static Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>> kidsProcessor() {
        return flux -> flux
                .doOnNext(p -> p.setPrice(0.5 * p.getPrice())) // 50% discount
                .flatMap(p -> Flux.concat(Mono.just(p), getFreeKidsOrder()));
    }

    private static Mono<PurchaseOrder> getFreeKidsOrder() {
        return Mono.fromSupplier( () -> {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setPrice(0.0);
            purchaseOrder.setCategory("Kids");
            purchaseOrder.setItem("FREE:" + purchaseOrder.getItem() + ":FREE");
            return purchaseOrder;
        });
    }




}
