package com.reactive.S09batching.assignment;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BooksRevenue {

    private static final Map<String, Double> db = new HashMap<>();

    static {
        db.put("Science fiction", 0.0);
        db.put("Fantasy", 0.0);
        db.put("Suspense/Thriller", 0.0);
    }

    public static void main(String[] args) {

        getBookOrder()
                .filter(bookOrder -> db.containsKey(bookOrder.category()))
                .doOnNext(bookOrder -> db.computeIfPresent(bookOrder.category(), (k,v) -> v + bookOrder.price()))
                .subscribe(bookOrder -> System.out.println(db));

        Util.sleepSeconds(60);

    }

    private static Flux<BookOrder> getBookOrder() {
        return Flux.interval(Duration.ZERO,Duration.ofMillis(100))
                .map(i -> new BookOrder());
    }

}
