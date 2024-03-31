package com.reactive.S12context;

import com.reactive.util.Util;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class L01Context {

    // With context, we are providing a means
    // to secure our reactive api requiring specific headers to consume it.
    // Context rules apply upward form where they are defined up to the publisher,
    // but they won't propagate downward to the subscriber

    public static void main(String[] args) {

        getWelcomeMsg()
                // we can also access the context and update it, ofc we need to know the header value set upstream
                .contextWrite( ctx -> ctx.put("user", ctx.get("user").toString().toUpperCase()))
                .contextWrite(Context.of("user", "Frodo")) // we are setting the required header to consume the api
                .subscribe(Util.subscriber());

    }

    // our api endpoint, the source upstream
    private static Mono<String> getWelcomeMsg() {
        return Mono.deferContextual(contextView -> { // the context is a map
           if (contextView.hasKey("user")) { // if the consumers send the header "user" than he can consume the api
               return Mono.just("Welcome " + contextView.get("user"));
           } else {
               return Mono.error(new RuntimeException("unauthenticated"));
           }
        });
    }
}
