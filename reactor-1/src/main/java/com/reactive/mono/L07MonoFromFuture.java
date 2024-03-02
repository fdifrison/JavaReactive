package com.reactive.mono;

import com.reactive.util.Util;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class L07MonoFromFuture {

    public static void main(String[] args) {

        Mono.fromFuture(getName())
                .subscribe(Util.onNext());

        Util.sleep(1); // just to see the result from the ForkJoinPool

    }

    // create a thread from the ForkJoinPool
    private static CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> Util.faker().name().fullName());
    }

}
